package com.paypal.musictag.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class StatisticsControllerTest {

	@Autowired
	private StatisticsController statisticsController;

	@Test
	public void testIndex() {
		assertEquals("/WEB-INF/pages/statistics.jsp", statisticsController.index());
	}

	@Test
	public void testArtistListeners() throws IOException {
		Map<?, ?> response = statisticsController.artistListeners(StaticValues.artistGid0);
		assertEquals(response.get("success"), true);
	}

	@Test
	public void testArtistPlaycount() throws IOException {
		Map<?, ?> response = statisticsController.artistPlaycount(StaticValues.artistGid0);
		assertEquals(response.get("success"), true);
	}

	@Test
	public void testReleaseListeners() throws IOException {
		Map<?, ?> response = statisticsController.releaseListeners(StaticValues.releaseGid0);
		assertEquals(response.get("success"), true);
	}

	@Test
	public void testReleasePlaycount() throws IOException {
		Map<?, ?> response = statisticsController.releasePlaycount(StaticValues.releaseGid0);
		assertEquals(response.get("success"), true);
	}

	@Test
	public void testRecordingListeners() throws IOException {
		Map<?, ?> response = statisticsController.recordingListeners(StaticValues.recordingGid0);
		assertEquals(response.get("success"), true);
	}

	@Test
	public void testRecordingPlaycount() throws IOException {
		Map<?, ?> response = statisticsController.recordingPlaycount(StaticValues.recordingGid0);
		assertEquals(response.get("success"), true);
	}
	@Test
	public void testArtistReleaseYearlyDist() throws IOException {
		Map<?, ?> response = statisticsController.artistReleaseYearlyDist(StaticValues.recordingGid0);
		assertEquals(response.get("success"), true);
	}
}
