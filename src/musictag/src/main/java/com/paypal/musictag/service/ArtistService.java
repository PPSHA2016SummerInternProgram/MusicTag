package com.paypal.musictag.service;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

public interface ArtistService {

    Map<String, Object> profile(String gid) throws NetConnectionException;

    Map<String, Object> relLinks(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;

    Map<String, Object> image(String gid) throws NetConnectionException;

    Map<String, Object> basicInfo(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;

    Map<String, Object> releaseGroup(String artistGid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;

    /**
     * 返回分好页的ReleaseGroups。
     * 
     * @param artistGid 筛选条件
     * @param curPage 第几页（从0开始）
     * @param perPage 每页几个
     * @param orderBy 按照什么排序（支持name、date）
     * @param direction 排序的方式（支持asc、desc）
     * @return
     * @throws Exception
     */
    Map<String, Object> releaseGroupPaged(String artistGid, int curPage,
            int perPage, String orderBy, String direction) throws Exception;
}
