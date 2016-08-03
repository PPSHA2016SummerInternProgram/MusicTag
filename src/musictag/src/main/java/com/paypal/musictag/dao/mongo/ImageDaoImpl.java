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
import com.paypal.musictag.dao.mongo.mapper.LastfmAlbum;
import com.paypal.musictag.dao.mongo.mapper.LastfmArtist;

@Service("imageDaoImpl")
public class ImageDaoImpl implements ImageDao {

	@Resource(name = "mongoTemplate")
	private MongoTemplate mongoTemplate;

	@Override
	public List<Map<String, Object>> artistImagesFromLastfm(String artistGid) {

		Query searchUserQuery = new Query(Criteria.where("gid").is(artistGid));

		LastfmArtist artist = mongoTemplate.findOne(searchUserQuery, LastfmArtist.class);

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

		Query searchUserQuery = new Query(Criteria.where("gid").is(albumGid));

		LastfmAlbum albums = mongoTemplate.findOne(searchUserQuery, LastfmAlbum.class);

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

}