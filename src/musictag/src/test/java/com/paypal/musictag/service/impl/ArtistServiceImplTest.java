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
	public void testTooltipInfo() throws IOException{
		String gid = StaticValues.randomUUID();
		artistServiceImpl.tooltipInfo(gid);
	}
}
