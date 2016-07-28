package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagServiceAPI;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ReleaseDaoWSImplTest {
	@Autowired
	ReleaseDao releaseDaoWSImpl;

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
    
    private void sendSomeRequest(String msg , Map<String, String> params) throws IOException{
        MusicTagServiceAPI.sendRequest(msg, params);
    }
    
    @Test(expected = NetBadRequestException.class)
    public void testThrowVoteException() throws IOException {
    	releaseDaoWSImpl.vote(null);
    }
    
    @Test
    public void testReleaseDaoArtistinfo() throws Exception {
    	
        Map<String, String> params = new HashMap<String, String>();
		
		params.put("inc", "artists");

        sendSomeRequest("release/"+StaticValues.releaseGid0, params);
        
        sendSomeRequest("release/"+StaticValues.releaseGid1, params);
        
    }
    
   
    
    @Test(expected = NetBadRequestException.class)
    public void testThrowArtistinfoException() throws IOException{
    	releaseDaoWSImpl.vote(null);
    }
    
    @Test
    public void testReleaseDaoReleasevote() throws Exception {
    	
        Map<String, String> params = new HashMap<String, String>();
		
		params.put("inc", "release-groups");

        sendSomeRequest("release/"+StaticValues.releaseGid0, params);
        
        params.put("inc", "ratings");

        sendSomeRequest("release-group/"+StaticValues.releaseGroupGid0, params);

    } 
    
    @Test(expected = NetBadRequestException.class)
    public void testThrowReleasevoteException() throws IOException{
    	releaseDaoWSImpl.vote(null);
    }
    
    

    
}

