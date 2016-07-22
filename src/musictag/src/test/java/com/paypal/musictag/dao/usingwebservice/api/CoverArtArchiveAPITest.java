package com.paypal.musictag.dao.usingwebservice.api;

import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

/**
 * Don't know how to test NetConnectionException, MalformedURLException and ProtocolException.
 *  
 * @author shilzhang
 *
 */
public class CoverArtArchiveAPITest {

    @Test
    public void testGetReleaseCover() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException {
        CoverArtArchiveAPI.sendRequest("release/", StaticValues.releaseGid0);
        CoverArtArchiveAPI.sendRequest("release/", StaticValues.releaseGid1);
    }
        
    @Test(expected=NetContentNotFoundException.class)
    public void testGetReleaseCoverNoContentException() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException{
    	CoverArtArchiveAPI.sendRequest("release/", "not exits");
    }
}
