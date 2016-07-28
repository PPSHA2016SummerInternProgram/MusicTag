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
@Service("ReleaseControllerTest")
public class ReleaseControllerTest {

	@Autowired
	private ReleaseController releaseController;

	@Test
	public void testTracklist() throws IOException {
		Map<?, ?> map = releaseController.tracklist(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testTracklistException() throws IOException{
		releaseController.tracklist(null);
	}

	@Test
	public void testArtistinfo() throws IOException {
		Map<?, ?> map = releaseController.artistinfo(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testArtistinfoException() throws IOException{
		releaseController.artistinfo(null);
	}

	@Test
	public void testReleasevote() throws IOException {
		Map<?, ?> map = releaseController.releasevote(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testReleasevoteException() throws IOException{
		releaseController.releasevote(null);
	}

	/*
	@Test
	public void testTracklistPage() {
		Map<?, ?> map = releaseController.tracklist(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = releaseController.tracklist(StaticValues.releaseGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	*/
	
	@Test
	public void testTracklistPage() {
		String page = releaseController.tracklistpage(null);
		assertEquals(page, "/WEB-INF/pages/release.jsp");
	}
	

}
