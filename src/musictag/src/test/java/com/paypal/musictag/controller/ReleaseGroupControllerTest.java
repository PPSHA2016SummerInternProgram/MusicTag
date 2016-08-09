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
@Service("ReleaseGroupControllerTest")
public class ReleaseGroupControllerTest {

	@Autowired
	private ReleaseGroupController releaseGroupController;

	@Test
	public void testReleases() throws IOException {
		Map<?, ?> map = releaseGroupController.releases(StaticValues.releaseGroupGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	
	@Test
	public void testimage() throws IOException {
		Map<?, ?> map = releaseGroupController.image(StaticValues.releaseGroupGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testReleaseException() throws IOException{
		releaseGroupController.releases(null);
	}
}
