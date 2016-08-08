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
@Service("SuggestControllerTest")
public class SuggestControllerTest {
	@Autowired
	SuggestController suggestController;
	
	@Test
	public void testSuggestAll() throws IOException{
		Map<?, ?> map = suggestController.suggestAll("ta", 10);
		System.out.println("=================");
		System.out.println(map);
		assertEquals(map.get("success"), true);
		Map<?, ?> map1 = suggestController.suggestAll("tay", 10);
		System.out.println("=================");
		System.out.println(map1);
		assertEquals(map1.get("success"), true);
		Map<?, ?> map2 = suggestController.suggestAll("taylor", 10);
		System.out.println("=================");
		System.out.println(map2);
		assertEquals(map2.get("success"), true);
	}
}
