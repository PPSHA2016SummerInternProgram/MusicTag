package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.dao.ImageDao;
import com.paypal.musictag.dao.usingdb.ArtistRelationMapper;
import com.paypal.musictag.dao.usingdb.ReleaseGroupMapper;
import com.paypal.musictag.dao.usingdb.resulthandler.ReleaseesCountsMapResultHandler;
import com.paypal.musictag.dao.usingwebservice.api.LastFmAPI;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.util.CooperationType;
import com.paypal.musictag.util.MusicTagUtil;

@Service("artistServiceImpl")
public class ArtistServiceImpl implements ArtistService {

	Logger logger = LoggerFactory.getLogger(ArtistServiceImpl.class);
	@Autowired
	private ArtistDao artistDaoWSImpl;
	@Autowired
	private ReleaseGroupMapper releaseGroupMapper;
	@Autowired
	private ArtistRelationMapper artistRelationMapper;
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
	public Map<String, Object> image(String gid) {
		List<Map<String, Object>> images = imageDaoImpl.artistImagesFromLastfm(gid);

		Map<String, Object> result = new HashMap<>();
		for (Map<String, Object> image : images) {
			if ("large".equals(image.get("size"))) {
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

	@Override
	public Map<String, Object> tooltipInfo(String gid) {
		Map<String, Object> result = new HashMap<>();
		result.putAll(image(gid));
		try {
			result.putAll(basicInfo(gid));
		} catch (Exception e) {
			logger.error(null, e);
		}
		try {
			result.putAll(profile(gid));
		} catch (Exception e) {
			logger.error(null, e);
		}

		return result;
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
				date = "" + MusicTagUtil.fillPrefix(year, '0', 4) + "-" + MusicTagUtil.fillPrefix(month, '0', 2) + "-"
						+ MusicTagUtil.fillPrefix(day, '0', 2);
			} else if (year != null && month != null) {
				date = "" + MusicTagUtil.fillPrefix(year, '0', 4) + "-" + MusicTagUtil.fillPrefix(month, '0', 2);
			} else if (year != null) {
				date = "" + MusicTagUtil.fillPrefix(year, '0', 4);
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

	@Override
	public Map<String, Object> artistCooperations(Integer sid, Integer tid, CooperationType type) {
		if (type == null) {
			throw new IllegalArgumentException("CooperationType should not be null");
		}
		Map<String, Object> result = new HashMap<>();
		switch(type){
		case  CREDIT:
			result.put("recordings", artistRelationMapper.getCooperationsOnRecordingOfArtists(sid, tid));
			result.put("releases", artistRelationMapper.getCooperationsOnReleaseOfArtists(sid, tid));
			break;
		case  LYRICIST:
			result.put("releases", artistRelationMapper.getReleaseLyricCooperationsOfArtist(sid, tid));
			result.put("recordings", artistRelationMapper.getRecordingLyricCooperationsOfArtists(sid, tid));
			break;
		case  COMPOSER:
			result.put("releases", artistRelationMapper.getReleaseComposerCooperationsOfArtist(sid, tid));
			result.put("recordings", artistRelationMapper.getRecordingComposerCooperationsOfArtists(sid, tid));
			break;
		default:
			throw new AssertionError("should not reach here");
		}
		
		return result;
	}

	@Override
	public Map<String, Object> similarArtist(String gid) throws NetConnectionException, NetContentNotFoundException,
			NetBadRequestException, JsonMappingException, ProtocolException, MalformedURLException {

		return LastFmAPI.similarArtist(gid);

	}
}
