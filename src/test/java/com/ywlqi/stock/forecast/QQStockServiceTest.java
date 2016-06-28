package com.ywlqi.stock.forecast;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.ywlqi.stock.forecast.service.QQStockService;

public class QQStockServiceTest extends BaseTestCase{
	
	@Test
	public void testInitStock(){
		QQStockService stockService = (QQStockService)context.getBean("qqStockService");
		
//		stockService.initStock();
		try {
			stockService.getRealTimeData();
//			stockService.initStock();
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
