package com.paypal.musictag.crawler.musixmatch;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mongodb.client.result.UpdateResult;
import com.paypal.musictag.exception.NoNextException;
import com.paypal.musictag.exception.OutOfLimitException;
import com.paypal.musictag.mongo.LyricConnector;
import com.paypal.musictag.psql.WorkConnector;

import com.paypal.musictag.util.CrawlerUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public final class MusixMatchCrawler {

    private static final Logger logger = LoggerFactory.getLogger(MusixMatchCrawler.class);
    private static final String[] ApiKeys = {
        "8bbd389ac24588e24dbcca43218a17b1",
        "7bfbe44f53758b6cf649a8a4eb372520",
        "7c6db7d24e115c1fd0c8cda086dcbc3e"
    };
    private WorkConnector workConnector;
    private LyricConnector lyricConnector;

    public MusixMatchCrawler(){
        try {
            this.workConnector = new WorkConnector();
            this.lyricConnector = new LyricConnector();
        } catch (SQLException | UnknownHostException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getLyricByMBID(String trackMBID, String apiKey) throws IOException {
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_MBID, trackMBID);
        params.put(Constants.FORMAT, "json");

        String response = MusixMatchRequest.sendRequest(Constants.TRACK_LYRICS_GET, params);
        return CrawlerUtil.jsontoMap(response);
    }

    public Map<String, Object> getTrackByMBID(String trackMBID, String apiKey) throws IOException {
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_MBID, trackMBID);
        params.put(Constants.FORMAT, "json");

        String response = MusixMatchRequest.sendRequest(Constants.TRACK_GET, params);
        return CrawlerUtil.jsontoMap(response);
    }

    public void dumpMBLyricUrls() throws SQLException {
        List<Map<String, Object>> works = workConnector.allWorkHavingLyricUrl();
        for(Map<String, Object> work : works) {
            List<String> urls = workConnector.findAllLyricUrls((int)work.get("id"));
            assert !urls.isEmpty();
            Map<String, Object> doc = new HashMap<>();
            doc.put("from", "musicbrainz");
            doc.put(Constants.WORK_MBID, String.valueOf(work.get("gid")) );
            doc.put("lyric_urls", urls);
            UpdateResult ur =  lyricConnector.replaceOneInFoundTable(doc, true);
            if(ur.getModifiedCount() == 1) {
                logger.info(doc.get("work_mbid") + ": " + "modified." );
            } else if( ur.getUpsertedId() != null ) {
                logger.info(doc.get("work_mbid") + ": " + "inserted." );
            } else {
                assert false;
            }
        }
    }

    public void crawlOneLyricUrl(String apiKey) throws IOException, SQLException, NoNextException, OutOfLimitException {
        Map<String, Object> work = workConnector.nextNoUrlWork();
        int workId = (int) work.get("id");
        List<String> urls = workConnector.findAllLyricUrls(workId);
        assert urls.isEmpty();

        Map<String, Object> doc = new HashMap<>();
        doc.put("work_mbid", work.get("gid").toString());

        logger.info("start crawling work: " + workId + " (" + doc.get("work_mbid") + ")" );

        doc.put("from", "musixmatch");
        List<Map<String, Object>> recordings = workConnector.findAllRecordings((int) work.get("id"));
        Map<String, Object> json = null;
        Map<String, Object> msg = null;
        int i = 0;
        for(; i < recordings.size(); ++i) {
            String mbid = recordings.get(i).get("gid").toString();
            logger.info("try recording" + i + " (" + mbid + ")" );
            json = getTrackByMBID(mbid, apiKey);
            msg = (Map<String, Object>) json.get("message");
            int status_code = (int) ( (Map<String, Object>) msg.get("header")).get("status_code");
            if( status_code == StatusCode.REQ_SUCCESS.getStatusCode() ) {
                doc.put("recording_mbid", mbid);
                break;
            } else if( status_code != StatusCode.RESOURCE_NOT_FOUND.getStatusCode()) {
                throw new OutOfLimitException("work: " + workId + " (" + doc.get("work_mbid") + ")");
            }
        }

        if( i == recordings.size() ) {
            lyricConnector.insertOneIntoNotFoundTable(doc);
            logger.info("[Lyric not found] work: " + workId + " (" + doc.get("work_mbid") + ")" );
        } else {
            doc.put("musixmatch_msg", msg);
            lyricConnector.insertOneIntoFoundTable(doc);
            logger.info("[Lyric found] work: " + workId + " (" + doc.get("work_mbid") + ")" );
        }
    }

    public void crawlOneLimitedLyric (String apiKey) throws IOException, SQLException, NoNextException, OutOfLimitException {
        Map<String, Object> lyric = lyricConnector.next();
        String work_mbid =(String) lyric.get("work_mbid");
        String recording_mbid =(String) lyric.get("recording_mbid");
        logger.info("[Crawl limited lyrics|start] recording: " + recording_mbid );

        Map<String, Object> doc = new HashMap<>();
        Map<String, Object> json = null;
        Map<String, Object> msg = null;
        json = getLyricByMBID(recording_mbid, apiKey);
        msg = (Map<String, Object>) json.get("message");
        int status_code = (int) ( (Map<String, Object>) msg.get("header")).get("status_code");
        if( status_code == StatusCode.REQ_SUCCESS.getStatusCode() ) {

            Map<String, Object> body = ((Map<String, Object>) msg.get("body"));
            doc.put("lyric_limited", ((Map<String, Object>) body.get("lyrics")).get("lyrics_body"));
            Map<String, Object> filter = new HashMap<>();
            filter.put("work_mbid", work_mbid);
            lyricConnector.updateOneInFoundTable(filter, doc);
            logger.info("[Crawl limited lyrics|found] recording: " + recording_mbid );
        } else if( status_code != StatusCode.RESOURCE_NOT_FOUND.getStatusCode()) {
            throw new OutOfLimitException("recording: " + recording_mbid );
        } else {
            logger.info("[Crawl limited lyrics|not found] recording: " + recording_mbid );
        }
    }



    class CrawlLyricUrlTask implements Runnable {
        private final String apiKey;

        CrawlLyricUrlTask(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public void run() {
            while( true ) {
                logger.info("Lyric url crawler started, apiKey is " + apiKey );
                try {
                    crawlOneLyricUrl(apiKey);
                } catch (IOException | SQLException e) {
                    logger.error("crawl lyric url error");
                    logger.error(null, e);
                } catch (NoNextException e) {
                    logger.error("No work left");
                    logger.error(null, e);
                    break;
                } catch (OutOfLimitException e) {
                    logger.error("MusixMatch limit reached");
                    logger.error(null, e);
                    break;
                }
            }
        }
    }
    class CrawlLyricLimitedTask implements Runnable {
        private final String apiKey;

        CrawlLyricLimitedTask(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public void run() {
            while( true ) {
                logger.info("Limited Lyric crawler started, apiKey is " + apiKey );
                try {
                    crawlOneLimitedLyric(apiKey);
                } catch (IOException | SQLException e) {
                    logger.error("crawl limited lyric error");
                    logger.error(null, e);
                } catch (NoNextException e) {
                    logger.error("No lyric url left");
                    logger.error(null, e);
                    break;
                } catch (OutOfLimitException e) {
                    logger.error("MusixMatch limit reached");
                    logger.error(null, e);
                    break;
                }
            }
        }
    }

    public void startCrawlingLyricUrl(int threadCnt) {
        ExecutorService es = Executors.newFixedThreadPool(threadCnt);
        for(int i = 0; i < threadCnt; i++) {
            es.execute(new CrawlLyricUrlTask(ApiKeys[i % ApiKeys.length]));
        }
    }

    public void startCrawlingLimitedLyric(int threadCnt) {
        ExecutorService es = Executors.newFixedThreadPool(threadCnt);
        for(int i = 0; i < threadCnt; i++) {
            es.execute(new CrawlLyricLimitedTask(ApiKeys[i % ApiKeys.length]));
        }
    }


    static public void main(String args[]) throws SQLException, IOException, NoNextException, OutOfLimitException {
        MusixMatchCrawler mmc = new MusixMatchCrawler();
        mmc.crawlOneLimitedLyric(ApiKeys[0]);
        int threadCnt = 12;
        mmc.startCrawlingLimitedLyric(threadCnt);
    }
}
