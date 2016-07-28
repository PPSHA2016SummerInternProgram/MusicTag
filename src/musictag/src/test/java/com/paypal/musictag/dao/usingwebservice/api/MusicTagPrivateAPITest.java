package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;

import org.junit.Test;

import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

public class MusicTagPrivateAPITest {

    @Test
    public void testGetArtistCommonsImage() throws IOException {
        MusicTagPrivateAPI.getArtistCommonsImage(StaticValues.artistGid0);
        MusicTagPrivateAPI.getArtistCommonsImage(StaticValues.artistGid1);
    }
    
    @Test(expected = NetContentNotFoundException.class)
    public void testGetArtistCommonsImageWithException() throws IOException{
    	MusicTagPrivateAPI.getArtistCommonsImage(StaticValues.artistGidNoCommonImg);
    }
    
    @Test(expected = NetBadRequestException.class)
    public void testGetArtistCommonsImageWithBadRequest() throws IOException{
    	MusicTagPrivateAPI.getArtistCommonsImage("a not exists person");
    }
    
    @Test
    public void testGetArtistWikiProfile() throws IOException{
        MusicTagPrivateAPI.getArtistWikiProfle(StaticValues.artistGid0);
        MusicTagPrivateAPI.getArtistWikiProfle(StaticValues.artistGid1);
    }

    @Test(expected = NetContentNotFoundException.class)
    public void testGetArtistWikiProfileWithException() throws IOException{
    	MusicTagPrivateAPI.getArtistWikiProfle(StaticValues.artistGidNoWikiExtract);
    }
    
    @Test(expected = NetBadRequestException.class)
    public void testGetArtistWikiProfileBadRequest() throws IOException{
    	MusicTagPrivateAPI.getArtistWikiProfle("a not exists person");
    }
}
