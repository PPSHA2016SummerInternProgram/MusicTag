package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagServiceAPI;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ReleaseDaoWSImplTest {
	@Autowired
	ReleaseDao releaseDaoWSImpl;

    private Exception exception = new Exception("An exception is needed.");

    @Test
    public void testReleaseDaoVote() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        
        params.put("release", StaticValues.releaseGid0);
		
		params.put("inc", "ratings");

        sendSomeRequest("recording", params);
        
        params.put("release", StaticValues.releaseGid1);
        
        params.put("inc", "ratings");
        
        sendSomeRequest("recording", params);

    }
    
    private void sendSomeRequest(String msg , Map<String, String> params) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException{
        MusicTagServiceAPI.sendRequest(msg, params);
    }
    
    @Test(expected = NetContentNotFoundException.class)
    public void testThrowVoteException() throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException {
    	releaseDaoWSImpl.vote(null);
    }
    
    @Test
    public void testReleaseDaoArtistinfo() throws Exception {
    	
        Map<String, String> params = new HashMap<String, String>();
		
		params.put("inc", "artists");

        sendSomeRequest("release/"+StaticValues.releaseGid0, params);
        
        sendSomeRequest("release/"+StaticValues.releaseGid1, params);
        
    }
    
   
    
    @Test(expected = NetContentNotFoundException.class)
    public void testThrowArtistinfoException() throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException{
    	releaseDaoWSImpl.vote(null);
    }
    
    
    public void testReleaseDaoReleasevote() throws Exception {
    	
        Map<String, String> params = new HashMap<String, String>();
		
		params.put("inc", "release-groups");

        sendSomeRequest("release/"+StaticValues.releaseGid0, params);
        
        params.put("inc", "ratings");

        sendSomeRequest("release-group/"+StaticValues.releaseGroupGid0, params);

    } 
    
    @Test(expected = NetContentNotFoundException.class)
    public void testThrowReleasevoteException() throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException{
    	releaseDaoWSImpl.vote(null);
    }
    
    

    
}

