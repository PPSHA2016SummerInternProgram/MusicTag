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
	
	@Test
	public void testTryGetArea(){

		System.out.println(statisticsServiceImpl.artistAreaCount(StaticValues.artistGid0));
		
	}
	
	
	@Test
	public void testGetEdit(){
		System.out.println("--------------------------------------------------------");
		System.out.println("******************"+statisticsServiceImpl.artistEdit(StaticValues.artistGid0)+"*******************");
		System.out.println("--------------------------------------------------------");
	}
	
	@Test
	public void testArtistAreaDetails(){
		System.out.println(statisticsServiceImpl.artistAreaDetails(StaticValues.artistGid0, "China"));
	}
	
	@Test
	public void testDistributionScores(){
		System.out.println(statisticsServiceImpl.distributionScores(StaticValues.artistGid0));
	}
	
	@Test
	public void testReleaseInfo(){
		System.out.println(statisticsServiceImpl.releaseInfo(StaticValues.artistGid0));
	}
	
}
