package com.paypal.musictag.dao.usingdb;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

public interface CountryAndDateMapper {

	List<Map<String, Object>> releaseCountryAndDate(@Param(value = "gids") List<UUID> gids);

	List<Map<String, Object>> recordingCountryAndDate(@Param(value = "gids") List<UUID> gids);
}
