package com.paypal.musictag.dao.usingdb;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
@Service("RecordingMapperTest")

public class RecordingMapperTest {
	@Autowired
	RecordingMapper recordingMapper;

	@Test
	public void findGetWorkMBID() {
		UUID id = UUID.fromString(StaticValues.recordingGid0);
		System.out.println(recordingMapper.getWorkMBID(id));
	}
}
