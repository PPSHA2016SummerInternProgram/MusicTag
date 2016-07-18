package com.paypal.musictag.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.dao.usingdb.ReleaseGroupMapper;
import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.util.ReleaseesCountsMapResultHandler;

@Service("artistServiceImpl")
public class ArtistServiceImpl implements ArtistService {

	@Autowired
	private ArtistDao artistDaoWSImpl;
	@Autowired
	private ReleaseGroupMapper releaseGroupMapper;

	public Map<String, Object> basicInfo(String gid) throws Exception {
		return artistDaoWSImpl.basicInfo(gid);
	}

	public Map<String, Object> releaseGroup(String artistGid) throws Exception {
		Map<String, Object> res = artistDaoWSImpl.releaseGroup(artistGid);
		List<Map<String, Object>> releaseGroups = (List<Map<String, Object>>) res.get("release-groups");
		if (releaseGroups.size() == 0) {
			return res;
		}
		List<UUID> releaseGroupList = new ArrayList<>(releaseGroups.size());
		for (Map<String, Object> releaseGroup : releaseGroups) {
			releaseGroupList.add(UUID.fromString((String) releaseGroup.get("id")));
		}
		ReleaseesCountsMapResultHandler handler = new ReleaseesCountsMapResultHandler();
		releaseGroupMapper.releasesCounts(releaseGroupList, handler);
		Map<String, Long> releasesCountsMap = handler.getReleaseCountMap();
		for (Map<String, Object> releaseGroup : releaseGroups) {
			releaseGroup.put("count", releasesCountsMap.get(releaseGroup.get("id")));
		}
		return res;
	}
}
