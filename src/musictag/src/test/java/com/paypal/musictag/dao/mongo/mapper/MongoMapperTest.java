package com.paypal.musictag.dao.mongo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class MongoMapperTest {

	@Test
	public void testCoverartInfo() {
		CoverartInfo info = new CoverartInfo();
		info.setGid("gid");
		info.getGid();
		info.setImages(null);
		info.getImages();
		info.setSuccess(true);
		info.getSuccess();
	}

	@Test
	public void testCoverartNotFound() {
		CoverartNotFound item = new CoverartNotFound();
		item.setGid("gid");
		item.getGid();
	}

	@Test
	public void testHotInfo() {
		HotInfo item = new HotInfo();
		item.setData(null);
		item.getData();
		item.setType(null);
		item.getType();
	}

	@Test
	public void testHotRank() {
		HotRank item = new HotRank();
		item.setAmount(0);
		item.getAmount();
		item.setHot(0);
		item.getHot();
	}

	@Test
	public void testLastfmAlbum() {
		LastfmAlbumOrTrack item = new LastfmAlbumOrTrack();
		item.setGid(null);
		item.getGid();
		item.setImage(null);
		item.getImage();
		item.setListeners(0);
		item.getListeners();
		item.setMbid(null);
		item.getMbid();
		item.setName(null);
		item.getName();
		item.setPlaycount(0);
		item.getPlaycount();
		item.setUrl(null);
		item.getUrl();
	}

	@Test
	public void testLastfmArtist() {
		LastfmArtist item = new LastfmArtist();
		item.setGid(null);
		item.getGid();
		item.setImage(null);
		item.getImage();
		item.setMbid(null);
		item.getMbid();
		item.setName(null);
		item.getName();
		item.setStats(null);
		item.getStats();
		item.setUrl(null);
		item.getUrl();

	}
	
	
	@Test
	public void testDistribution(){
		Distribution item = new Distribution();
		item.getId();
		item.setId(null);
		item.setId("id");
		item.getType();
		item.setType(null);
		item.setType("type");
		item.getDescription();
		item.getData();
		item.setData(null);
		List<Object> data = new ArrayList<Object>();	
		item.setData(data);
		item.setMin(0);
		item.setMax(10);	
	}
}
