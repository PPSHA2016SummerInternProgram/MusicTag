package com.paypal.musictag.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void testProfile() throws IOException {
		Map<?, ?> map = artistController.profile(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetContentNotFoundException.class)
	public void testProfileException() throws IOException{
		artistController.profile(StaticValues.artistGidNoWikiExtract);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testProfileBadRequest() throws IOException{
		artistController.profile(null);
	}

	@Test
	public void testRelLinks() throws IOException {
		Map<?, ?> map = artistController.relLinks(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testRelLinksException() throws IOException{
		artistController.relLinks(null);
	}

	@Test
	public void testImage() throws IOException {
		Map<?, ?> map = artistController.image(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected = NetContentNotFoundException.class)
	public void testImageException() throws IOException {
		artistController.image(StaticValues.artistGidNoCommonImg);
	}
	
	@Test(expected = NetBadRequestException.class)
	public void testImageBadRequestException() throws IOException {
		artistController.image(null);
	}

	@Test
	public void testBasicInfo() throws IOException {
		Map<?, ?> map = artistController.basicInfo(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testBasicInfoException() throws IOException{
		artistController.basicInfo(null);
	}

	@Test
	public void testReleaseGroup() throws IOException {
		Map<?, ?> map = artistController.releaseGroup(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
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
	public void testReleaseGroupPagedException() throws Exception{
		artistController.releaseGroup(null);
		artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, null, null);
		artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "something", null);
		artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "date", "something");
	}

	@Test
	public void testArtistOverview() {
		String page = artistController.artistOverview(null);
		assertEquals(page, "/WEB-INF/pages/artist-overview.jsp");
	}
}
