package com.paypal.musictag.dao.usingwebservice.api;

import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

/**
 * Don't know how to test NetConnectionException, MalformedURLException and ProtocolException.
 *  
 * @author shilzhang
 *
 */
public class CoverArtArchiveAPITest {

    @Test
    public void testGetReleaseCover() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException, NetBadRequestException {
        CoverArtArchiveAPI.sendRequest("release/", StaticValues.releaseGid0);
        CoverArtArchiveAPI.sendRequest("release/", StaticValues.releaseGid1);
    }
        
    @Test(expected=NetContentNotFoundException.class)
    public void testGetReleaseCoverNoContentException() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException, NetBadRequestException{
    	CoverArtArchiveAPI.sendRequest("release/", StaticValues.releaseGidNoCoverArt);
    }
    
    @Test(expected=NetBadRequestException.class)
    public void testGetReleaseCoverNetBadRequestException() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException, NetBadRequestException{
    	CoverArtArchiveAPI.sendRequest("release/", "not exits");
    }
    
    @Test(expected=NetContentNotFoundException.class)
    public void testGetReleaseGroupCoverNoContentException() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException, NetBadRequestException{
    	CoverArtArchiveAPI.sendRequest("release-group/", StaticValues.releaseGroupGidNoCoverArt);
    }
    
    @Test(expected=NetBadRequestException.class)
    public void testGetReleaseGroupCoverNetBadRequestException() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException, NetBadRequestException{
    	CoverArtArchiveAPI.sendRequest("release-group/", "not exits");
    }
}
