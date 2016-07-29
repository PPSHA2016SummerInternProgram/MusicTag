package com.paypal.musictag.crawler.lastfm;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paypal.musictag.exception.NoArtistException;
import com.paypal.musictag.mongo.AlbumConnector;
import com.paypal.musictag.mongo.ArtistConnector;
import com.paypal.musictag.psql.PsqlConnector;
import com.paypal.musictag.util.CrawlerUtil;

public class LastfmCrawler {

	final private static String API_KEY = "4c8fe16aebbb7f2394d538ccd131723e";
	final private static String API_URL = "http://ws.audioscrobbler.com/2.0/?method=%s&mbid=%s&api_key=" + API_KEY
			+ "&format=json";

	enum ApiMethod {
		ARTIST_INFO("artist.getinfo"), ALBUM_INFO("album.getinfo"), TRACK_INFO("track.getinfo");
		private String methodName;

		ApiMethod(String method) {
			methodName = method;
		}

		public String getMethod() {
			return methodName;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(LastfmCrawler.class);

	private Runnable crawlArtistTask = new Runnable() {
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

	private Runnable crawlAlbumTask = new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					crawlAlbum();
				} catch (NoArtistException e) {
					break;
				} catch (SQLException e) {
					logger.error(null, e);
				}
			}
		}
	};

	private PsqlConnector psqlConnector;
	private ArtistConnector artistConnector;
	private AlbumConnector albumConnector;

	private int WORK_AMOUNT_MIN = 0;

	public LastfmCrawler() throws ClassNotFoundException, SQLException {
		psqlConnector = new PsqlConnector();
	}

	public void startCrawlingArtistInfo(int threadAmount) throws UnknownHostException {
		artistConnector = new ArtistConnector();
		ExecutorService executor = Executors.newFixedThreadPool(threadAmount);
		for (int i = 0; i < threadAmount; i++) {
			executor.execute(crawlArtistTask);
		}
	}

	public void startCrawlingAlbumInfo(int threadAmount) throws UnknownHostException {
		artistConnector = new ArtistConnector();
		albumConnector = new AlbumConnector();
		ExecutorService executor = Executors.newFixedThreadPool(threadAmount);
		for (int i = 0; i < threadAmount; i++) {
			executor.execute(crawlAlbumTask);
		}
	}

	private int LISTENER_FILTER = 10000;

	private void crawlAlbum() throws NoArtistException, SQLException {
		Map<String, Object> artist = artistConnector.nextArtist();
		String gid = String.valueOf(artist.get("gid"));
		int seq = (int) artist.get("seq");
		logger.info("seq=" + seq + ", artistGid=" + gid);
		if (!artist.containsKey("stats")) {
			logger.info(gid + ", no stats found, skip it.");
			return;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> stats = (Map<String, Object>) artist.get("stats");
		int listeners = Integer.parseInt(String.valueOf(stats.get("listeners")));
		if (listeners < LISTENER_FILTER) {
			logger.info(gid + ", listeners is not enough, skip it.");
			return;
		}

		crawlAllAlbums(gid);
	}

	private void crawlAllAlbums(String artistGid) throws SQLException {
		logger.info(artistGid + ", ready to crawl ablums of the artist.");
		List<Map<String, Object>> releases = psqlConnector.findAllReleases(artistGid);
		logger.info(artistGid + ", has " + releases.size() + " releases.");
		for (Map<String, Object> release : releases) {
			crawlOneAlbum(String.valueOf(release.get("gid")));
		}
	}

	private void crawlOneAlbum(String releaseGid) {
		try {
			if (albumConnector.isAlreadyFound(releaseGid)) {
				logger.info(releaseGid + ", already exist in " + albumConnector.getAblumTableName() + ", skip it.");
				return;
			}
			if (albumConnector.isAlreadyNotFound(releaseGid)) {
				logger.info(
						releaseGid + ", already exist in " + albumConnector.getAlbumNotFoundTablename() + ", skip it.");
				return;
			}

			Map<String, Object> response = crawlInfo(ApiMethod.ALBUM_INFO, releaseGid);
			if (response.containsKey("album")) {

				@SuppressWarnings("unchecked")
				Map<String, Object> album = (Map<String, Object>) response.get("album");
				album.put("gid", releaseGid);
				albumConnector.insertOneIntoFoundTable(album);
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("gid", releaseGid);
				error.put("error", response);
				albumConnector.insertOneIntoNotFoundTable(error);
			}
			System.out.println(response);
		} catch (IOException e) {
			logger.error(null, e);
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

			if (artistConnector.isAlreadyFound(gid)) {
				logger.info(gid + ", already exist in " + artistConnector.getArtistTableName() + ", skip it.");
				return;
			}

			if (artistConnector.isAlreadyNotFound(gid)) {
				logger.info(gid + ", already exist in " + artistConnector.getArtistNotFoundTablename() + ", skip it.");
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
			artistConnector.insertOneIntoNotFoundTable(map);
		} else {
			logger.info(gid + ", ok.");
			@SuppressWarnings("unchecked")
			Map<String, Object> artist = (Map<String, Object>) response.get("artist");
			artist.put("gid", gid);
			artistConnector.insertOneIntoFoundTable(artist);
		}

	}

	private Map<String, Object> crawlInfo(ApiMethod method, String mbid) throws IOException {
		String urlString = String.format(API_URL, method.getMethod(), mbid);
		logger.info("crawling --> " + urlString);
		return CrawlerUtil.jsontoMap(CrawlerUtil.getJsonFromURL(urlString));
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, UnknownHostException {
		int threadAmount = 1;
		String type = null;
		if (args.length == 0) {
			type = "album";
			threadAmount = 1;
		} else if (args.length == 2) {
			type = args[0];
			threadAmount = Integer.parseInt(args[1]);
		}

		LastfmCrawler crawler = new LastfmCrawler();

		System.out.println("type: " + type + ", threadAmount: " + threadAmount);

		if ("artist".equals(type)) {
			crawler.startCrawlingArtistInfo(threadAmount);
		} else if ("album".equals(type)) {
			crawler.startCrawlingAlbumInfo(threadAmount);
		} else {
			System.out.println("cannot support: " + type);
		}

	}

}
