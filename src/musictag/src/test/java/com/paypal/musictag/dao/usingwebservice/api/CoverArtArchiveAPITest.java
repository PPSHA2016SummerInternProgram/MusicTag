package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;

import org.junit.Test;

import com.paypal.musictag.values.StaticValues;

public class CoverArtArchiveAPITest {

    private Exception exception = new Exception("An exception is needed.");

    @Test
    public void testGetReleaseCover() throws Exception {
        CoverArtArchiveAPI.sendRequest("release/", StaticValues.releaseGid0);
        CoverArtArchiveAPI.sendRequest("release/", StaticValues.releaseGid1);
        try {
            CoverArtArchiveAPI.sendRequest("release/", null);
            throw exception;
        } catch (IOException e) {
            // Just empty
        }
    }
}
