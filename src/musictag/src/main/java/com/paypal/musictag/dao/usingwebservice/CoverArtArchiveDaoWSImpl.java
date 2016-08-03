package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.CoverArtArchiveDao;
import com.paypal.musictag.dao.ImageDao;
import com.paypal.musictag.dao.usingwebservice.api.CoverArtArchiveAPI;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;

@Service("coverArtArchiveDaoWSImpl")
public class CoverArtArchiveDaoWSImpl implements CoverArtArchiveDao {

	@Autowired
	private ImageDao imageDaoImpl;

	@Override
	public Map<String, Object> releaseCover(String releaseGid) throws JsonMappingException, NetConnectionException,
			NetContentNotFoundException, MalformedURLException, ProtocolException, NetBadRequestException {

		Map<String, Object> info = imageDaoImpl.imageInfoFromCoverart(releaseGid);
		if (info != null) {
			return info;
		}

		if (imageDaoImpl.isNotFound(releaseGid)) {
			return imageNotFound();
		}

		try {
			info = CoverArtArchiveAPI.sendRequest("release/", releaseGid);
			imageDaoImpl.saveImageInfoToCoverart(releaseGid, info);
			return info;
		} catch (Exception e) {
			imageDaoImpl.saveNotFoundToCoverart(releaseGid);
			return imageNotFound();
		}
	}

	private Map<String, Object> imageNotFound() {
		Map<String, Object> notFound = new HashMap<>();
		notFound.put("error", "not found");
		return notFound;
	}

	@Override
	public Map<String, Object> releaseGroupCover(String releaseGroupGid)
			throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException,
			ProtocolException, NetBadRequestException {

		Map<String, Object> info = imageDaoImpl.imageInfoFromCoverart(releaseGroupGid);
		if (info != null) {
			return info;
		}

		if (imageDaoImpl.isNotFound(releaseGroupGid)) {
			return imageNotFound();
		}

		try {
			info = CoverArtArchiveAPI.sendRequest("release-group/", releaseGroupGid);
			imageDaoImpl.saveImageInfoToCoverart(releaseGroupGid, info);
			return info;
		} catch (Exception e) {
			imageDaoImpl.saveNotFoundToCoverart(releaseGroupGid);
			return imageNotFound();
		}
	}
}
