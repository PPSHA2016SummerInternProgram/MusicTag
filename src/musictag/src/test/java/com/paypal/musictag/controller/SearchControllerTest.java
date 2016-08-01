package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("SearchControllerTest")
public class SearchControllerTest {

	@Autowired
	SearchController searchController;

	@Test
	public void testSearchAll() throws IOException {
		Map<?, ?> map = searchController.searchAll("Taylor Swift", 1);
		assertEquals(map.get("success"), true);
	}

	@Test
	public void testSearchArtist() throws IOException {
		Map<?, ?> map = searchController.searchArtist("Taylor Swift", 0, 1);
		assertEquals(map.get("success"), true);
	}

	@Test
	public void testSearchRelease() throws IOException {
		Map<?, ?> map = searchController.searchRelease("Taylor Swift", 0, 1);
		assertEquals(map.get("success"), true);
	}

	@Test
	public void testSearchRecording() throws IOException {
		Map<?, ?> map = searchController.searchRecording("Taylor Swift", 0, 1);
		assertEquals(map.get("success"), true);
	}

}
