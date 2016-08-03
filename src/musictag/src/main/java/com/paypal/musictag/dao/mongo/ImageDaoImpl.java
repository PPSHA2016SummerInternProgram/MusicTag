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
import com.paypal.musictag.dao.mongo.mapper.LastfmArtist;

@Service("imageDaoImpl")
public class ImageDaoImpl implements ImageDao {

	@Resource(name = "mongoTemplate")
	private MongoTemplate mongoTemplate;

	@Override
	public List<Map<String, Object>> artistImagesFromLastfm(String artistGid) {

		Query searchUserQuery = new Query(Criteria.where("gid").is(artistGid));

		LastfmArtist artist = mongoTemplate.findOne(searchUserQuery, LastfmArtist.class);

		List<Map<String, Object>> images = new ArrayList<>();
		if (artist != null && artist.getImage() != null) {
			for (Object image : artist.getImage()) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) image;
				String src = String.valueOf(map.get("#text"));
				if (src != null && src.length() != 0) {
					Map<String, Object> imageMap = new HashMap<>();
					imageMap.put("size", map.get("size"));
					imageMap.put("src", src);
					images.add(imageMap);
				}
			}
		}

		return images;
	}

}