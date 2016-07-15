package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.CoverArtArchiveDao;

@Service("coverArtArchiveDaoWSImpl")
public class CoverArtArchiveDaoWSImpl implements CoverArtArchiveDao {

	@Override
	public Map<String, Object> releaseCover(String releaseGid) throws IOException {
		return CoverArtArchiveServiceAPI.sendRequest("release/", releaseGid);
	}

	@Override
	public Map<String, Object> releaseGroupCover(String releaseGroupGid) throws IOException {
		return CoverArtArchiveServiceAPI.sendRequest("release-group/", releaseGroupGid);
	}
}
