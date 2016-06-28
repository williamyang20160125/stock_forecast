package com.ywlqi.stock.forecast.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.HttpGet;
import com.zhaopin.common.util.Regex;
import com.zhaopin.common.util.ResultSet;
import com.zhaopin.common.util.ThreadPool;

@Service 
public class Lhb2Service extends BaseService{
	private static final Logger logger = Logger.getLogger(Lhb2Service.class);
	@Autowired
	private LhbService lhbService;
	
	public void getTodayLhbData(){
		logger.info("getTodayLhbData is running..................");
		try {
			Date today = DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd"));
			Date end = DateUtil.addDay(today, -2);
			for(;end.getTime()<=today.getTime();end = DateUtil.addDay(end, 1)){
				logger.info("getTodayLhbData ready...."+end);
				this.getLhbDataAndDetailByDate(end);
				logger.info("getTodayLhbData done...."+end);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
	}
	
	public void getLhbDataAndDetailByDate(final Date date) throws CannotGetJdbcConnectionException, SQLException, Exception{
		getLhbDataByDate(date);
		
	}
	
	public void getLhbDataByDate(Date date) throws Exception{
		String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vInvestConsult/kind/lhb/index.phtml?tradedate="+DateUtil.format(date, "yyyy-MM-dd");
		String body = this.httpGet.getBodyToString(url, "GB2312", null, "GET");
		System.out.println("url=="+url);
		body = body.replaceAll("\\n", "").replaceAll("\\r", "");
		String pattern = "<td .*?>\\s+<a href=\"http://biz\\.finance\\.sina\\.com\\.cn/suggest/lookup_n\\.php\\?q=\\d+\".*?>(\\d+)</a>";
		
		
		List<String> stockCodeList = Regex.matchMRowSField(body, pattern, false);
		
		if(CollectionUtils.isNotEmpty(stockCodeList)){
			Set<String> stockCodeSet = new HashSet<String>();
			for(String code:stockCodeList){
				stockCodeSet.add(code);
			}
			try{
				saveLhbData(stockCodeSet,date);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			this.saveLhbDataDetail(body);
		}
		
	}
	
	private void saveLhbDataDetail(String body){
		String pattern2 = "onclick=\"showDetail\\('(\\d+)','(\\d+)','(.*?)',this\\)";
		List<ResultSet> stockLhbDetailList = Regex.matchMRowMField(body, pattern2);

		if(CollectionUtils.isNotEmpty(stockLhbDetailList)){
			
			for(ResultSet rs:stockLhbDetailList){
				try{
					this.lhbService.getLhbDataDetail(rs.getString(1), DateUtil.parse(rs.getString(2),"yyyy-MM-dd"), rs.getString(0));
				}catch(Exception e){
					logger.error("",e);
				}
				
			}
			
		}
	}
	
	private void saveLhbData(final Set<String> stockCodeSet,final Date date){
		
		final Object[] objArr = stockCodeSet.toArray();
		try{
			this.forecastDao.batchUpdate("replace into data_lhb(stock_code,stock_date) values(?,?)", new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					String code = (String) objArr[i];
					
					ps.setString(1, code);
					ps.setDate(2, new java.sql.Date(date.getTime()));
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return stockCodeSet.size();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
