package com.paypal.musictag.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ExceptionTest {

	@Test
	public void GlobalControllerExceptionHandlerTest0() throws Exception {
		GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();
		handler.defaultExceptionHandler(new Exception("Test"));
	}

	@Test(expected = Exception.class)
	public void GlobalControllerExceptionHandlerTest1() throws Exception {
		GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();
		handler.defaultExceptionHandler(new NetConnectionException("Test"));
	}

	@Test
	public void NetConnectionExceptionTest() {
		new NetConnectionException("Test");
		new NetConnectionException("Test", new Exception("Test"));
	}

	@Test
	public void NetContentNotFoundExceptionTest() {
		new NetContentNotFoundException("Test");
		new NetContentNotFoundException("Test", new Exception("Test"));
	}

	@Test
	public void ResourceNotFoundExceptionTest() {
		new ResourceNotFoundException("Test");
		new ResourceNotFoundException("Test", new Exception("Test"));
	}

}
