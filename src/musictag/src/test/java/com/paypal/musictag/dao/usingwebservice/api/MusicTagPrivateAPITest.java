package com.paypal.musictag.dao.usingwebservice.api;

import org.junit.Test;

import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.values.StaticValues;

public class MusicTagPrivateAPITest {

    @Test
    public void testGetArtistCommonsImage() throws NetConnectionException {
        MusicTagPrivateAPI.getArtistCommonsImage(StaticValues.artistGid0);
        MusicTagPrivateAPI.getArtistCommonsImage(StaticValues.artistGid1);
    }
    
    @Test(expected = NetConnectionException.class)
    public void testGetArtistCommonsImageWithException() throws NetConnectionException{
    	MusicTagPrivateAPI.getArtistCommonsImage("a not exists person");
    }
    
    @Test
    public void testGetArtistWikiProfile() throws NetConnectionException{
        MusicTagPrivateAPI.getArtistWikiProfle(StaticValues.artistGid0);
        MusicTagPrivateAPI.getArtistWikiProfle(StaticValues.artistGid1);
    }

    @Test(expected = NetConnectionException.class)
    public void testGetArtistWikiProfileWithException() throws NetConnectionException{
    	MusicTagPrivateAPI.getArtistWikiProfle("a not exists person");
    }
}
