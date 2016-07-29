package com.paypal.musictag.crawler.lastfm;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paypal.musictag.mongo.ArtistConnector;
import com.paypal.musictag.psql.NoArtistException;
import com.paypal.musictag.psql.PsqlConnector;
import com.paypal.musictag.util.CrawlerUtil;

public class LastfmCrawler {

	final private static String API_KEY = "4c8fe16aebbb7f2394d538ccd131723e";
	final private static String API_URL = "http://ws.audioscrobbler.com/2.0/?method=%s&mbid=%s&api_key=" + API_KEY
			+ "&format=json";

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
					logger.error("Crawl Artist Error");
					logger.error(null, e);
				} catch (NoArtistException e) {
					logger.error(null, e);
					break;
				}
			}
		}
	};

	private PsqlConnector psqlConnector;
	private ArtistConnector mongoConnector;

	private int WORK_AMOUNT_MIN = 0;

	public LastfmCrawler() throws ClassNotFoundException, SQLException {
		psqlConnector = new PsqlConnector();
	}

	public void startCrawling(int threadAmount) throws UnknownHostException {
		mongoConnector = new ArtistConnector();
		ExecutorService executor = Executors.newFixedThreadPool(threadAmount);
		for (int i = 0; i < threadAmount; i++) {
			executor.execute(crawlTask);
		}
	}

	private void crawlArtist() throws SQLException, NoArtistException, IOException, InterruptedException {
		Map<String, Object> artist = psqlConnector.nextArtist();
		String gid = String.valueOf(artist.get("gid"));
		// String id = String.valueOf(artist.get("id"));
		long workAmount = ((Number) artist.get("count")).intValue();
		int seq = (int) artist.get("seq");
		logger.info("seq=" + seq + ", artistGid=" + gid);
		if (workAmount < WORK_AMOUNT_MIN) {
			logger.info(gid + ", work amount is" + workAmount + ", skip it");
		} else {

			if (mongoConnector.isAlreadyFound(gid)) {
				logger.info(gid + ", already exist in " + mongoConnector.getArtistTableName() + ", skip it.");
				return;
			}

			if (mongoConnector.isAlreadyNotFound(gid)) {
				logger.info(gid + ", already exist in " + mongoConnector.getArtistNotFoundTablename() + ", skip it.");
				return;
			}

			logger.info(gid + ", ready to crawl it.");
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
		if (response == null || response.containsKey("error") || !response.containsKey("artist")) {
			logger.info(gid + ", error" + (response == null ? null : response.toString()));
			Map<String, Object> map = new HashMap<>();
			map.put("gid", gid);
			map.put("error", response);
			mongoConnector.insertOneIntoNotFoundTable(map);
		} else {
			logger.info(gid + ", ok.");
			@SuppressWarnings("unchecked")
			Map<String, Object> artist = (Map<String, Object>) response.get("artist");
			artist.put("gid", gid);
			mongoConnector.insertOneIntoFoundTable(artist);
		}

	}

	private Map<String, Object> crawlInfo(ApiMethod method, String mbid) throws IOException {
		String urlString = String.format(API_URL, method.getMethod(), mbid);
		logger.info("crawling artist-->" + urlString);
		return CrawlerUtil.jsontoMap(CrawlerUtil.getJsonFromURL(urlString));
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, UnknownHostException {
		int threadAmount = 1;
		if (args.length > 0) {
			threadAmount = Integer.parseInt(args[0]);
		}
		LastfmCrawler crawler = new LastfmCrawler();
		crawler.startCrawling(threadAmount);
	}

}
