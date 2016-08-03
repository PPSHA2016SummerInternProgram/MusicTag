package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.dao.ImageDao;
import com.paypal.musictag.dao.usingdb.ReleaseGroupMapper;
import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.util.ReleaseesCountsMapResultHandler;

@Service("artistServiceImpl")
public class ArtistServiceImpl implements ArtistService {

	@Autowired
	private ArtistDao artistDaoWSImpl;
	@Autowired
	private ReleaseGroupMapper releaseGroupMapper;

	@Autowired
	private ImageDao imageDaoImpl;

	@Override
	public Map<String, Object> profile(String gid) throws IOException {
		return artistDaoWSImpl.profile(gid);
	}

	@Override
	public Map<String, Object> relLinks(String gid) throws IOException {
		return artistDaoWSImpl.relLinks(gid);
	}

	@Override
	public Map<String, Object> image(String gid) throws IOException {
		List<Map<String, Object>> images = imageDaoImpl.artistImagesFromLastfm(gid);

		Map<String, Object> result = new HashMap<>();
		for (Map<String, Object> image : images) {
			if ("extralarge".equals(image.get("size"))) {
				result.put("commons-img", image.get("src"));
				return result;
			}
		}
		return artistDaoWSImpl.image(gid);
	}

	@Override
	public Map<String, Object> basicInfo(String gid) throws IOException {
		return artistDaoWSImpl.basicInfo(gid);
	}

	public Map<String, Object> releaseGroup(String artistGid) throws IOException {
		Map<String, Object> res = artistDaoWSImpl.releaseGroup(artistGid);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> releaseGroups = (List<Map<String, Object>>) res.get("release-groups");
		if (releaseGroups.isEmpty()) {
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

	@Override
	public Map<String, Object> releaseGroupPaged(String artistGid, int curPage, int perPage, String orderBy,
			String direction) {

		int curPageTmp = Math.max(curPage, 0);

		// at most 100, at least 1
		int perPageTmp = Math.min(perPage, 100);
		perPageTmp = Math.max(perPageTmp, 1);

		checkReleaseGroupPagedArgs(orderBy, direction);

		List<Map<String, Object>> groups = findReleaseGroupsPaged(artistGid, orderBy, direction, curPageTmp,
				perPageTmp);

		Map<String, Object> result = wrapResultForReleaseGroups(groups);

		return result;
	}

	private Map<String, Object> wrapResultForReleaseGroups(List<Map<String, Object>> groups) {
		Map<String, Object> result = new HashMap<>();
		result.put("release-group-total-count", 0);
		List<Map<String, Object>> resGroups = new ArrayList<>(groups.size());
		for (Map<String, Object> group : groups) {
			result.put("release-group-total-count", group.get("total_row_count"));
			Map<String, Object> resGroup = new HashMap<>();
			resGroup.put("id", group.get("gid"));
			resGroup.put("primary-type", group.get("primary_type"));
			resGroup.put("title", group.get("name"));
			resGroup.put("release-count", group.get("count"));
			String date = "";
			Object year = group.get("first_release_date_year");
			Object month = group.get("first_release_date_month");
			Object day = group.get("first_release_date_day");
			if (year != null && month != null && day != null) {
				date = "" + year + "-" + month + "-" + day;
			} else if (year != null && month != null) {
				date = "" + year + "-" + month;
			} else if (year != null) {
				date = "" + year;
			}
			resGroup.put("first-release-date", date);
			resGroups.add(resGroup);
		}
		result.put("release-groups", resGroups);
		return result;
	}

	private List<Map<String, Object>> findReleaseGroupsPaged(String artistGid, String orderBy, String direction,
			int curPageTmp, int perPageTmp) {
		UUID gid = UUID.fromString(artistGid);
		Map<String, Object> param = new HashMap<>();
		param.put("artistGid", gid);
		param.put("offset", curPageTmp * perPageTmp);
		param.put("amount", perPageTmp);
		param.put("order_by", orderBy);
		param.put("direction", direction);
		List<Map<String, Object>> groups = releaseGroupMapper.findReleasesByReleaseGroup(param);
		return groups;
	}

	private void checkReleaseGroupPagedArgs(String orderBy, String direction) {
		// only support order by name or date
		if (!"name".equals(orderBy) && !"date".equals(orderBy)) {
			throw new IllegalArgumentException("Don't support order by: " + orderBy);
		}

		// only support asc & desc
		if (!"asc".equals(direction) && !"desc".equals(direction)) {
			throw new IllegalArgumentException("Don't support sort direction: " + direction);
		}
	}

}
