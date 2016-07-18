package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.ArtistDao;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath*:/spring-mvc.xml"})  
public class ArtistDaoWSImplTest extends TestCase {

    ArtistDao artistDao = new ArtistDaoWSImpl();

    @Test
    public void testBasicInfo() {

        try {
            artistDao.basicInfo("a223958d-5c56-4b2c-a30a-87e357bc121b"); // 周杰伦
            artistDao.basicInfo("20244d07-534f-4eff-b4d4-930878889970"); // Taylor Swift
        } catch (IOException e) {
            e.printStackTrace();
            assertEquals(0, 1);
        }

        try {
            artistDao.basicInfo("some one not exists");
            assertEquals(0, 1);
        } catch (IOException e) {
            // Just Empty
        }

        try {
            artistDao.basicInfo(null);
            assertEquals(0, 1);
        } catch (IOException e) {
            // Just Empty
        }
    }

    @Test
    public void testReleaseGroup() {

        try {
            artistDao.releaseGroup("a223958d-5c56-4b2c-a30a-87e357bc121b"); // 周杰伦
            artistDao.releaseGroup("20244d07-534f-4eff-b4d4-930878889970"); // Taylor Swift
        } catch (IOException e) {
            e.printStackTrace();
            assertEquals(0, 1);
        }

        try {
            artistDao.releaseGroup("some one not exists");
            assertEquals(0, 1);
        } catch (IOException e) {
            // Just Empty
        }

        try {
            artistDao.releaseGroup(null);
            assertEquals(0, 1);
        } catch (IOException e) {
            // Just Empty
        }
    }
}
