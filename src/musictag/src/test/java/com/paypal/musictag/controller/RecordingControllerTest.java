package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("RecordingControllerTest")
public class RecordingControllerTest {
	@Autowired
	private RecordingController recordingController;

	@Test
	public void testBasic() {
		Map<?, ?> map = recordingController.basic(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = recordingController.basic(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testReleases() {
		Map<?, ?> map = recordingController.releases(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = recordingController.releases(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testWorkArtistRels() {
		Map<?, ?> map = recordingController.workArtistRels(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = recordingController.workArtistRels(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

	@Test
	public void testFull() {
		Map<?, ?> map = recordingController.full(null);
		assertEquals(map.get("success"), false);
		assertEquals(map.get("responseCode"), 402);
		map = recordingController.full(StaticValues.recordingGid0);
		assertEquals(map.get("success"), true);
		assertNotEquals(map.get("data"), null);
	}

}
