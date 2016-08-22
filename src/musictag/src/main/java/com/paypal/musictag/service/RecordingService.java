package com.paypal.musictag.service;

import java.io.IOException;
import java.util.Map;

public interface RecordingService {
    Map<String, Object> basic(String recordingId) throws IOException;

    Map<String, Object> releases(String recordingId) throws IOException;

    Map<String, Object> workArtistRels(String recordingId) throws IOException;

    Map<String, Object> full(String recordingId) throws IOException;

    Map<String, Object> lyric(String recordingId) throws IOException;
}
