package com.paypal.musictag.dao.usingwebservice.api;

import org.junit.Test;

import com.paypal.musictag.values.StaticValues;

public class MusicTagPrivateAPITest {

    @Test
    public void testMusicTagPrivateAPI() throws Exception {
        MusicTagPrivateAPI.getArtistCommonsImage(StaticValues.artistGid0);
        MusicTagPrivateAPI.getArtistCommonsImage(StaticValues.artistGid1);
        MusicTagPrivateAPI.getArtistWikiProfle(StaticValues.artistGid0);
        MusicTagPrivateAPI.getArtistWikiProfle(StaticValues.artistGid1);
    }
}
