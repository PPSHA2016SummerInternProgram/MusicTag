
package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.service.ReleaseService;

@Service("releaseServiceImpl")
public class ReleaseServiceImpl implements ReleaseService {
	@Autowired
	private ReleaseDao releaseDaoWSImpl;

	@Override
	public Map<String, Object> vote(String gid) throws IOException {
		return releaseDaoWSImpl.vote(gid);
	}

	@Override
	public Map<String, Object> artistinfo(String gid) throws IOException{
		return releaseDaoWSImpl.artistinfo(gid);
	}

	@Override
	public Map<String, Object> releasevote(String gid) throws IOException{
		return releaseDaoWSImpl.releasevote(gid);
	}
}
