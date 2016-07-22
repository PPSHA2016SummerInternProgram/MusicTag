package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.ReleaseDao;
import com.paypal.musictag.values.StaticValues;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ReleaseDaoWSImplTest extends TestCase {
	@Autowired
	ReleaseDao releaseDaoWSImpl;

    private Exception exception = new Exception("An exception is needed.");

    @Test
    public void testvote() throws Exception {
    	releaseDaoWSImpl.vote(StaticValues.releaseGid0);
    	releaseDaoWSImpl.vote(StaticValues.releaseGid1);
        try {
        	releaseDaoWSImpl.vote(null);
            throw exception;
        } catch (IOException e) {

        }
    }	
}
