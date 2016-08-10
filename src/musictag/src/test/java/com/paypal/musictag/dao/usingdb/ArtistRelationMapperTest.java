package com.paypal.musictag.dao.usingdb;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("ArtistRelationMapperTest")
public class ArtistRelationMapperTest {
	@Autowired
	ArtistRelationMapper artistRelationMapper;

	@Test
	public void testFindArtistCredit() {
		UUID id = UUID.fromString(StaticValues.artistGid0);
		System.out.println(artistRelationMapper.findArtistCreditLinkWithCount(id));
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
