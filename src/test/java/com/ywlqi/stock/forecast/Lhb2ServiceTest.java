package com.ywlqi.stock.forecast;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.ywlqi.stock.forecast.service.Lhb2Service;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.ThreadPool;

public class Lhb2ServiceTest extends BaseTestCase{
	
	@Test
	public void testGetLhbDataByDate() {
		Lhb2Service lhb2Service = (Lhb2Service)context.getBean("lhb2Service");
		try{
			Date date = DateUtil.parse("2016-01-05","yyyy-MM-dd");
			
			lhb2Service.getLhbDataAndDetailByDate(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testInitLhbDataByDate(){
		try{
			Lhb2Service lhb2Service = (Lhb2Service)context.getBean("lhb2Service");
		
			Date startDate = DateUtil.parse("2016-04-01","yyyy-MM-dd");
			Date endDate = DateUtil.parse("2015-01-01","yyyy-MM-dd");
			
			long days = (startDate.getTime() - endDate.getTime())/1000/3600/24;
			System.out.println(days);
			while(startDate.after(endDate)){
				
					try{
						lhb2Service.getLhbDataAndDetailByDate(startDate);
					}catch(Exception e){
						e.printStackTrace();
					}
					startDate = DateUtil.addDay(startDate, -1);
			}
			
			System.out.println("00000000000000000000000000000000000000000000000000000000000000000");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInitLhbDataDetailByDate() throws ParseException{
		Lhb2Service lhb2Service = (Lhb2Service)context.getBean("lhb2Service");
		Date date = DateUtil.parse("2015-10-09","yyyy-MM-dd");
		lhb2Service.getLhbDataByDate(date);
	}

}
