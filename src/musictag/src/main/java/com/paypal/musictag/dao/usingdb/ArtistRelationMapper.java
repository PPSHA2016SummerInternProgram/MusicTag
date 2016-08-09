package com.paypal.musictag.dao.usingdb;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

public interface ArtistRelationMapper {
	List<Map<String, Object>> findArtistCreditLinkWithCount(@Param(value = "artistGid") UUID artistGid);

	List<Map<String, Object>> findArtistCreditNode(@Param(value="artistIds") List<Integer> ids);
}
