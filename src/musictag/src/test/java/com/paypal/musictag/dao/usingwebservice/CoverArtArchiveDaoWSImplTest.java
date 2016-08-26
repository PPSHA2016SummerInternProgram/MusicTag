package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class CoverArtArchiveDaoWSImplTest {
	@Autowired
	CoverArtArchiveDaoWSImpl coverArtArchiveDaoWSImpl;
	
	@Test
	public void testReleaseCover() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, NetBadRequestException, MalformedURLException, ProtocolException{
		String randomReleaseGid = String.valueOf((int)(Math.random() * 1000));
		coverArtArchiveDaoWSImpl.releaseCover(randomReleaseGid);
	}
	
	@Test
	public void testReleaseGroupCover() throws JsonMappingException, NetConnectionException, NetContentNotFoundException, NetBadRequestException, MalformedURLException, ProtocolException{
		String randomReleaseCoverGid = String.valueOf((int)(Math.random() * 1000));
		coverArtArchiveDaoWSImpl.releaseGroupCover(randomReleaseCoverGid);
	}
}
