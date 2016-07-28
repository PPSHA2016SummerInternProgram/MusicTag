package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ArtistDaoWSImplTest {

	@Autowired
	ArtistDao artistDaoWSImpl;

	@Test
	public void testProfile() throws IOException {
		artistDaoWSImpl.profile(StaticValues.artistGid0);
		artistDaoWSImpl.profile(StaticValues.artistGid1);
	}

	@Test(expected = NetContentNotFoundException.class)
	public void testProfileException() throws IOException {
		artistDaoWSImpl.profile(StaticValues.artistGidNoWikiExtract);
	}
	
	@Test(expected = NetBadRequestException.class)
	public void testProfileBadRequestException() throws IOException {
		artistDaoWSImpl.profile(null);
	}

	@Test
	public void testRelLinks() throws IOException {
		artistDaoWSImpl.relLinks(StaticValues.artistGid0);
		artistDaoWSImpl.relLinks(StaticValues.artistGid1);
	}

	@Test(expected = NetBadRequestException.class)
	public void testRelLinksBadRequestException() throws IOException {
		artistDaoWSImpl.relLinks(null);
	}

	@Test
	public void testRelLinksImage() throws IOException {
		artistDaoWSImpl.image(StaticValues.artistGid0);
		artistDaoWSImpl.image(StaticValues.artistGid1);
	}

	@Test(expected = NetContentNotFoundException.class)
	public void testRelLinksImageException() throws IOException {
		artistDaoWSImpl.image(StaticValues.artistGidNoCommonImg);
	}
	
	@Test(expected = NetBadRequestException.class)
	public void testRelLinksImageBadRequestException() throws IOException {
		artistDaoWSImpl.image(null);
	}

	@Test
	public void testBasicInfo() throws IOException {
		artistDaoWSImpl.basicInfo(StaticValues.artistGid0);
		artistDaoWSImpl.basicInfo(StaticValues.artistGid1);
	}

	@Test(expected = NetBadRequestException.class)
	public void testBasicInfoException0() throws IOException {
		artistDaoWSImpl.basicInfo("some one not exists");
	}

	@Test(expected = NetBadRequestException.class)
	public void testBasicInfoException1() throws IOException {
		artistDaoWSImpl.basicInfo(null);
	}

	@Test
	public void testReleaseGroup() throws IOException {
		artistDaoWSImpl.releaseGroup(StaticValues.artistGid0);
		artistDaoWSImpl.releaseGroup(StaticValues.artistGid1);

	}

	@Test(expected = NetBadRequestException.class)
	public void testReleaseGroupBadRequestException0() throws IOException {
		artistDaoWSImpl.releaseGroup("some one not exists");
	}

	@Test(expected = NetBadRequestException.class)
	public void testReleaseGroupBadRequestException1() throws IOException {
		artistDaoWSImpl.releaseGroup(null);
	}
}
