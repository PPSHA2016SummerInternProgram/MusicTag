package com.paypal.musictag.dao;

import java.io.IOException;
import java.util.Map;

public interface RecordingDao {
    Map<String, Object> basic(String recordingId) throws IOException;

    Map<String, Object> releases(String recordingId) throws IOException;

    Map<String, Object> workArtistRels(String recordingId) throws IOException;

    /**
     * @param recordingId the MBID of the recording
     * @return Map object contains basic info, ratings, related releases, and work & artist relationships,
     *         where work further contains artist relationships
     * @throws IOException
     */
    Map<String, Object> full(String recordingId) throws IOException;

}
