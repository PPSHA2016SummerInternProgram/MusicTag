package com.paypal.musictag.controller;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("ArtistControllerTest")
public class ArtistControllerTest {

	@Autowired
	ArtistController artistController;

	@Test
	public void testProfile() {
		Map<?, ?> map = artistController.profile(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 401);
		map = artistController.profile(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testRelLinks() {
		Map<?, ?> map = artistController.relLinks(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = artistController.relLinks(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testImage() {
		Map<?, ?> map = artistController.image(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 401);
		map = artistController.image(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testBasicInfo() {
		Map<?, ?> map = artistController.basicInfo(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = artistController.basicInfo(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testReleaseGroup() {
		Map<?, ?> map = artistController.releaseGroup(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = artistController.releaseGroup(StaticValues.artistGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testReleaseGroupPaged() {
		Map<?, ?> map = artistController.releaseGroup(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, null, null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 406);
		map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "something", null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 406);
		map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "date", "something");
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 406);
		map = artistController.releaseGroupPaged(StaticValues.artistGid0, 0, 10, "date", "asc");
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

	@Test
	public void testArtistOverview() {
		String page = artistController.artistOverview(null);
		assertEquals(page, "/WEB-INF/pages/artist-overview.jsp");
	}
}
