package com.paypal.musictag.service.impl;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ArtistServiceImplTest {
	@Autowired
	ArtistService artistServiceImpl;

	@Test
	public void testTooltipInfo() throws IOException {
		String gid = StaticValues.randomUUID();
		artistServiceImpl.tooltipInfo(gid);
	}

	@Test
	public void testReleaseGroup() throws IOException {
		artistServiceImpl.releaseGroup(StaticValues.artistGidNoReleaseGroup);
	}

	@Test
	public void testReleaseGroupPaged0() throws Exception {
		artistServiceImpl.releaseGroupPaged(StaticValues.artistGid1, 0, 1000, "date", "asc");
		artistServiceImpl.releaseGroupPaged(StaticValues.artistGid0, 0, 1, "date", "desc");
		artistServiceImpl.releaseGroupPaged(StaticValues.artistGid0, 0, 1, "name", "desc");
		artistServiceImpl.releaseGroupPaged(StaticValues.artistGid0, 0, 1, "name", "desc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReleaseGroupPaged1() throws Exception {
		artistServiceImpl.releaseGroupPaged(StaticValues.artistGid0, 0, 1, "date", "unknown");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReleaseGroupPaged3() throws Exception {
		artistServiceImpl.releaseGroupPaged(StaticValues.artistGid0, 0, 1, "unknown", "desc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testArtistCooperations0() {
		artistServiceImpl.artistCooperations(null, null, null);
	}
}
