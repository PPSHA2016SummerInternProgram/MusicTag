package com.paypal.musictag.crawler.musixmatch;

public class Constants {
    /**
     * A constant field to denote the value of API_KEY in the KV mapping.
     */
    public static final String API_KEY = "apikey";

    /**
     * A constant field to denote the base API url.
     */
    public static final String API_URL = "http://api.musixmatch.com/ws/";

    /**
     * A constant field to denote the API version.
     */
    public static final String API_VERSION = "1.1";

    /**
     * A constant field to denote the value of FORMAT in the KV mapping.
     */
    public static final String FORMAT = "format";

    /**
     * A constant field to denote the value of F_HAS_LYRICS in the KV mapping.
     */
    public static final String F_HAS_LYRICS = "f_has_lyrics";

    /**
     * A constant field to denote the value of JSON in the KV mapping.
     */
    public static final String JSON = "json";

    /**
     * A constant field to denote the value of PAGE in the KV mapping.
     */
    public static final String PAGE = "page";

    /**
     * A constant field to denote the value of PAGE_SIZE in the KV mapping.
     */
    public static final String PAGE_SIZE = "page_size";

    /**
     * A constant field to denote the value of QUERY in the KV mapping.
     */
    public static final String QUERY = "q";

    /**
     * A constant field to denote the value of QUERY_ARTIST in the KV mapping.
     */
    public static final String QUERY_ARTIST = "q_artist";

    /**
     * A constant field to denote the value of QUERY_TRACK in the KV mapping.
     */
    public static final String QUERY_TRACK = "q_track";

    /**
     * A constant field to denote the value of TRACK_ID in the KV mapping.
     */
    public static final String TRACK_ID = "track_id";

    /**
     * A constant field to denote the value of TRACK_MBID (musicbrainz track mbid or recording mbid) in the KV mapping.
     */
    public static final String TRACK_MBID = "track_mbid";

    /**
     * A constant field to denote the value of '/' .
     */
    public static final String URL_DELIM = "/";

    /**
     * A constant field to denote the value of XML in the KV mapping.
     */
    public static final String XML = "xml";

    /**
     * Retrieve the lyrics of a track.
     */
    public static final String TRACK_LYRICS_GET = "track.lyrics.get";

    /**
     * Get a track info from our database: title, artist, instrumental flag and
     * cover art.
     */
    public static final String TRACK_GET = "track.get";

    public static final String WORK_MBID = "work_mbid";
}
