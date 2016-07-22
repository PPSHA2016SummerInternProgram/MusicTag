
package com.paypal.musictag.service.impl;


import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.service.ReleaseService;

 
@Service("releaseServiceImpl")
public class ReleaseServiceImpl implements ReleaseService{
	@Autowired
	private ReleaseDao releaseDaoWSImpl;
	
	@Override
	public Map<String, Object> vote(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException{
		return releaseDaoWSImpl.vote(gid);
	}
	
	@Override
	public Map<String, Object> artistinfo(String gid) throws Exception{
		return releaseDaoWSImpl.artistinfo(gid);
	}
	
	@Override
	public Map<String, Object> releasevote(String gid) throws Exception{
		return releaseDaoWSImpl.releasevote(gid);
	}
}

