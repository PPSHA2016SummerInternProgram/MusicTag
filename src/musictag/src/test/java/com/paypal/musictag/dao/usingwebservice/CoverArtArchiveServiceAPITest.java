package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

public class CoverArtArchiveServiceAPITest extends TestCase{
	
	@Test
	public void testGetReleaseCover(){
		try {
			CoverArtArchiveServiceAPI.sendRequest("release/", "76df3287-6cda-33eb-8e9a-044b5e15ffdd");
		} catch (IOException e) {
			assertEquals(0, 1);
		}
	}
	
	
}
