package com.paypal.musictag.service.impl;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.paypal.musictag.service.SearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class SearchServiceImplTest {
	@Autowired
	SearchService searchServiceImpl;
	
	@Test
	public void testSearchAllParamsNotNull() throws IOException{
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		searchServiceImpl.searchArtist("taylor swift", 0, 24, params);
	}
}
