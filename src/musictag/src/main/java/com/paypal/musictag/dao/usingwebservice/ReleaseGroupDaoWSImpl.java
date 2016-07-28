package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ReleaseGroupDao;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagServiceAPI;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;

@Service("releaseGroupDaoWSImpl")
public class ReleaseGroupDaoWSImpl implements ReleaseGroupDao{

	@Override
	public Map<String, Object> releases(String releaseGroupId) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException, NetBadRequestException {
		// http://10.24.53.72:5000/ws/2/release-group/01385daa-0a2d-462a-bdbb-fe9a320d0f13?inc=releases&fmt=json
		Map<String, String> params = new HashMap<>();
		params.put("inc", "releases");
		return MusicTagServiceAPI.sendRequest("release-group/" + releaseGroupId, params);
	}
}
