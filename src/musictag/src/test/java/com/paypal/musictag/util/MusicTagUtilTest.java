package com.paypal.musictag.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class MusicTagUtilTest {

	@Test
	public void testContructor() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> c = Class.forName("com.paypal.musictag.util.MusicTagUtil");
		Constructor<?> c0 = c.getDeclaredConstructor();
		c0.setAccessible(true);
		c0.newInstance();
	}

	@Test(expected = MalformedURLException.class)
	public void testGetJsonFromURL0() throws NetConnectionException, NetContentNotFoundException,
			NetBadRequestException, ProtocolException, MalformedURLException {

		MusicTagUtil.getJsonFromURLWithoutProxy(new URL(""));
	}

	@Test(expected = NetBadRequestException.class)
	public void testGetJsonFromURL1() throws NetConnectionException, NetContentNotFoundException,
			NetBadRequestException, ProtocolException, MalformedURLException {

		MusicTagUtil.getJsonFromURLWithoutProxy(new URL("http://10.24.53.72:8080/no_this_page"));
	}

	@Test(expected = NetConnectionException.class)
	public void testGetJsonFromURL2() throws NetConnectionException, NetContentNotFoundException,
			NetBadRequestException, ProtocolException, MalformedURLException {

		// No this service
		MusicTagUtil.getJsonFromURLWithoutProxy(new URL("http://10.24.53.72:10010"));
	}

	@Test(expected = JsonMappingException.class)
	public void testJsonToMap() throws JsonMappingException {
		MusicTagUtil.jsontoMap("{Not a json}");
	}
}
