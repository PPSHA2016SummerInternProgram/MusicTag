package com.paypal.musictag.dao.usingwebservice;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.dao.ReleaseDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class ReleaseDaoWSImplTest {
	@Autowired
	ReleaseDao releaseDaoWSImpl;

    private Exception exception = new Exception("An exception is needed.");

/*
 * !!! I don't understand how to write the test, so I'll rewrite it latter.
 * 
 * @Test
    public void testvote() throws Exception {
    	releaseDaoWSImpl.vote(StaticValues.releaseGid0);
    	releaseDaoWSImpl.vote(StaticValues.releaseGid1);
        try {
        	releaseDaoWSImpl.vote(null);
            throw exception;
        } catch (IOException e) {

        }
    }
 */

    
    
}

