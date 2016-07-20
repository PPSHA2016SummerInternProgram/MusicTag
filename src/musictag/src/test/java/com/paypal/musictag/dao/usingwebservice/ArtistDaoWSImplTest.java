package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ArtistDaoWSImplTest {

    @Autowired
    ArtistDao artistDaoWSImpl;

    private Exception exception = new Exception("An exception is needed.");

    @Test
    public void testProfile() throws Exception {
        artistDaoWSImpl.profile(StaticValues.artistGid0);
        artistDaoWSImpl.profile(StaticValues.artistGid1);
        try {
            artistDaoWSImpl.profile(null);
            throw exception;
        } catch (IOException e) {

        }
    }

    @Test
    public void testRelLinks() throws Exception {
        artistDaoWSImpl.relLinks(StaticValues.artistGid0);
        artistDaoWSImpl.relLinks(StaticValues.artistGid1);
        try {
            artistDaoWSImpl.relLinks(null);
            throw exception;
        } catch (IOException e) {

        }
    }

    @Test
    public void testRelLinksImage() throws Exception {
        artistDaoWSImpl.image(StaticValues.artistGid0);
        artistDaoWSImpl.image(StaticValues.artistGid1);
        try {
            artistDaoWSImpl.image(null);
            throw exception;
        } catch (IOException e) {

        }
    }

    @Test
    public void testBasicInfo() throws Exception {

        artistDaoWSImpl.basicInfo(StaticValues.artistGid0);
        artistDaoWSImpl.basicInfo(StaticValues.artistGid1);

        try {
            artistDaoWSImpl.basicInfo("some one not exists");
            throw exception;
        } catch (IOException e) {
            // Just Empty
        }

        try {
            artistDaoWSImpl.basicInfo(null);
            throw exception;
        } catch (IOException e) {
            // Just Empty
        }
    }

    @Test
    public void testReleaseGroup() throws Exception {

        artistDaoWSImpl.releaseGroup(StaticValues.artistGid0);
        artistDaoWSImpl.releaseGroup(StaticValues.artistGid1);

        try {
            artistDaoWSImpl.releaseGroup("some one not exists");
            throw exception;
        } catch (IOException e) {
            // Just Empty
        }

        try {
            artistDaoWSImpl.releaseGroup(null);
            throw exception;
        } catch (IOException e) {
            // Just Empty
        }
    }
}
