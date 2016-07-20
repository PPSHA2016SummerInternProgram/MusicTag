package com.paypal.musictag.dao.usingdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("ReleaseGroupMapperTest")
public class ReleaseGroupMapperTest extends TestCase {

    @Autowired
    private ReleaseGroupMapper releaseGroupMapper;

    static private int amount = 0;

    @Test
    public void testFindReleasesByReleaseGroup0() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("artistGid", UUID.fromString(StaticValues.artistGid0));
        param.put("offset", 0);
        param.put("amount", 1);
        List<Map<String, Object>> result = releaseGroupMapper
                .findReleasesByReleaseGroup(param);
        amount = (int) (long) result.get(0).get("total_row_count"); // find the amount of all release-groups
    }

    @Test
    public void testFindReleasesByReleaseGroup1() {

        // find all release-groups (asc & desc)
        // and compare their IDs
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("artistGid", UUID.fromString(StaticValues.artistGid0));
        param.put("offset", 0);
        param.put("amount", amount);
        param.put("order_by", "date");
        param.put("direction", "asc");
        List<Map<String, Object>> allDateAsc = releaseGroupMapper
                .findReleasesByReleaseGroup(param);
        param.put("direction", "desc");
        List<Map<String, Object>> allDateDesc = releaseGroupMapper
                .findReleasesByReleaseGroup(param);
        assertEquals(allDateAsc.size(), amount);
        assertEquals(allDateDesc.size(), amount);
        for (int i = 0; i < amount; i++) {
            Map<String, Object> m1 = allDateAsc.get(i);
            Map<String, Object> m2 = allDateDesc.get(amount - i - 1);
            assertEquals(String.valueOf(m1.get("id")),
                    String.valueOf(m2.get("id")));
        }

    }

    @Test
    public void testFindReleasesByReleaseGroup2() {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("artistGid", UUID.fromString(StaticValues.artistGid0));
        param.put("offset", 0);
        param.put("amount", amount);
        param.put("order_by", "date");
        param.put("direction", "asc");
        List<Map<String, Object>> allDateAsc = releaseGroupMapper
                .findReleasesByReleaseGroup(param);

        int offset = 10;
        param.put("offset", offset);
        param.put("amount", amount - offset);
        List<Map<String, Object>> someDateAsc = releaseGroupMapper
                .findReleasesByReleaseGroup(param);
        assertEquals(someDateAsc.size(), amount - offset);
        for (int i = 0; i < amount - offset; i++) {
            Map<String, Object> m1 = someDateAsc.get(i);
            Map<String, Object> m2 = allDateAsc.get(i + offset);
            assertEquals(String.valueOf(m1.get("id")),
                    String.valueOf(m2.get("id")));

        }
    }
}
