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
import com.paypal.musictag.exception.NetContentNotFoundException;
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
	
	@Test(expected=NetContentNotFoundException.class)
	public void testReleaseCoverException() throws IOException{
		coverArtArchiveController.releaseCover(StaticValues.releaseGidNoCoverArt);
	}

	@Test(expected=NetBadRequestException.class)
	public void testReleaseCoverBadRequestException() throws IOException{
		coverArtArchiveController.releaseCover("not exist gid");
	}
	
	@Test
	public void testReleaseGroupCover() throws IOException {
		Map<?, ?> map = coverArtArchiveController.releaseGroupCover(StaticValues.releaseGroupGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetContentNotFoundException.class)
	public void testReleaseGroupCoverException() throws IOException{
		coverArtArchiveController.releaseGroupCover(StaticValues.releaseGroupGidNoCoverArt);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testReleaseGroupCoverBadRequestException() throws IOException{
		coverArtArchiveController.releaseGroupCover("not exist gid");
	}
}
