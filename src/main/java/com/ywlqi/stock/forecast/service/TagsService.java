package com.ywlqi.stock.forecast.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import com.ywlqi.stock.forecast.model.Stock;
import com.zhaopin.common.util.DateUtil;

/*
 * 连续三天成交量递增
 * 连续三天成交额递增
 * 5、10、20、30、60、120、200日均线
 * 连续三天上涨
 * 连续三天收阳
 * 连续三天跌停
 * 连续两天涨停
 * 当天量增幅
 * 当天价增幅
 * 当天涨幅
 * 当天振幅
 * 总市值
 * 流通市值
 * 行业
 * 所属板块
 * 重点机构买入
 * 重点机构卖出
 */
@Service
public class TagsService extends BaseService{
	
	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,6,300, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(30),new ThreadPoolExecutor.CallerRunsPolicy());
	
	private static final Logger logger = Logger.getLogger(TagsService.class);
	
	
	public void toDayTag() throws Exception{
		Date date = DateUtil.parse(DateUtil.format(new Date(),"yyyyMMdd"),"yyyyMMdd");

		this.getTagByDate(date);
	}
	
	public void getTagByDate(final Date date) throws Exception{
//		threeDayIncrementDealNum(date);
//		threeDayIncrementDealAmount(date);
//		avgInfo(date);
//		threeDayIncrementStockEnd(date);
//		threeDayRed(date);
//		twoDayLimitUp(date);
//		threeDayLimitDown(date);
//		dealNumPercent(date);
//		dealAmountPercent(date);
//		jumpPercent(date);
//		amplitudePercent(date);
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					threeDayIncrementDealNum(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					threeDayIncrementDealAmount(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					avgInfo(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					threeDayIncrementStockEnd(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					threeDayRed(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					twoDayLimitUp(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					threeDayLimitDown(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					dealNumPercent(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					dealAmountPercent(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					jumpPercent(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					amplitudePercent(date);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		});
	}
	
	private Boolean isExsit(String stockCode,Date date) throws  Exception{
		String isExsitSql = "select 1 from stock_tags where stock_code = ? and stock_date = ?";
		
		List list = this.forecastDao.findListBySQL(isExsitSql, stockCode,date);
		
		if(CollectionUtils.isEmpty(list)){
			return false;
		}else{
			return true;
		}
	}
	
	/*
	 * 三日成交量递增
	 */
	public void threeDayIncrementDealNum(final Date date) throws SQLException, Exception{
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from  ");
		sql.append(" (select d.stock_code,                                                                                                                    ");
		sql.append(" d.STOCK_DEAL_NUM day1,                                                                                                                     ");
		sql.append(" (select STOCK_DEAL_NUM from data_day dd where dd.stock_code=d.stock_code and dd.stock_date<d.stock_date order by stock_date desc limit 0,1) day2, ");
		sql.append(" (select STOCK_DEAL_NUM from data_day dd where dd.stock_code=d.stock_code and dd.stock_date<d.stock_date order by stock_date desc limit 1,1) day3, ");
		sql.append(" (select STOCK_DEAL_NUM from data_day dd where dd.stock_code=d.stock_code and dd.stock_date<d.stock_date order by stock_date desc limit 2,1) day4  ");
		sql.append("  from data_day d                                                                                                                         ");
		sql.append(" where d.stock_date=?) t1                                                                                                      ");
		sql.append(" where t1.day1>t1.day2                                                                                                                    ");
		sql.append("  and t1.day2>t1.day3                                                                                                                     ");
		sql.append("  and t1.day3>t1.day4                                                                                                                     ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
	
		
		if(CollectionUtils.isNotEmpty(list)){
	
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set three_day_up_deal_num=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,three_day_up_deal_num) values(?,?,?)", row.get("stock_code"),date,1);
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set three_day_up_deal_num=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
					}
				}
			}
			
		}
		
	}
	
	/*
	 * 三日成交额递增
	 */
	public void threeDayIncrementDealAmount(final Date date) throws SQLException, Exception{
		
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from  ");
		sql.append(" (select d.stock_code,                                                                                                                    ");
		sql.append(" STOCK_DEAL_AMOUNT day1,                                                                                                                     ");
		sql.append(" (select STOCK_DEAL_AMOUNT from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2, ");
		sql.append(" (select STOCK_DEAL_AMOUNT from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 1,1) day3, ");
		sql.append(" (select STOCK_DEAL_AMOUNT from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 2,1) day4  ");
		sql.append("  from data_day d                                                                                                                         ");
		sql.append(" where d.stock_date=?) t1                                                                                                      ");
		sql.append(" where t1.day1>t1.day2                                                                                                                    ");
		sql.append("  and t1.day2>t1.day3                                                                                                                     ");
		sql.append("  and t1.day3>t1.day4                                                                                                                     ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set three_day_up_deal_amount=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,three_day_up_deal_amount) values(?,?,?)", row.get("stock_code"),date,1);
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set three_day_up_deal_amount=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
					}
				}
			}
			
		}
		
	}
	
	public void avgInfo(final Date date) throws CannotGetJdbcConnectionException, SQLException, Exception{
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.*,                                                                                                                                                                           ");
		sql.append(" ((t.STOCK_END - t.STOCK_YESTERDAY_END) / t.STOCK_YESTERDAY_END * 100) today_ratios,                                                                                                   ");
		sql.append(" ((t.STOCK_END - t.avg5 )/ t.avg5 * 100) avg5_ratios,                                                                                                                                  ");
		sql.append(" ((t.STOCK_END - t.avg10 )/ t.avg10 * 100) avg10_ratios,                                                                                                                               ");
		sql.append(" ((t.STOCK_END - t.avg20 )/ t.avg20 * 100) avg20_ratios,                                                                                                                               ");
		sql.append(" ((t.STOCK_END - t.avg30 )/ t.avg30 * 100) avg30_ratios,                                                                                                                               ");
		sql.append(" ((t.STOCK_END - t.avg60 )/ t.avg60 * 100) avg60_ratios,                                                                                                                               ");
		sql.append(" ((t.STOCK_END - t.avg120 )/ t.avg120 * 100) avg120_ratios,                                                                                                                            ");
		sql.append(" ((t.STOCK_END - t.avg200 )/ t.avg200 * 100 ) avg200_ratios                                                                                                                            ");
        sql.append("                                                                                                                                                                                       ");
		sql.append(" from (select   d.stock_code,                                                                                                                                                                       ");
		sql.append("   d.STOCK_YESTERDAY_END,                                                                                                                                                              ");
		sql.append("   d.STOCK_END,                                                                                                                                                                        ");
		sql.append("   d.TOTAL_VALUE,                                                                                                                                                                        ");
		sql.append("   d.CIRCULATION_VALUE,                                                                                                                                                                        ");
		sql.append("   d.PE_RATIOS,                                                                                                                                                                        ");
		sql.append("   d.PB_RATIOS,                                                                                                                                                                        ");
		sql.append("  (select case count(*) when 5 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,5) t) avg5,      ");
		sql.append("  (select case count(*) when 10 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,10) t) avg10,   ");
		sql.append("  (select case count(*) when 20 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=?order by stock_date desc limit 0,20) t) avg20,   ");
		sql.append("  (select case count(*) when 30 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,30) t) avg30,   ");
		sql.append("  (select case count(*) when 60 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,60) t) avg60,   ");
		sql.append("  (select case count(*) when 120 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,120) t) avg120,");
		sql.append("  (select case count(*) when 200 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,200) t) avg200 ");
		sql.append(" from data_day d                                                                                                                                                                       ");
		sql.append(" where d.stock_date=?                                                                                                                                                       ");
		sql.append("  and d.stock_code = ?) t                                                                                                                                                      ");
		
		
		List<Stock> stockList = this.forecastDao.findListBySQL("select * from stock", Stock.class);
		
		for(Stock stock : stockList){
			final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(),stock.getStockCode(),stock.getStockCode(),stock.getStockCode(),stock.getStockCode(),stock.getStockCode(),stock.getStockCode(),stock.getStockCode(), date,stock.getStockCode());
			
			if(CollectionUtils.isNotEmpty(list)){
				
				for(Map<String,Object> row :list){
					if(this.isExsit((String) row.get("stock_code"), date)){
						this.forecastDao.executeSQL("update stock_tags set avg5_ratios=?,avg10_ratios=?,avg20_ratios=?,avg30_ratios=?,avg60_ratios=?,avg120_ratios=?,avg200_ratios=?,avg5=?,avg10=?,avg20=?,avg30=?,avg60=?,avg120=?,avg200=? where stock_code=? and stock_date=?", 
								 row.get("avg5_ratios")==null?null: ((Number)row.get("avg5_ratios")).floatValue(),  
								 row.get("avg10_ratios")==null?null: ((Number)row.get("avg10_ratios")).floatValue(),
								 row.get("avg20_ratios")==null?null: ((Number)row.get("avg20_ratios")).floatValue(),
								 row.get("avg30_ratios")==null?null: ((Number)row.get("avg30_ratios")).floatValue(),
								 row.get("avg60_ratios")==null?null: ((Number)row.get("avg60_ratios")).floatValue(),
								 row.get("avg120_ratios")==null?null: ((Number)row.get("avg120_ratios")).floatValue(),
								 row.get("avg200_ratios")==null?null: ((Number)row.get("avg200_ratios")).floatValue(),
								
								 row.get("avg5")==null?null: ((Number)row.get("avg5")).floatValue(),  
								 row.get("avg10")==null?null: ((Number)row.get("avg10")).floatValue(),
								 row.get("avg20")==null?null: ((Number)row.get("avg20")).floatValue(),
								 row.get("avg30")==null?null: ((Number)row.get("avg30")).floatValue(),
								 row.get("avg60")==null?null: ((Number)row.get("avg60")).floatValue(),
								 row.get("avg120")==null?null: ((Number)row.get("avg120")).floatValue(),
								 row.get("avg200")==null?null: ((Number)row.get("avg200")).floatValue(),
								 row.get("stock_code"),date);
					}else{
						try{
							this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,avg5_ratios,avg10_ratios,avg20_ratios,avg30_ratios,avg60_ratios,avg120_ratios,avg200_ratios,avg5,avg10,avg20,avg30,avg60,avg120,avg200) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", row.get("stock_code"),date,
									 row.get("avg5_ratios")==null?null: ((Number)row.get("avg5_ratios")).floatValue(),  
									 row.get("avg10_ratios")==null?null: ((Number)row.get("avg10_ratios")).floatValue(),
									 row.get("avg20_ratios")==null?null: ((Number)row.get("avg20_ratios")).floatValue(),
									 row.get("avg30_ratios")==null?null: ((Number)row.get("avg30_ratios")).floatValue(),
									 row.get("avg60_ratios")==null?null: ((Number)row.get("avg60_ratios")).floatValue(),
									 row.get("avg120_ratios")==null?null: ((Number)row.get("avg120_ratios")).floatValue(),
									 row.get("avg200_ratios")==null?null: ((Number)row.get("avg200_ratios")).floatValue(),
									 row.get("avg5")==null?null: ((Number)row.get("avg5")).floatValue(),  
									 row.get("avg10")==null?null: ((Number)row.get("avg10")).floatValue(),
									 row.get("avg20")==null?null: ((Number)row.get("avg20")).floatValue(),
									 row.get("avg30")==null?null: ((Number)row.get("avg30")).floatValue(),
									 row.get("avg60")==null?null: ((Number)row.get("avg60")).floatValue(),
									 row.get("avg120")==null?null: ((Number)row.get("avg120")).floatValue(),
									 row.get("avg200")==null?null: ((Number)row.get("avg200")).floatValue());
						}catch(Exception e){
							this.forecastDao.executeSQL("update stock_tags set avg5_ratios=?,avg10_ratios=?,avg20_ratios=?,avg30_ratios=?,avg60_ratios=?,avg120_ratios=?,avg200_ratios=?,avg5=?,avg10=?,avg20=?,avg30=?,avg60=?,avg120=?,avg200=? where stock_code=? and stock_date=?", 
									 row.get("avg5_ratios")==null?null: ((Number)row.get("avg5_ratios")).floatValue(),  
									 row.get("avg10_ratios")==null?null: ((Number)row.get("avg10_ratios")).floatValue(),
									 row.get("avg20_ratios")==null?null: ((Number)row.get("avg20_ratios")).floatValue(),
									 row.get("avg30_ratios")==null?null: ((Number)row.get("avg30_ratios")).floatValue(),
									 row.get("avg60_ratios")==null?null: ((Number)row.get("avg60_ratios")).floatValue(),
									 row.get("avg120_ratios")==null?null: ((Number)row.get("avg120_ratios")).floatValue(),
									 row.get("avg200_ratios")==null?null: ((Number)row.get("avg200_ratios")).floatValue(),
									
									 row.get("avg5")==null?null: ((Number)row.get("avg5")).floatValue(),  
									 row.get("avg10")==null?null: ((Number)row.get("avg10")).floatValue(),
									 row.get("avg20")==null?null: ((Number)row.get("avg20")).floatValue(),
									 row.get("avg30")==null?null: ((Number)row.get("avg30")).floatValue(),
									 row.get("avg60")==null?null: ((Number)row.get("avg60")).floatValue(),
									 row.get("avg120")==null?null: ((Number)row.get("avg120")).floatValue(),
									 row.get("avg200")==null?null: ((Number)row.get("avg200")).floatValue(),
									 row.get("stock_code"),date);
						}
					}
				}
			}
		}
		
	}
	
	/*
	 * 连续三天上涨
	 */
	public void threeDayIncrementStockEnd(final Date date) throws SQLException, Exception{
		
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from  ");
		sql.append(" (select d.stock_code,                                                                                                                    ");
		sql.append(" stock_end day1,                                                                                                                     ");
		sql.append(" (select stock_end from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2, ");
		sql.append(" (select stock_end from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 1,1) day3, ");
		sql.append(" (select stock_end from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 2,1) day4  ");
		sql.append("  from data_day d                                                                                                                         ");
		sql.append(" where d.stock_date=?) t1                                                                                                      ");
		sql.append(" where t1.day1>t1.day2                                                                                                                    ");
		sql.append("  and t1.day2>t1.day3                                                                                                                     ");
		sql.append("  and t1.day3>t1.day4                                                                                                                     ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set three_day_up_stock_end=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,three_day_up_stock_end) values(?,?,?)", row.get("stock_code"),date,1);
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set three_day_up_stock_end=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
					}
				}
			}
			
		}
		
	}
	
	/*
	 * 连续三天收阳
	 */
	public void threeDayRed(final Date date) throws SQLException, Exception{
		
		
		StringBuffer sql = new StringBuffer();

		sql.append(" select * from                                                                                                                               ");
		sql.append(" (select d.stock_code,                                                                                                                       ");
		sql.append(" stock_start day1_start,                                                                                                                     ");
		sql.append(" stock_end day1_end,                                                                                                                         ");
		sql.append(" (select stock_start from data_day dd where dd.stock_code=d.stock_code and dd.stock_date<d.stock_date order by stock_date desc limit 0,1) day2_start, ");
		sql.append(" (select stock_end from data_day dd where dd.stock_code=d.stock_code and dd.stock_date<d.stock_date order by stock_date desc limit 0,1) day2_end,     ");
		sql.append(" (select stock_start from data_day dd where dd.stock_code=d.stock_code and dd.stock_date<d.stock_date order by stock_date desc limit 1,1) day3_start, ");
		sql.append(" (select stock_end from data_day dd where dd.stock_code=d.stock_code and dd.stock_date<d.stock_date order by stock_date desc limit 1,1) day3_end      ");
		sql.append("  from data_day d                                                                                                                            ");
		sql.append(" where d.stock_date=?) t1                                                                                                         ");
		sql.append(" where t1.day1_end>t1.day1_start                                                                                                             ");
		sql.append("  and t1.day2_end>t1.day2_start                                                                                                              ");
		sql.append("  and t1.day3_end>t1.day3_start                                                                                                              ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set three_day_red=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,three_day_red) values(?,?,?)", row.get("stock_code"),date,1);
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set three_day_red=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
					}
				}
			}
			
		}
	}
	
	/*
	 * 连续两天涨停
	 */
	public void twoDayLimitUp(final Date date) throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select * from                                                                                                                                          ");
		sql.append(" (select stock_code,STOCK_END,STOCK_YESTERDAY_END,                                                                                                      ");
		sql.append(" (select STOCK_YESTERDAY_END from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2_yesterday,");
		sql.append("  (select STOCK_END from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2_end                ");
		sql.append("  from data_day d                                                                                                                                       ");
		sql.append(" where d.stock_date=?                                                                                                                        ");
		sql.append("  and d.STOCK_END>=truncate(d.STOCK_YESTERDAY_END * 1.1,2)                                                                                              ");
		sql.append("  and d.STOCK_YESTERDAY_END != 0) t where day2_end>=truncate(day2_yesterday*1.1,2) and day2_yesterday!=0                                                ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set two_day_limit_up=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,two_day_limit_up) values(?,?,?)", row.get("stock_code"),date,1);
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set two_day_limit_up=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
					}
				}
			}
		}
	}
	
	
	/*
	 * 连续三天跌停
	 */
	public void threeDayLimitDown(final Date date) throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select * from                                                                                                                                            ");
		sql.append(" (select stock_code,stock_date,STOCK_END,STOCK_YESTERDAY_END,                                                                                             ");
		sql.append(" (select STOCK_YESTERDAY_END from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2_yesterday,  ");
		sql.append("  (select STOCK_END from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2_end,                 ");
		sql.append(" (select STOCK_YESTERDAY_END from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 1,1) day3_yesterday,  ");
		sql.append("  (select STOCK_END from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 1,1) day3_end                  ");
		sql.append("  from data_day d                                                                                                                                         ");
		sql.append(" where d.stock_date=?                                                                                                                         ");
		sql.append("  and d.STOCK_END<=ROUND(d.STOCK_YESTERDAY_END * 0.9,2)                                                                                                   ");
		sql.append("  and d.STOCK_END != 0) t where day2_end<=ROUND(day2_yesterday*0.9,2) and day2_end!=0 and day3_end<=ROUND(day3_yesterday*0.9,2) and day3_end!=0         ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set three_day_limit_down=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,three_day_limit_down) values(?,?,?)", row.get("stock_code"),date,1);
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set three_day_limit_down=1 where stock_code=? and stock_date=?", row.get("stock_code"),date);
					}
				}
			}
		}
	}
	
	/*
	 * 当天量增幅
	 */
	public void dealNumPercent(final Date date) throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select stock_code,                                                                                                                                  ");
		sql.append("  (day1-day2)/day2 * 100 jump                                                                                                                   ");
		sql.append(" from (select stock_code,                                                                                                                            ");
		sql.append(" STOCK_DEAL_NUM day1,                                                                                                                     ");
		sql.append(" (select STOCK_DEAL_NUM from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2  ");
		sql.append(" from data_day d                                                                                                                          ");
		sql.append(" where stock_date=?) t                                                                                                         ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set deal_num_percent=? where stock_code=? and stock_date=?", row.get("jump")==null?null:((Number)row.get("jump")).floatValue(), row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,deal_num_percent) values(?,?,?)", row.get("stock_code"),date, row.get("jump")==null?null:((Number)row.get("jump")).floatValue());
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set deal_num_percent=? where stock_code=? and stock_date=?",  row.get("jump")==null?null:((Number)row.get("jump")).floatValue(),row.get("stock_code"),date);
					}
				}
			}
		}
	}
	
	/*
	 * 当天价增幅
	 */
	public void dealAmountPercent(final Date date) throws Exception{
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select stock_code,                                                                                                                                  ");
		sql.append("  (day1-day2)/day2 * 100 jump                                                                                                                   ");
		sql.append(" from (select  stock_code,                                                                                                                           ");
		sql.append(" STOCK_DEAL_AMOUNT day1,                                                                                                                     ");
		sql.append(" (select STOCK_DEAL_AMOUNT from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2  ");
		sql.append(" from data_day d                                                                                                                          ");
		sql.append(" where stock_date=?) t                                                                                                         ");
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString() , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set deal_amount_percent=? where stock_code=? and stock_date=?", row.get("jump")==null?null:((Number)row.get("jump")).floatValue(), row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,deal_amount_percent) values(?,?,?)", row.get("stock_code"),date, row.get("jump")==null?null:((Number)row.get("jump")).floatValue());
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set deal_amount_percent=? where stock_code=? and stock_date=?",  row.get("jump")==null?null:((Number)row.get("jump")).floatValue(),row.get("stock_code"),date);
					}
				}
			}
		}
	}
	
	/*
	 * 当天涨幅
	 */
	public void jumpPercent(final Date date) throws SQLException, Exception{
		String sql  = "select stock_code, (stock_end - STOCK_YESTERDAY_END) / STOCK_YESTERDAY_END * 100 jump from data_day where stock_date=?";
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set jump_percent=? where stock_code=? and stock_date=?", row.get("jump")==null?null:((Number)row.get("jump")).floatValue(), row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,jump_percent) values(?,?,?)", row.get("stock_code"),date, row.get("jump")==null?null:((Number)row.get("jump")).floatValue());
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set jump_percent=? where stock_code=? and stock_date=?",  row.get("jump")==null?null:((Number)row.get("jump")).floatValue(),row.get("stock_code"),date);
					}
				}
			}
			
		}
	}

	/*
	 * 当天增幅
	 */
	public void amplitudePercent(final Date date) throws SQLException, Exception{
		String sql  = "select stock_code,(stock_end - stock_start) / stock_start * 100 jump from data_day where stock_date=?";
		
		final List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql , date);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			for(Map<String,Object> row :list){
				if(this.isExsit((String) row.get("stock_code"), date)){
					this.forecastDao.executeSQL("update stock_tags set amplitude_percent=? where stock_code=? and stock_date=?", row.get("jump")==null?null:((Number)row.get("jump")).floatValue(), row.get("stock_code"),date);
				}else{
					try{
						this.forecastDao.executeSQL("insert into stock_tags(stock_code,stock_date,amplitude_percent) values(?,?,?)", row.get("stock_code"),date, row.get("jump")==null?null:((Number)row.get("jump")).floatValue());
					}catch(Exception e){
						this.forecastDao.executeSQL("update stock_tags set amplitude_percent=? where stock_code=? and stock_date=?",  row.get("jump")==null?null:((Number)row.get("jump")).floatValue(),row.get("stock_code"),date);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select stock_code,                                                                                                                                  ");
		sql.append("  (day1-day2)/day2 * 100 jump                                                                                                                   ");
		sql.append(" from (select stock_code,                                                                                                                            ");
		sql.append(" STOCK_DEAL_NUM day1,                                                                                                                     ");
		sql.append(" (select STOCK_DEAL_NUM from data_day where stock_code=d.stock_code and stock_date<d.stock_date order by stock_date desc limit 0,1) day2  ");
		sql.append(" from data_day d                                                                                                                          ");
		sql.append(" where stock_date=?) t                                                                                                         ");
		
		System.out.println(sql.toString());
	}
}
