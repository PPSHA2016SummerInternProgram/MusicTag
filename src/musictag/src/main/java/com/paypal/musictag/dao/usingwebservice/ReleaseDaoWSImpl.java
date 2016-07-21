package com.paypal.musictag.dao.usingwebservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagServiceAPI;

@Service("releaseDaoWSImpl")
public class ReleaseDaoWSImpl implements ReleaseDao {

	
	public Map<String, Object> vote(String gid) throws Exception{
		
		Map<String, String> params = new HashMap<String, String>();

		params.put("release", gid);
		
		params.put("inc", "ratings");

		Map<String, Object> result = MusicTagServiceAPI.sendRequest("recording", params);

		System.out.println(result);

		return result;
	}	
}
