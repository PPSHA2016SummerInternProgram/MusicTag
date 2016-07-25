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
@Service("CoverArtArchiveControllerTest")
public class CoverArtArchiveControllerTest {

	@Autowired
	private CoverArtArchiveController coverArtArchiveController;

	@Test
	public void testReleaseCover() {
		Map<?, ?> map = coverArtArchiveController.releaseCover(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = coverArtArchiveController.releaseCover(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testReleaseGroupCover() {
		Map<?, ?> map = coverArtArchiveController.releaseGroupCover(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = coverArtArchiveController.releaseGroupCover(StaticValues.releaseGroupGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
}
