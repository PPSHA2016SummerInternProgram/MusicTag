package com.paypal.musictag.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.service.StatisticsService;
import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class StatisticServiceImplTest {
	
	@Autowired 
	StatisticsService statisticsServiceImpl;
	
	@Test
	public void testArtistCreditCount(){
		System.out.println(statisticsServiceImpl.artistCreditCount(StaticValues.artistGid0));
		
	}
}
