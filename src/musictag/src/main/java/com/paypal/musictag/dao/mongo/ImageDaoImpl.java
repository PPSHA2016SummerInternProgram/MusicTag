package com.paypal.musictag.dao.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ImageDao;
import com.paypal.musictag.dao.mongo.mapper.CoverartInfo;
import com.paypal.musictag.dao.mongo.mapper.CoverartNotFound;
import com.paypal.musictag.dao.mongo.mapper.LastfmAlbum;
import com.paypal.musictag.dao.mongo.mapper.LastfmArtist;

@Service("imageDaoImpl")
public class ImageDaoImpl implements ImageDao {

	@Resource(name = "mongoTemplate")
	private MongoTemplate mongoTemplate;

	@Override
	public List<Map<String, Object>> artistImagesFromLastfm(String artistGid) {

		Query searchQuery = new Query(Criteria.where("gid").is(artistGid));

		LastfmArtist artist = mongoTemplate.findOne(searchQuery, LastfmArtist.class);

		List<Map<String, Object>> images;
		if (artist != null && artist.getImage() != null) {
			images = extractImages(artist.getImage());
		} else {
			images = new ArrayList<>();
		}

		return images;
	}

	@Override
	public List<Map<String, Object>> albumImagesFromLastfm(String albumGid) {

		Query searchQuery = new Query(Criteria.where("gid").is(albumGid));

		LastfmAlbum albums = mongoTemplate.findOne(searchQuery, LastfmAlbum.class);

		List<Map<String, Object>> images;
		if (albums != null && albums.getImage() != null) {
			images = extractImages(albums.getImage());
		} else {
			images = new ArrayList<>();
		}

		return images;
	}

	private List<Map<String, Object>> extractImages(List<?> images) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (Object image : images) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) image;
			String src = String.valueOf(map.get("#text"));
			if (src != null && src.length() != 0) {
				Map<String, Object> imageMap = new HashMap<>();
				imageMap.put("size", map.get("size"));
				imageMap.put("src", src);
				result.add(imageMap);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveImageInfoToCoverart(String mbid, Map<String, Object> info) {

		if (imageInfoFromCoverart(mbid) == null) {
			CoverartInfo coverart = new CoverartInfo();
			coverart.setGid(mbid);
			coverart.setImages((List<Map<String, Object>>) info.get("images"));
			mongoTemplate.save(coverart);
		}
	}

	@Override
	public Map<String, Object> imageInfoFromCoverart(String mbid) {
		Query searchQuery = new Query(Criteria.where("gid").is(mbid));
		CoverartInfo coverart = mongoTemplate.findOne(searchQuery, CoverartInfo.class);
		if (coverart != null && coverart.getImages() != null && !coverart.getImages().isEmpty()) {
			Map<String, Object> result = new HashMap<>();
			result.put("images", coverart.getImages());
			return result;
		}
		return null;
	}

	@Override
	public void saveNotFoundToCoverart(String mbid) {

		if (!isNotFound(mbid)) {
			CoverartNotFound coverart = new CoverartNotFound();
			coverart.setGid(mbid);
			mongoTemplate.save(coverart);
		}
	}

	@Override
	public boolean isNotFound(String mbid) {
		Query searchQuery = new Query(Criteria.where("gid").is(mbid));
		CoverartNotFound coverart = mongoTemplate.findOne(searchQuery, CoverartNotFound.class);
		return coverart != null;
	}

}