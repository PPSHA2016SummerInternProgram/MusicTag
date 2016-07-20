package com.paypal.musictag.dao.usingdb;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

public interface ReleaseGroupMapper {
	void releasesCounts(@Param(value="releaseGroupList")List<UUID> releaseGroupList, ResultHandler<Map<String, Object>> handler);
	
	/**
	 * ReleaseGroupId, OrderBy, OffSet, Amount are needed in parameters.
	 * Return the releases.
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findReleasesByReleaseGroup(Map<String, Object> param);
}
