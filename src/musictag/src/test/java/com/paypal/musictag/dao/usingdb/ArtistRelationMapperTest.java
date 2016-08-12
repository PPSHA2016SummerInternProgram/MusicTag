package com.paypal.musictag.dao.usingdb;

import java.util.List;
import java.util.Map;
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
	public void testGetCooperationsOnRecordingOfArtists(){
		Integer sid = 35536; //周杰伦
		Integer tid = 467348;//Terdsak Janpan
		System.out.println("recording coop\n" + artistRelationMapper.getCooperationsOnRecordingOfArtists(sid, tid));
	}
	
	@Test
	public void testGetCooperationsOnReleaseOfArtists(){
		Integer sid = 35536; //周杰伦
		Integer tid = 467348;//Terdsak Janpan
		System.out.println("release coop \n" + artistRelationMapper.getCooperationsOnReleaseOfArtists(sid, tid));
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

	@Test
	public void testGetReleaseYearlyDist(){
		UUID id = UUID.fromString("b2d122f9-eadb-4930-a196-8f221eeb0c66");
		List<Map<String, Object>> data = artistRelationMapper.getReleaseYearlyDist(id);
        assertTrue((int)data.get(0).get("date_year") == -1);
	}
}
