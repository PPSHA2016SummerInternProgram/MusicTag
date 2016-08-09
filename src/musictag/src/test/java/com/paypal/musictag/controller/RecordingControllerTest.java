package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("RecordingControllerTest")
public class RecordingControllerTest {
	@Autowired
	private RecordingController recordingController;

	@Test
	public void testBasic() throws IOException {
		Map<?, ?> map = recordingController.basic(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testBasicException() throws IOException{
		recordingController.basic(null);
	}

	@Test
	public void testReleases() throws IOException {
		Map<?, ?> map = recordingController.releases(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected = NetBadRequestException.class)
	public void testReleasesException() throws IOException{
		recordingController.releases(null);
	}

	@Test
	public void testWorkArtistRels() throws IOException {
		Map<?, ?> map = recordingController.workArtistRels(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	
	@Test(expected=NetBadRequestException.class)
	public void testWorkArtistRelsException() throws IOException{
		recordingController.workArtistRels(null);
	}

	@Test
	public void testFull() throws IOException {
		Map<?, ?> map = recordingController.full(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}
	@Test(expected=NetBadRequestException.class)
	public void testFullException() throws IOException{
		recordingController.full(null);
	}
	
	@Test
	public void testRecordingOverview(){
		HttpServletRequest request = new MockHttpServletRequest();
		
		String page = recordingController.recording(null, StaticValues.artistGid0, request);
		
		assertEquals(page,"/WEB-INF/pages/song.jsp");
	}

}
