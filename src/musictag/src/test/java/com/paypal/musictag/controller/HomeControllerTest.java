package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("HomeControllerTest")
public class HomeControllerTest {
    @Autowired
	HomeController homeController;

    @Test
	public void testPortal() {
		HttpServletRequest request = new MockHttpServletRequest();
		String page = homeController.portal();
		assertEquals(page, "/WEB-INF/pages/home.jsp");
	}
}
