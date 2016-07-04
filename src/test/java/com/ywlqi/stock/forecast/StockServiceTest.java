package com.ywlqi.stock.forecast;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.ywlqi.stock.forecast.model.DataDay;
import com.ywlqi.stock.forecast.service.ImportOrgService;
import com.ywlqi.stock.forecast.service.Lhb2Service;
import com.ywlqi.stock.forecast.service.QQStockService;
import com.ywlqi.stock.forecast.service.StockService;
import com.ywlqi.stock.forecast.service.TagsService;
import com.ywlqi.stock.forecast.service.WeixinService;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.ThreadPool;

public class StockServiceTest extends BaseTestCase{
	
	@Test
	public void testFillOneDayData() throws ParseException{
		this.testInitStock();
//		this.testInitGetStockClass();
		
		Date date = DateUtil.parse( "2016-07-01","yyyy-MM-dd");
		
		StockService stockService = (StockService)context.getBean("stockService");
		
		try{
		stockService.getHistoryData( date);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		Lhb2Service lhb2Service = (Lhb2Service)context.getBean("lhb2Service");
		try{
			
			lhb2Service.getLhbDataAndDetailByDate(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		TagsService tagsService = (TagsService)context.getBean("tagsService");
		
		try{
			tagsService.getTagByDate(date);
			}catch(Exception e){
				e.printStackTrace();
			}
		
	QQStockService qqStockService = (QQStockService)context.getBean("qqStockService");
		
//		stockService.initStock();
		try {
			qqStockService.getRealTimeData();
//			stockService.initStock();
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//每月1日执行	
/*ImportOrgService importOrgService = (ImportOrgService)context.getBean("importOrgService");
		
		try {
			importOrgService.updateKeyOrg();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		WeixinService WeixinService = (WeixinService)context.getBean("weixinService");
		
		try {
			WeixinService.sendKeyOrgStock(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			WeixinService.sendKeyOrgStock2(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			WeixinService.sendSelectionsByDate(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInitGetStockClass(){
		StockService stockService = (StockService)context.getBean("stockService");
		stockService.initStockClass();
	}
	
	@Test
	public void testInitStock(){
		StockService stockService = (StockService)context.getBean("stockService");
		
		try {
			stockService.initStock();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTodayData() throws CannotGetJdbcConnectionException, SQLException, Exception{
		StockService stockService = (StockService)context.getBean("stockService");
		Date date = DateUtil.parse( "2015-10-30","yyyy-MM-dd");
		try{
		stockService.getHistoryData( date);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInitGetTodayData() throws CannotGetJdbcConnectionException, SQLException, Exception{
		StockService stockService = (StockService)context.getBean("stockService");
		Date startDate = DateUtil.parse( "2016-04-02","yyyy-MM-dd");
		Date endDate = DateUtil.parse( "2016-04-01","yyyy-MM-dd");
		long days = (startDate.getTime() - endDate.getTime())/1000/3600/24;
		while(true){
			
			
			if(startDate.after(endDate)){
				try{
					stockService.getHistoryData( endDate);
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println("endDate=="+endDate);
				endDate = DateUtil.addDay(endDate, 1);
			}else{
//				Thread.sleep(10000);
//				
//				System.out.println("all task:"+ThreadPool.getInstance().getTaskCount());
//				if(!ThreadPool.getInstance().isShutdown() && ThreadPool.getInstance().getTaskCount() == days){
//					Thread.sleep(10000);
//					ThreadPool.getInstance().shutdown();
//				}
				break;
			}
			
		}
		
	}
	
	@Test
	public void testGetData() throws Exception{
		StockService stockService = (StockService)context.getBean("stockService");
		try {
			Date date = DateUtil.parse( "2015-10-28","yyyy-MM-dd");
			
			DataDay data = stockService.getHistoryData("002647", date);
			
			if(data != null){
				try {
					stockService.saveDataDay(data);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInitYesterday() throws ParseException{
		StockService stockService = (StockService)context.getBean("stockService");
		
		Date date = DateUtil.parse( "2016-01-10","yyyy-MM-dd");
		
		stockService.initYesterDay(date);
	}
}
