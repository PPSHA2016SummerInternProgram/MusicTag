package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.RecordingDao;
import com.paypal.musictag.values.StaticValues;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class RecordingDaoImplTest {

    @Autowired
    RecordingDao recordingDaoWSImpl;

    @Test
    public void testBasic() throws IOException {
        Map<String, Object> data = recordingDaoWSImpl.basic(StaticValues.recordingGid0);
        mapContainsKeys(data, new String [] {"title", "length", "rating"});
    }

    @Test(expected = NetContentNotFoundException.class)
    public void testBasicWithException() throws IOException {
        recordingDaoWSImpl.basic(null);
    }

    @Test
    public void testReleases() throws IOException {
        Map<String, Object> data = recordingDaoWSImpl.releases(StaticValues.recordingGid0);
        mapContainsKeys(data, new String []{"title", "length", "rating", "releases"});
    }

    @Test(expected = NetContentNotFoundException.class)
    public void testReleasesWithNullId() throws IOException {
        recordingDaoWSImpl.releases(null);
    }

    @Test(expected = NetContentNotFoundException.class)
    public void testReleasesWithEmptyId() throws IOException {
        recordingDaoWSImpl.releases("");
    }

    @Test
    public void testWorkArtistRels() throws IOException {
        Map<String, Object> data = recordingDaoWSImpl.workArtistRels(StaticValues.recordingGid0);
        mapContainsKeys(data, new String []{"title", "length", "rating", "relations"});
    }

    @Test(expected = NetContentNotFoundException.class)
    public void testWorkArtistRelsWithException() throws IOException {
        recordingDaoWSImpl.basic(null);
    }

    @Test
    public void testFull() throws IOException {
        Map<String, Object> data = recordingDaoWSImpl.full(StaticValues.recordingGid0);
        mapContainsKeys(data, new String []{"title", "length", "rating", "releases", "relations"});
    }

    @Test(expected = NetContentNotFoundException.class)
    public void testFullWithException() throws IOException {
        recordingDaoWSImpl.full(null);
    }

    private void mapContainsKeys( Map<String, Object> map, String[] keys) {
        for(String key : keys) assertTrue(map.containsKey(key));
    }
}
