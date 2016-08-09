package com.paypal.musictag.crawler.musixmatch;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.paypal.musictag.exception.NoNextException;
import com.paypal.musictag.mongo.LyricConnector;
import com.paypal.musictag.psql.WorkConnector;

import com.paypal.musictag.util.CrawlerUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public final class MusixMatchCrawler {

    private final String apiKey;
    private static final Logger logger = LoggerFactory.getLogger(MusixMatchCrawler.class);

	private WorkConnector workConnector;
	private LyricConnector lyricConnector;

    public MusixMatchCrawler(String apiKey){
        this.apiKey = apiKey;
    }

    public Map<String, Object> getLyricByMBID(String trackMBID) throws IOException {
		Map<String, Object> params = new HashMap<>();

		params.put(Constants.API_KEY, apiKey);
		params.put(Constants.TRACK_MBID, trackMBID);
		params.put(Constants.FORMAT, "json");

		String response = MusixMatchRequest.sendRequest(Constants.TRACK_LYRICS_GET, params);
        return CrawlerUtil.jsontoMap(response);
	}

	public void crawlOneLyric() throws IOException, SQLException, NoNextException {
		Map<String, Object> work = workConnector.nextWork();
		int workId = (int) work.get("id");
        String from = "";
        List<String> urls = workConnector.findAllLyricUrls(workId);
        Map<String, Object> doc = new HashMap<>();
        doc.put("work_mbid", work.get("gid").toString());
		if( urls.isEmpty() ) {
		    doc.put("from", "musixmatch");
			List<Map<String, Object>> recordings = workConnector.findAllRecordings((int) work.get("id"));
            Map<String, Object> json = null;
			Map<String, Object> msg = null;
			int i = 0;
			for(; i < recordings.size(); ++i) {
				String mbid = recordings.get(i).get("gid").toString();
				json = getLyricByMBID(mbid);
				msg = (Map<String, Object>) json.get("message");
                if(( (Map<String, Object>) msg.get("header")).get("status_code") == StatusCode.REQ_SUCCESS )
                	break;
			}

			if( i == recordings.size() ) {
				lyricConnector.insertOneIntoNotFoundTable(doc);
			} else {
				doc.put("musixmatch_msg", msg);
				lyricConnector.insertOneIntoFoundTable(doc);
			}
		} else {
		    doc.put("from", "musicbrainz");
            doc.put("lyric_urls", urls);
			lyricConnector.insertOneIntoFoundTable(doc);
		}
	}


	static public void main(String args[]) {
	    logger.info("test");
		logger.error("adfasf");
	}
}
