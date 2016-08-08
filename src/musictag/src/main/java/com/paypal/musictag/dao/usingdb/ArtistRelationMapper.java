package com.paypal.musictag.dao.usingdb;

import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

public interface ArtistRelationMapper {
	void findArtistCredit(@Param(value = "artistGid") UUID artistGid, ResultHandler<Map<String, Object>> handler);
}
