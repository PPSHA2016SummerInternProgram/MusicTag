package com.paypal.musictag.dao.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ImageDaoImplTest {

	@Autowired
	private ImageDaoImpl imageDaoImpl;

	@Test
	public void testArtistImage() {
		imageDaoImpl.artistImagesFromLastfm(StaticValues.artistGid0);
	}

}
