
package com.paypal.musictag.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.paypal.musictag.dao.usingdb.ReleaseGroupMapper;
import com.paypal.musictag.service.ReleaseService;
import com.paypal.musictag.util.ReleaseesCountsMapResultHandler;
import com.paypal.musictag.dao.ReleaseDao;

 
@Service("releaseServiceImpl")
public class ReleaseServiceImpl implements ReleaseService{
	@Autowired
	private ReleaseDao releaseDaoWSImpl;
	
	
	@Override
	public Map<String, Object> vote(String gid) throws Exception{
		return releaseDaoWSImpl.vote(gid);
	}
}

