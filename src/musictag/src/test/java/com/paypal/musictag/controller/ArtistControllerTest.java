package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("ArtistControllerTest")
public class ArtistControllerTest {

	@Autowired
	ArtistController artistController;
	
	@Test
	public void testArtistAreasDetails() throws IOException{
		artistController.artistAreasDetails(StaticValues.artistGid0, "China");
		artistController.artistAreasDetails(StaticValues.artistGid0, "Unknown Area");
	}
	
	@Test
	public void testArtistEdit() throws IOException{
		artistController.artistEdit(StaticValues.artistGid0);
	}

	@Test
	public void testArtistLyricists() throws IOException{
		artistController.artistLyricists(StaticValues.artistGid0);
	}

	@Test
	public void testArtistComposers() throws IOException{
		artistController.artistComposers(StaticValues.artistGid0);
	}

	@Test
	public void testTooltipInfo() throws IOException{
		artistController.tooltipInfo(StaticValues.artistGid0);
	}

	@Test
	public void testArtistCreditCount() throws IOException{
		artistController.artistCreditCount(StaticValues.artistGid0);
	}
	
	@Test
	public void testDistributionScores0() throws IOException{
		artistController.distributionScores(StaticValues.artistGid0);
		artistController.distribution(StaticValues.artistGid0, "edit_amount");
		artistController.distribution(StaticValues.artistGid0, "recording_amount");
		artistController.distribution(StaticValues.artistGid0, "release_amount");
		artistController.distribution(StaticValues.artistGid0, "active_years");
		artistController.distribution(StaticValues.artistGid0, "contacts_amount");
		artistController.distribution(StaticValues.artistGid0, "country_amount");
		artistController.distribution(StaticValues.artistGid0, "listener_amount");
		artistController.distribution(StaticValues.artistGid0, "play_amount");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDistributionScores1() throws IOException{
		artistController.distribution(StaticValues.artistGid0, "");
	}
	
	@Test
	public void testArtistAreas() throws IOException{
		Map<?,?> map = artistController.artistAreas(StaticValues.artistGid0);
		assertEquals(map.get("success"),true);
		assertNotEquals(map.get("data"),null);
	}
	
	@Test
	public void testProfile() throws IOException {
		Map<?, ?> map = artistController.profile(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test(expected = NetContentNotFoundException.class)
	public void testProfileException() throws IOException {
		artistController.profile(StaticValues.artistGidNoWikiExtract);
	}

	@Test(expected = NetBadRequestException.class)
	public void testProfileBadRequest() throws IOException {
		artistController.profile(null);
	}

	@Test
	public void testRelLinks() throws IOException {
		Map<?, ?> map = artistController.relLinks(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test(expected = NetBadRequestException.class)
	public void testRelLinksException() throws IOException {
		artistController.relLinks(null);
	}

	@Test
	public void testImage() throws IOException {
		Map<?, ?> map = artistController.image(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testImageException() throws IOException {
		Map<?, ?> map = artistController.image(StaticValues.artistGidNoCommonImg);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);

	}

	@Test
	public void testImageBadRequestException() throws IOException {
		Map<?, ?> map = artistController.image(null);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testBasicInfo() throws IOException {
		Map<?, ?> map = artistController.basicInfo(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test(expected = NetBadRequestException.class)
	public void testBasicInfoException() throws IOException {
		artistController.basicInfo(null);
	}

	@Test
	public void testReleaseGroup() throws IOException {
		Map<?, ?> map = artistController.releaseGroup(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test(expected = NetBadRequestException.class)
	public void testReleaseGroupException() throws IOException {
		artistController.releaseGroup(null);
	}

	@Test
	public void testReleaseGroupPaged() throws Exception {
		Map<?, ?> map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "date", "asc");
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
		map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "date", "desc");
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
		map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "name", "asc");
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
		map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "name", "desc");
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test(expected = Exception.class)
	public void testReleaseGroupPagedException() throws Exception {
		artistController.releaseGroup(null);
		artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, null, null);
		artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "something", null);
		artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "date", "something");
	}

	@Test
	public void testArtistOverview() {
		HttpServletRequest request = new MockHttpServletRequest();
		String page = artistController.artistOverview(null, StaticValues.artistGid0, request);
		assertEquals(page, "/WEB-INF/pages/artist-overview.jsp");
	}

	@Test
	public void testArtistRelationship() {
	    HttpServletRequest request = new MockHttpServletRequest();
		String page = artistController.artistRelationship(null, StaticValues.artistGid0, request);
		assertEquals(page, "/WEB-INF/pages/artist_relationship.jsp");
	}

	@Test
	public void testArtistPopularity() {
	    HttpServletRequest request = new MockHttpServletRequest();
		String page = artistController.artistPopularity(null, StaticValues.artistGid0, request);
		assertEquals(page, "/WEB-INF/pages/artist_popularity.jsp");
	}

	@Test
	public void testArtistInfluence() {
	    HttpServletRequest request = new MockHttpServletRequest();
		String page = artistController.artistInfluence(null, StaticValues.artistGid0, request);
		assertEquals(page, "/WEB-INF/pages/artist_influence.jsp");
	}

	@Test
	public void testArtistProductivity() {
	    HttpServletRequest request = new MockHttpServletRequest();
		String page = artistController.artistProductivity(null, StaticValues.artistGid0, request);
		assertEquals(page, "/WEB-INF/pages/artist_productivity.jsp");
	}

	@Test
	public void testArtistActiveSpan() {
	    HttpServletRequest request = new MockHttpServletRequest();
		String page = artistController.artistActiveSpan(null, StaticValues.artistGid0, request);
		assertEquals(page, "/WEB-INF/pages/artist_active_span.jsp");
	}
	
	@Test
	public void testArtsitCooperations(){
		Integer sid = 35536; //周杰伦
		Integer tid = 467348;//Terdsak Janpan
		artistController.artistCooperations(sid, tid, "credit");
		artistController.artistCooperations(sid, tid, "lyricist");
		artistController.artistCooperations(sid, tid, "composer");
	}
}
