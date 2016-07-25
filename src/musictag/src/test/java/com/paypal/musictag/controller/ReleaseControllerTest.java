package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
@Service("ReleaseControllerTest")
public class ReleaseControllerTest {

	@Autowired
	private ReleaseController releaseController;

	@Test
	public void testTracklist() {
		Map<?, ?> map = releaseController.tracklist(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = releaseController.tracklist(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testArtistinfo() {
		Map<?, ?> map = releaseController.artistinfo(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = releaseController.artistinfo(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testReleasevote() {
		Map<?, ?> map = releaseController.releasevote(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = releaseController.releasevote(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testTracklistPage() {
		Map<?, ?> map = releaseController.tracklist(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = releaseController.tracklist(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
}
