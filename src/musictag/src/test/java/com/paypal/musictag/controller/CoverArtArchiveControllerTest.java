package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("CoverArtArchiveControllerTest")
public class CoverArtArchiveControllerTest {

	@Autowired
	private CoverArtArchiveController coverArtArchiveController;

	@Test
	public void testReleaseCover() throws IOException {
		Map<?, ?> map = coverArtArchiveController.releaseCover(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test
	public void testReleaseCoverException() throws IOException{
		Map<?, ?> map = coverArtArchiveController.releaseCover(StaticValues.releaseGidNoCoverArt);
		assertEquals(map.get("success"), true);
	}

	@Test
	public void testReleaseCoverBadRequestException() throws IOException{
		Map<?, ?> map = coverArtArchiveController.releaseCover("not exist gid");
		assertEquals(map.get("success"), true);
	}
	
	@Test
	public void testReleaseGroupCover() throws IOException {
		Map<?, ?> map = coverArtArchiveController.releaseGroupCover(StaticValues.releaseGroupGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test
	public void testReleaseGroupCoverException() throws IOException{
		Map<?, ?> map = coverArtArchiveController.releaseGroupCover(StaticValues.releaseGroupGidNoCoverArt);
		assertEquals(map.get("success"), true);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testReleaseGroupCoverBadRequestException() throws IOException{
		coverArtArchiveController.releaseGroupCover("not exist gid");
	}
}
