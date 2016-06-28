package com.ywlqi.stock.forecast;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.ywlqi.stock.forecast.service.LhbService;
import com.zhaopin.common.util.DateUtil;

public class LhbServiceTest extends BaseTestCase{
	
	@Test
	public void testGetLhbData() throws ParseException{
		LhbService lhbService = (LhbService)context.getBean("lhbService");
		
//		lhbService.getTodayLhbData();
		Date today = DateUtil.parse( "2015-12-29","yyyy-MM-dd");
		try {
			lhbService.getTodayLhbData(today);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetLhbDataDetail() throws Exception{
		LhbService lhbService = (LhbService)context.getBean("lhbService");
		Date today = DateUtil.parse( "2015-12-29","yyyy-MM-dd");
		lhbService.getLhbDataDetailByDate(today);
	}
	
}
