package com.paypal.musictag.dao.usingdb;

import java.util.Map;

public interface DistributionMapper {
	Integer editAmount(Map<String, Object> param);

	Integer recordingAmount(Map<String, Object> param);

	Integer releaseAmount(Map<String, Object> param);

	Integer activeYears(Map<String, Object> param);

	Integer contactsAmount(Map<String, Object> param);

	Integer countryAmount(Map<String, Object> param);
}
