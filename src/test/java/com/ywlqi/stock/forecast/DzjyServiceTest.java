package com.ywlqi.stock.forecast;


import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.ywlqi.stock.forecast.service.DzjyService;
import com.zhaopin.common.util.DateUtil;

public class DzjyServiceTest extends BaseTestCase{

	@Test
	public void testInitDzjy() throws ParseException{
		DzjyService dzjyService = (DzjyService)context.getBean("dzjyService");
		Date date = DateUtil.parse("2015-01-01","yyyy-MM-dd");
		dzjyService.getDzjyByDate(date, 1);
	}
}
