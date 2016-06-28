package com.ywlqi.stock.forecast.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import com.ywlqi.stock.forecast.model.DataDay;
import com.ywlqi.stock.forecast.model.Stock;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.Regex;

@Service(value="qqStockService")
public class QQStockService extends BaseService{
	
	/*
	 * 0: 未知  
 1: 名字  
 2: 代码  
 3: 当前价格  
 4: 昨收  
 5: 今开  
 6: 成交量（手）  
 7: 外盘  
 8: 内盘  
 9: 买一  
10: 买一量（手）  
11-18: 买二 买五  
19: 卖一  
20: 卖一量  
21-28: 卖二 卖五  
29: 最近逐笔成交  
30: 时间  
31: 涨跌  
32: 涨跌%  
33: 最高  
34: 最低  
35: 价格/成交量（手）/成交额  
36: 成交量（手）  
37: 成交额（万）  
38: 换手率  
39: 市盈率  
40:   
41: 最高  
42: 最低  
43: 振幅  
44: 流通市值  
45: 总市值  
46: 市净率  
47: 涨停价  
48: 跌停价  
	 */
	
	private static final Logger logger = Logger.getLogger(QQStockService.class);
	
	public void initStock(){
		try {
			List<Stock> stockList = this.forecastDao.findListBySQL("select * from stock", Stock.class);
			
			for(Stock stock:stockList){
				try{
					this.initSingleStock(stock);
				}catch(Exception e){
					logger.error("stock.code =="+stock.getStockCode(),e);
//					e.printStackTrace();
				}
			}
			
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getRealTimeStockDataFromNet(String fullStockCode) throws Exception{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "http://qt.gtimg.cn/q="+fullStockCode;
		String body = httpGet.getBodyToString(url, "GBK", null, "GET");
		String data = Regex.matchSRowSField(body, "\"(.*?)\"", false);
		
		return data;
	}
	
	
	private void initSingleStock(Stock stock) throws Exception{
		
		String data = this.getRealTimeStockDataFromNet(stock.getFullStockCode());
		
		
		String[] dataArr = data.split("~");
		
		stock.setCirculationValue(StringUtils.isBlank(dataArr[44])?null:Float.valueOf(dataArr[44]));
		stock.setTotalValue(StringUtils.isBlank(dataArr[45])?null:Float.valueOf(dataArr[45]));
		stock.setPbRatios(StringUtils.isBlank(dataArr[46])?null:Float.valueOf(dataArr[46]));
		stock.setPeRatios(StringUtils.isBlank(dataArr[39])?null:Float.valueOf(dataArr[39]));
		stock.setStockName(dataArr[1]);
		
		this.saveStock(stock);
	}
	
	private void saveStock(Stock stock){
		String sql = "replace into stock(stock_code,stock_name,total_value,circulation_value,pe_ratios,pb_ratios) values(?,?,?,?,?,?)";
		
		try {
			this.forecastDao.executeSQL(sql, 
					stock.getStockCode(),
					stock.getStockName(),
					stock.getTotalValue(),
					stock.getCirculationValue(),
					stock.getPeRatios(),
					stock.getPbRatios());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getRealTimeData() throws CannotGetJdbcConnectionException, SQLException, Exception{
		List<Stock> stockList = this.forecastDao.findListBySQL("select * from stock", Stock.class);
		
		for(Stock stock:stockList){
			try{
				DataDay dataDay = getRealTimeData(stock);
				this.saveDataDay(dataDay);
			}catch(Exception e){
				logger.error("",e);
			}
			
		}
	}
	
	private DataDay getRealTimeData(Stock stock) throws Exception{
		String url = "http://qt.gtimg.cn/q="+stock.getFullStockCode();
		logger.info("url==="+url);
		
		String body = httpGet.getBodyToString(url, "GBK", null, "GET");
		
		DataDay dataDay = DataDay.toBeanByQQAPI(body,stock.getStockCode());
		
		return dataDay;

//		return null;
	}
	
	
	public void saveDataDay(DataDay dataDay) throws SQLException, Exception{
		String sql = "replace into data_day(stock_code,stock_date,stock_start,stock_end,stock_min,stock_max,stock_deal_num,stock_deal_amount,stock_yesterday_end,total_value,circulation_value,pe_ratios,pb_ratios,turnover_rate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
//		StringBuffer yesterday = new StringBuffer();
//		
//		yesterday.append("select stock_end                                 ");
//		yesterday.append("  from data_day dd                               ");
//		yesterday.append(" where dd.stock_code = ?                         ");
//		yesterday.append("   and dd.stock_date = (select max(stock_date)   ");
//		yesterday.append("                          from data_day d        ");
//		yesterday.append("                         where d.stock_code = ?  ");
//		yesterday.append("                           and d.stock_date < ?) ");
		
//		Float yesterdayEnd = null;
//		
//		List<Map<String,Object>> yesterdayList = this.forecastDao.findListBySQL(yesterday.toString(), dataDay.getStockCode(),dataDay.getStockCode(),dataDay.getStockDate());
//		
//		if(CollectionUtils.isNotEmpty(yesterdayList)){
//			try{
//				yesterdayEnd = ((Number)yesterdayList.get(0).get("stock_end")).floatValue();
//			}catch(Exception e){
//				logger.error("get yesterday end error!!!",e);
//			}
//		}
		
		
		this.forecastDao.executeSQL(sql, 
				dataDay.getStockCode(),
				dataDay.getStockDate(),
				dataDay.getStockStart(),
				dataDay.getStockEnd(),
				dataDay.getStockMin(),
				dataDay.getStockMax(),
				dataDay.getStockDealNum(),
				dataDay.getStockDealAmount(),
				dataDay.getStockYesterdayEnd(),
				dataDay.getTotalValue(),
				dataDay.getCirculationValue(),
				dataDay.getPeRatios(),
				dataDay.getPbRatios(),
				dataDay.getTurnoverRate());
	}
	
	
	
	
	public static void main(String[] args) {	
		System.out.println(Integer.valueOf("09"));
	}
}
