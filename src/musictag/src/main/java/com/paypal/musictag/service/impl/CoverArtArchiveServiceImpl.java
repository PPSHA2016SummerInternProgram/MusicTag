package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.CoverArtArchiveDao;
import com.paypal.musictag.dao.ImageDao;
import com.paypal.musictag.dao.ReleaseGroupDao;
import com.paypal.musictag.service.CoverArtArchiveService;

@Service("coverArtArchiveServiceImpl")
public class CoverArtArchiveServiceImpl implements CoverArtArchiveService {

	@Autowired
	private CoverArtArchiveDao coverArtArchiveDaoWSImpl;

	@Autowired
	private ReleaseGroupDao releaseGroupDaoWSImpl;

	@Autowired
	private ImageDao imageDaoImpl;

	@Override
	public Map<String, Object> releaseCover(String releaseGid) throws IOException {
		
		Map<String, Object> image = albumCoverFromLastFm(releaseGid);
		if(image != null){
			return image;
		}
		
		return coverArtArchiveDaoWSImpl.releaseCover(releaseGid);
	}
	
	private Map<String, Object> albumCoverFromLastFm(String albumGid){
		List<Map<String, Object>> images = imageDaoImpl.albumImagesFromLastfm(albumGid);
		for (Map<String, Object> image : images) {
			if ("large".equals(image.get("size"))) {
				Map<String, Object> result = new HashMap<>();
				List<Object> list = new ArrayList<>();
				Map<String, Object> first = new HashMap<>();
				Map<String, Object> thumbnails = new HashMap<>();
				thumbnails.put("large", image.get("src"));
				thumbnails.put("small", image.get("src"));
				first.put("thumbnails", thumbnails);
				first.put("image", image.get("src"));
				list.add(first);
				result.put("images", list);
				return result;
			}
		}
		return null;
	}
	
	

	@Override
	public Map<String, Object> releaseGroupCover(String releaseGroupGid) throws IOException {

		Map<String, Object> data = releaseGroupDaoWSImpl.releases(releaseGroupGid);

		String firstReleaseId = null;
		if (data != null && data.get("releases") != null) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> release = (List<Map<String, Object>>) data.get("releases");
			if (!release.isEmpty()) {
				firstReleaseId = String.valueOf(release.get(0).get("id"));
			}
		}

		if (firstReleaseId != null) {
			Map<String, Object> image = albumCoverFromLastFm(firstReleaseId);
			if(image != null){
				return image;
			}
		}

		return coverArtArchiveDaoWSImpl.releaseGroupCover(releaseGroupGid);
	}

}
