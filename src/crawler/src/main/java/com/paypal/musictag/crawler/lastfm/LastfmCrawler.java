package com.paypal.musictag.crawler.lastfm;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paypal.musictag.mongo.MongoConnector;
import com.paypal.musictag.psql.NoArtistException;
import com.paypal.musictag.psql.PsqlConnector;
import com.paypal.musictag.util.CrawlerUtil;

public class LastfmCrawler {

	final private static String API_KEY = "4c8fe16aebbb7f2394d538ccd131723e";
	final private static String API_URL = "http://ws.audioscrobbler.com/2.0/?method=%s&mbid=%s&api_key=" + API_KEY
			+ "&format=json";

	enum MongoCollectionName {
		LAST_FM_ARTIST("lastfm.artist");
		private String collectionName;

		MongoCollectionName(String name) {
			collectionName = name;
		}

		public String getName() {
			return collectionName;
		}
	}

	enum ApiMethod {
		ARTIST_INFO("artist.getinfo"), ALBUM_INFO("artist.getalbum"), TRACK_INFO("artist.gettrack");
		private String methodName;

		ApiMethod(String method) {
			methodName = method;
		}

		public String getMethod() {
			return methodName;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(LastfmCrawler.class);

	private Runnable crawlTask = new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					crawlArtist();
				} catch (SQLException | IOException | InterruptedException e) {
					logger.error(null, e);
				} catch (NoArtistException e) {
					logger.error(null, e);
					break;
				}
			}
		}
	};

	private PsqlConnector psqlConnector;
	private MongoConnector mongoConnector;

	private int WORK_AMOUNT_MIN = 0;

	public LastfmCrawler() throws ClassNotFoundException, SQLException {
		psqlConnector = new PsqlConnector();
	}

	public void startCrawling(int threadAmount) throws UnknownHostException {
		mongoConnector = new MongoConnector(MongoCollectionName.LAST_FM_ARTIST.getName());
		ExecutorService executor = Executors.newFixedThreadPool(threadAmount);
		for (int i = 0; i < threadAmount; i++) {
			executor.execute(crawlTask);
		}
	}

	private void crawlArtist() throws SQLException, NoArtistException, IOException, InterruptedException {
		Map<String, Object> artist = psqlConnector.nextArtist();
		String gid = String.valueOf(artist.get("gid"));
		String id = String.valueOf(artist.get("id"));
		long workAmount = (long) artist.get("count");
		if (workAmount < WORK_AMOUNT_MIN) {
			logger.info("artistGid=" + gid + ", id=" + id + ", workAmount=" + workAmount + "-->skip it.");
		} else {

			if (!mongoConnector.findArtist(gid).isEmpty()) {
				logger.info("artistGid=" + gid + ", id=" + id + ", workAmount=" + workAmount
						+ "-->already exist in mongo, skip it.");
				return;
			}

			logger.info("artistGid=" + gid + ", id=" + id + ", workAmount=" + workAmount + "-->ready to crawl it.");
			Map<String, Object> response = crawlInfo(ApiMethod.ARTIST_INFO, gid);

			// rate limit, so try again
			int retryAmount = 5;
			while (--retryAmount > 0 && response.containsKey("error")
					&& "29".equals(String.valueOf(response.get("error")))) {
				Thread.sleep(500);
				response = crawlInfo(ApiMethod.ARTIST_INFO, gid);
			}

			saveArtist(gid, response);
		}
	}

	private void saveArtist(String gid, Map<String, Object> response) {
		if (response == null || response.containsKey("error")) {
			logger.error("failed to get information of artist " + gid + "-->"
					+ (response == null ? null : response.toString()));
			return;
		}

		if (!response.containsKey("artist")) {
			logger.error("failed to get information of artist " + gid + "-->" + response.toString());
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> artist = (Map<String, Object>) response.get("artist");

		artist.put("gid", gid);
		mongoConnector.insertArtist(artist);

	}

	private Map<String, Object> crawlInfo(ApiMethod method, String mbid) throws IOException {
		String urlString = String.format(API_URL, method.getMethod(), mbid);
		logger.info("crawling artist-->" + urlString);
		return CrawlerUtil.jsontoMap(CrawlerUtil.getJsonFromURL(urlString));
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, UnknownHostException {
		int threadAmount = 2;
		if (args.length > 0) {
			threadAmount = Integer.parseInt(args[0]);
		}
		LastfmCrawler crawler = new LastfmCrawler();
		crawler.startCrawling(threadAmount);
	}

}
