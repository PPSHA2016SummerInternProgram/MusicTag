package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ArtistDaoWSImplTest {

	@Autowired
	ArtistDao artistDaoWSImpl;

	@Test
	public void testProfile() throws NetConnectionException {
		artistDaoWSImpl.profile(StaticValues.artistGid0);
		artistDaoWSImpl.profile(StaticValues.artistGid1);
	}

	@Test(expected = NetConnectionException.class)
	public void testProfileException() throws NetConnectionException {
		artistDaoWSImpl.profile(null);
	}

	@Test
	public void testRelLinks() throws NetConnectionException, NetContentNotFoundException, JsonMappingException,
			MalformedURLException, ProtocolException {
		artistDaoWSImpl.relLinks(StaticValues.artistGid0);
		artistDaoWSImpl.relLinks(StaticValues.artistGid1);
	}

	@Test(expected = NetContentNotFoundException.class)
	public void testRelLinksException() throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException {
		artistDaoWSImpl.relLinks(null);
	}

	@Test
	public void testRelLinksImage() throws NetConnectionException {
		artistDaoWSImpl.image(StaticValues.artistGid0);
		artistDaoWSImpl.image(StaticValues.artistGid1);
	}

	@Test(expected = NetConnectionException.class)
	public void testRelLinksImageException() throws NetConnectionException {
		artistDaoWSImpl.image(null);
	}

	@Test
	public void testBasicInfo() throws NetConnectionException, NetContentNotFoundException, JsonMappingException,
			MalformedURLException, ProtocolException {
		artistDaoWSImpl.basicInfo(StaticValues.artistGid0);
		artistDaoWSImpl.basicInfo(StaticValues.artistGid1);
	}

	@Test(expected = NetContentNotFoundException.class)
	public void testBasicInfoException0() throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException {
		artistDaoWSImpl.basicInfo("some one not exists");
	}

	@Test(expected = NetContentNotFoundException.class)
	public void testBasicInfoException1() throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException {
		artistDaoWSImpl.basicInfo(null);
	}

	@Test
	public void testReleaseGroup() throws NetConnectionException, NetContentNotFoundException, JsonMappingException,
			MalformedURLException, ProtocolException {
		artistDaoWSImpl.releaseGroup(StaticValues.artistGid0);
		artistDaoWSImpl.releaseGroup(StaticValues.artistGid1);

	}

	@Test(expected = NetContentNotFoundException.class)
	public void testReleaseGroupException0() throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException {
		artistDaoWSImpl.releaseGroup("some one not exists");
	}

	@Test(expected = NetContentNotFoundException.class)
	public void testReleaseGroupException1() throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException {
		artistDaoWSImpl.releaseGroup(null);
	}
}
