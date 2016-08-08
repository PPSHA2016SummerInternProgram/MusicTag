package com.paypal.musictag.dao.usingdb;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.usingdb.resulthandler.ArtistCreditCountResultHandler;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("ArtistRelationMapperTest")
public class ArtistRelationMapperTest {

	@Autowired
	ArtistRelationMapper artistRelationMapper;

	@Test
	public void testFindArtistCredit() {
		UUID id = UUID.fromString(StaticValues.artistGid0);
		ArtistCreditCountResultHandler handler = new ArtistCreditCountResultHandler();
		artistRelationMapper.findArtistCredit(id, handler);
		System.out.println("==================");
		System.out.println(handler.getArtistCreditCountMap());
	}
}
