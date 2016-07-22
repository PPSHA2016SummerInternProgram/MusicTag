
package com.paypal.musictag.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.service.ReleaseService;

 
@Service("releaseServiceImpl")
public class ReleaseServiceImpl implements ReleaseService{
	@Autowired
	private ReleaseDao releaseDaoWSImpl;
	
	
	@Override
	public Map<String, Object> vote(String gid) throws Exception{
		return releaseDaoWSImpl.vote(gid);
	}
}

