package com.paypal.musictag.dao.usingdb;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.util.MusicTagUtil;
import static org.junit.Assert.assertTrue;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("ArtistRelationMapperTest")
public class ArtistRelationMapperTest {
	@Autowired
	ArtistRelationMapper artistRelationMapper;

	@Test
	public void findArtistCreditLinkWithCount() {
		UUID id = UUID.fromString(StaticValues.artistGid0);
		System.out.println(artistRelationMapper.findArtistCreditLinkWithCount(id));
	}

	@Test
	public void testFindArtistCreditNode() {
		UUID id = UUID.fromString(StaticValues.artistGid0);
		List<Integer> ids = MusicTagUtil.extractUniqueIds(artistRelationMapper.findArtistCreditLinkWithCount(id));
		System.out.println(artistRelationMapper.findArtistCreditNode(ids));
	}
	
	@Test
	public void testGetArtistCountInFirstLevel(){
		UUID id = UUID.fromString(StaticValues.artistGid0);
		System.out.println(artistRelationMapper.getArtistCountInFirstLevel(id));
	}
	@Test
	public void testGetReleaseCount() {
		UUID id = UUID.fromString(StaticValues.artistGid0);
        assertTrue(artistRelationMapper.getReleaseCount(id) > 0 );
	}

	@Test
	public void testGetRecordingCount() {
		UUID id = UUID.fromString(StaticValues.artistGid0);
		assertTrue(artistRelationMapper.getRecordingCount(id) > 0);
	}
}
