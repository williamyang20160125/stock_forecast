package com.ywlqi.stock.forecast.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SelectionService extends BaseService{
	private static final Logger logger = Logger.getLogger(SelectionService.class);
	
	/*
	 * 收盘价和最高价与昨收的比值差不超过20%
	 * 成交量较前一日增长超过100%
	 * 当天上涨
	 * 收盘价比开盘价高
	 * 流通市值小于100亿
	 * 市盈率大于0
	 * 还手率大于4.5%
	 */
	public List<Map<String,Object>> selection1(Date date) throws Exception{
		
//		String sql = "select s.stock_code,s.stock_name from stock_tags t,stock s  where t.stock_code = s.stock_code and  stock_date=? and deal_num_percent>100 and jump_percent>0 and amplitude_percent>0 and s.circulation_value<100 and s.pe_ratios>0 order by circulation_value";
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT   s.stock_code,s.stock_name                                                                                                              ");
		sql.append(" FROM stock_tags t, stock s, data_day d                                                                                                          ");
		sql.append(" WHERE t.stock_code = s.stock_code                                                                                                               ");
		sql.append(" 	AND d.stock_code = t.stock_code                                                                                                              ");
		sql.append(" 	AND ((d.STOCK_END - d.STOCK_YESTERDAY_END) / d.STOCK_YESTERDAY_END ) / ((d.STOCK_MAX - d.STOCK_YESTERDAY_END) / d.STOCK_YESTERDAY_END) >0.8  ");
		sql.append(" 	AND d.stock_date = ?                                                                                                              ");
		sql.append(" 	AND t.stock_date = ?                                                                                                              ");
		sql.append(" 	AND t.deal_num_percent > 100                                                                                                                 ");
		sql.append(" 	AND t.jump_percent > 0                                                                                                                       ");
		sql.append(" 	AND t.amplitude_percent > 0                                                                                                                  ");
		sql.append(" 	AND s.circulation_value < 100                                                                                                                ");
		sql.append(" 	AND d.pe_ratios > 0                                                                                                                          ");
		sql.append(" 	AND d.turnover_rate > 4.5                                                                                                                    ");
		
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(), date,date);
		return list;
	}
	
	/*
	 * 三天收阳
	 * 三天增量
	 * 流通市值小于150亿
	 * 市盈率小于120
	 * 价格在5日均线上方
	 * 5日均线在10日均线上方
	 */
	public List<Map<String,Object>> selection2(Date date) throws Exception{
		
		String sql = "select s.stock_code,s.stock_name from stock_tags t,stock s where t.stock_code = s.stock_code and  t.stock_date=? and t.three_day_red=1 and three_day_up_deal_num=1 and s.circulation_value<150 and pe_ratios<120  and avg5_ratios>0 and avg5_ratios>avg10_ratios order by circulation_value";
		
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql, date);
		logger.info("list2.size=="+list.size());
		return list;
	}
	
	/*
	 * 5日穿10日
	 * 成交量较前一天增长大于等于40%
	 * 总市值小雨200亿
	 * 市盈率大于0
	 */
	public List<Map<String,Object>> selection3(Date date) throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();                                                                                                                         
		sql.append(" select s.stock_code,s.stock_name from (select t.stock_code,avg5,avg10,                                                             ");
		sql.append(" (select avg5 from stock_tags dd where dd.stock_code=t.stock_code and dd.stock_date<t.stock_date order by stock_date desc limit 0,1) y_5,  ");
		sql.append(" (select avg10 from stock_tags dd where dd.stock_code=t.stock_code and dd.stock_date<t.stock_date order by stock_date desc limit 0,1) y_10 ");
		sql.append("  from                                                                                                                                            ");
		sql.append(" stock_tags t                                                                                                                                     ");
		sql.append(" where t.stock_date=?                                                                                                                 ");
		sql.append(" and avg5>avg10                                                                                                                     ");
		sql.append(" and deal_num_percent>=40                                                                                                                          ");
		sql.append(" ) t1,stock s                                                                                                                                     ");
		sql.append("  where t1.y_10>t1.y_5                                                                                                                            ");
		sql.append(" and t1.stock_code=s.stock_code                                                                                                                   ");
		sql.append("  and total_value<200                                                                                                                             ");
		sql.append("  and pe_ratios>0                                                                                                                                 ");
		
		return this.forecastDao.findListBySQL(sql.toString(), date);
	}
	
	/*
	 * 缳首率大于10%
	 * 流通市值小于100亿
	 * PE大于0
	 * 当天涨幅大于5%
	 */
	
	public List<Map<String,Object>> selection4(Date date) throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select s.stock_code,s.stock_name       ");
		sql.append("  from data_day dd,stock_tags t,stock s ");
		sql.append(" where dd.stock_code = t.stock_code     ");
		sql.append("  and dd.stock_date = t.stock_date      ");
		sql.append("  and dd.stock_code = s.stock_code      ");
		sql.append("  and dd.stock_date=?        ");
		sql.append("  and dd.turnover_rate>10               ");
		sql.append("  and dd.CIRCULATION_VALUE<100          ");
		sql.append("  and t.deal_num_percent>80             ");
		sql.append("  and dd.PE_RATIOS>0                    ");
		sql.append("  and JUMP_PERCENT>5                   ");
		
		return this.forecastDao.findListBySQL(sql.toString(), date);
	}
}
