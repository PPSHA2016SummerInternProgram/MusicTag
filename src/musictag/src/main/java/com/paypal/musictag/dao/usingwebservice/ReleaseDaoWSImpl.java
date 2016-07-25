package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagServiceAPI;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

@Service("releaseDaoWSImpl")
public class ReleaseDaoWSImpl implements ReleaseDao {

	
	public Map<String, Object> vote(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException{
		
		Map<String, String> params = new HashMap<String, String>();

		params.put("release", gid);
		
		params.put("inc", "ratings");

		Map<String, Object> result = MusicTagServiceAPI.sendRequest("recording", params);

		return result;
	}	
	
	public Map<String, Object> artistinfo(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException {
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("inc", "artists");

		Map<String, Object> result = MusicTagServiceAPI.sendRequest("release/"+gid, params);

		return result;
	}
	
	public Map<String, Object> releasevote(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException{
		
		Map<String, String> paramsFindReleaseGroup = new HashMap<String, String>();
		
		paramsFindReleaseGroup.put("inc", "release-groups");

		Map<String, Object> resultreleaseGroup = MusicTagServiceAPI.sendRequest("release/"+gid, paramsFindReleaseGroup);

		
		@SuppressWarnings("unchecked")
		Map<String, Object> releaseGroup = (Map<String, Object>)resultreleaseGroup.get("release-group");
		

		String releaseGroupGid= (String)releaseGroup.get("id");
		
		

		Map<String, String> paramsFindReleaseVote = new HashMap<String, String>();
		
		paramsFindReleaseVote.put("inc", "ratings");

		Map<String, Object> result = MusicTagServiceAPI.sendRequest("release-group/"+releaseGroupGid, paramsFindReleaseVote);
		
		return result;

	}
	
}
