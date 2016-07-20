package com.paypal.musictag.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public Map<String, Object> profile(String gid) throws Exception {
        return artistDaoWSImpl.profile(gid);
    }

    @Override
    public Map<String, Object> relLinks(String gid) throws Exception {
        return artistDaoWSImpl.relLinks(gid);
    }

    @Override
    public Map<String, Object> image(String gid) throws Exception {
        return artistDaoWSImpl.image(gid);
    }

    public Map<String, Object> basicInfo(String gid) throws Exception {
        return artistDaoWSImpl.basicInfo(gid);
    }

    public Map<String, Object> releaseGroup(String artistGid) throws Exception {
        Map<String, Object> res = artistDaoWSImpl.releaseGroup(artistGid);
        List<Map<String, Object>> releaseGroups = (List<Map<String, Object>>) res
                .get("release-groups");
        if (releaseGroups.size() == 0) {
            return res;
        }
        List<UUID> releaseGroupList = new ArrayList<>(releaseGroups.size());
        for (Map<String, Object> releaseGroup : releaseGroups) {
            releaseGroupList.add(UUID.fromString((String) releaseGroup
                    .get("id")));
        }
        ReleaseesCountsMapResultHandler handler = new ReleaseesCountsMapResultHandler();
        releaseGroupMapper.releasesCounts(releaseGroupList, handler);
        Map<String, Long> releasesCountsMap = handler.getReleaseCountMap();
        for (Map<String, Object> releaseGroup : releaseGroups) {
            releaseGroup.put("count",
                    releasesCountsMap.get(releaseGroup.get("id")));
        }
        return res;
    }

    @Override
    public Map<String, Object> releaseGroupPaged(String artistGid, int curPage,
            int perPage, String orderBy, String direction) throws Exception {

        curPage = Math.max(curPage, 0);

        // at most 100, at least 1
        perPage = Math.min(perPage, 100);
        perPage = Math.max(perPage, 1);

        // only support order by name or date
        if (!"name".equals(orderBy) && !"date".equals(orderBy)) {
            throw new Exception("Don't support order by: " + orderBy);
        }

        // only support asc & desc
        if (!"asc".equals(direction) && !"desc".equals(direction)) {
            throw new Exception("Don't support sort direction: " + direction);
        }

        UUID gid = UUID.fromString(artistGid);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("artistGid", gid);
        param.put("offset", curPage * perPage);
        param.put("amount", perPage);
        param.put("order_by", orderBy);
        param.put("direction", direction);
        List<Map<String, Object>> groups = releaseGroupMapper
                .findReleasesByReleaseGroup(param);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("release-group-total-count", 0);
        List<Map<String, Object>> resGroups = new ArrayList<Map<String, Object>>(
                groups.size());
        for (Map<String, Object> group : groups) {
            result.put("release-group-total-count", group.get("total_row_count"));
            Map<String, Object> resGroup = new HashMap<String, Object>();
            resGroup.put("id", group.get("gid"));
            resGroup.put("primary-type", group.get("primary_type"));
            resGroup.put("title", group.get("name"));
            resGroup.put("release-count", group.get("count"));
            String date = "";
            Object year = group.get("first_release_date_year");
            Object month = group.get("first_release_date_month");
            Object day = group.get("first_release_date_day");
            if (year != null) {
                date = "" + year;
                if (month != null) {
                    date += "-" + month;
                    if (day != null) {
                        date += "-" + day;
                    }
                }
            }
            resGroup.put("first-release-date", date);
            resGroups.add(resGroup);
        }
        result.put("release-groups", resGroups);

        return result;
    }

}
