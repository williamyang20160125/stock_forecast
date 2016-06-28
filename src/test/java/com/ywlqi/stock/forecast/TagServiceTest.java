package com.ywlqi.stock.forecast;

import java.util.Date;

import org.junit.Test;

import com.ywlqi.stock.forecast.service.TagsService;
import com.zhaopin.common.util.DateUtil;

public class TagServiceTest  extends BaseTestCase{
	
	@Test
	public void testInitTag() throws Exception{
		TagsService tagsService = (TagsService)context.getBean("tagsService");


		Date startDate = DateUtil.parse( "2016-04-11","yyyy-MM-dd");
		Date endDate = DateUtil.parse( "2016-04-11","yyyy-MM-dd");
		long days = (startDate.getTime() - endDate.getTime())/1000/3600/24;
		
		while(true){
			try{
			tagsService.getTagByDate(endDate);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
				
			}
			endDate = DateUtil.addDay(endDate, 1);
			
			if(startDate.before(endDate)){
				break;
			}
		}
		
		try {
			Thread.sleep(3600000*24);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("ttttttttttttttttttttt");
	}

}
