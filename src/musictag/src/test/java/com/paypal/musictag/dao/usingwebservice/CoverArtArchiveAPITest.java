package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import com.paypal.musictag.dao.usingwebservice.api.CoverArtArchiveAPI;

public class CoverArtArchiveAPITest extends TestCase{
	
	@Test
	public void testGetReleaseCover(){
		try {
			CoverArtArchiveAPI.sendRequest("release/", "76df3287-6cda-33eb-8e9a-044b5e15ffdd");
		} catch (IOException e) {
			assertEquals(0, 1);
		}
	}
	
	
}
