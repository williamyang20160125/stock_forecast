package com.ywlqi.stock.forecast.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ywlqi.stock.forecast.model.DataLhb;
import com.ywlqi.stock.forecast.model.DataLhbDetail;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.HttpGet;
import com.zhaopin.common.util.Regex;
import com.zhaopin.common.util.ResultSet;

@Service
public class LhbService extends BaseService {
	private static final Logger logger = Logger.getLogger(LhbService.class);
	
	public void getTodayLhbData() throws Exception{
		Date today = DateUtil.parse( DateUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
		
		getTodayLhbData(today);
		
	}
	
	public void getTodayLhbData(Date date) throws Exception{
		int page = 1;
		
		while(true){
			boolean needNext = this.getLhbData(date, page);
			
			if(needNext){
				page = page + 1;
			}else{
				break;
			}
		}
		
		this.getLhbDataDetailByDate(date);
		
	}
	
	public boolean getLhbData(Date date,int page) throws Exception{
		String url ="http://vip.stock.finance.sina.com.cn/q/go.php/vLHBData/kind/jgmx/index.phtml?p="+page;
		
		String body = this.httpGet.getBodyToString(url, "GB2312", null, "GET");
		body = body.replaceAll("\\n", "");
		body = body.replaceAll("\\r", "");
		
		String pattern = "<tr>\\s+<td><a .*?>(\\d+)</a></td>\\s+<td><a .*?>.*?</a></td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+</tr>";
		
		List<ResultSet > list = Regex.matchMRowMField(body, pattern);
		List<DataLhb > dataLhbList = new ArrayList<DataLhb >();
		boolean needNext = true;
		
		for(ResultSet row:list){
			Date d = DateUtil.parse(row.getString(1),"yyyy-MM-dd");
			
//			if(!d.equals(date)){
//				needNext = false;
//				break;
//			}
			
			if(d.before(date)){
				needNext = false;
				break;
			}
			
			DataLhb dataLhb = new DataLhb();
			
			dataLhb.setStockCode(row.getString(0));
			dataLhb.setLhbBuy(Double.valueOf(row.getDouble(2)).floatValue());
			dataLhb.setLhbSell(Double.valueOf(row.getDouble(3)).floatValue());
			dataLhb.setLhbReason(row.getString(4));
			dataLhb.setStockDate(d);
			
			dataLhbList.add(dataLhb);
		}
		if(CollectionUtils.isNotEmpty(dataLhbList)){
			this.saveLhbData(dataLhbList);
		}
		return needNext;
		
	}
	
	private void saveLhbData(final List<DataLhb > dataLhbList){
		String sql = "replace into data_lhb(stock_code,stock_date,lhb_buy,lhb_sell,lhb_reason) values(?,?,?,?,?)";
		
		this.forecastDao.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				DataLhb dataLhb = dataLhbList.get(i);
				ps.setString(1, dataLhb.getStockCode());
				ps.setDate(2, new java.sql.Date(dataLhb.getStockDate().getTime()));
				ps.setFloat(3, dataLhb.getLhbBuy());
				ps.setFloat(4, dataLhb.getLhbSell());
				ps.setString(5, dataLhb.getLhbReason());
			}
			
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return dataLhbList.size();
			}
		});
	}
	
	public void getTodayLhbDataDetail() throws Exception{
		Date today = DateUtil.parse( DateUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
		
		getLhbDataDetailByDate(today);
	}
	
	public void getLhbDataDetailByDate(Date date) throws CannotGetJdbcConnectionException, SQLException, Exception{
		String sql = "select * from data_lhb t where stock_date=?";
		
		List<DataLhb> list = this.forecastDao.findListBySQL(sql, DataLhb.class, date);
		
		if(CollectionUtils.isNotEmpty(list)){
			for(DataLhb lhb:list){
				getLhbDataDetail(lhb.getStockCode(),date,"01");
				getLhbDataDetail(lhb.getStockCode(),date,"02");
				getLhbDataDetail(lhb.getStockCode(),date,"03");
				getLhbDataDetail(lhb.getStockCode(),date,"04");
				getLhbDataDetail(lhb.getStockCode(),date,"05");
				getLhbDataDetail(lhb.getStockCode(),date,"06");
				getLhbDataDetail(lhb.getStockCode(),date,"07");
				getLhbDataDetail(lhb.getStockCode(),date,"08");
				getLhbDataDetail(lhb.getStockCode(),date,"09");
				getLhbDataDetail(lhb.getStockCode(),date,"10");
				getLhbDataDetail(lhb.getStockCode(),date,"11");
				getLhbDataDetail(lhb.getStockCode(),date,"12");
				getLhbDataDetail(lhb.getStockCode(),date,"13");
				getLhbDataDetail(lhb.getStockCode(),date,"14");
				getLhbDataDetail(lhb.getStockCode(),date,"15");
				getLhbDataDetail(lhb.getStockCode(),date,"16");
				getLhbDataDetail(lhb.getStockCode(),date,"17");
				getLhbDataDetail(lhb.getStockCode(),date,"18");
				getLhbDataDetail(lhb.getStockCode(),date,"19");
				getLhbDataDetail(lhb.getStockCode(),date,"20");
				getLhbDataDetail(lhb.getStockCode(),date,"21");
				getLhbDataDetail(lhb.getStockCode(),date,"22");
				getLhbDataDetail(lhb.getStockCode(),date,"23");
				getLhbDataDetail(lhb.getStockCode(),date,"24");
				getLhbDataDetail(lhb.getStockCode(),date,"25");
				getLhbDataDetail(lhb.getStockCode(),date,"26");
				getLhbDataDetail(lhb.getStockCode(),date,"27");
				getLhbDataDetail(lhb.getStockCode(),date,"28");
				getLhbDataDetail(lhb.getStockCode(),date,"29");
				getLhbDataDetail(lhb.getStockCode(),date,"30");
			}
		}
	}
	
	public void getLhbDataDetail(String stockCode,Date date,String type){
		try{
			String dateStr = DateUtil.format(date,"yyyy-MM-dd");
			String url = "http://vip.stock.finance.sina.com.cn/q/api/jsonp.php/var%20details=/InvestConsultService.getLHBComBSData?symbol="+stockCode+"&tradedate="+dateStr+"&type="+type;
			String body = new HttpGet(10000).getBodyToString(url, "GB2312", null, "GET");
			
			logger.info("url==="+url);
			
			body = body.replaceAll("\\n", "").replaceAll("\\r", "");
			String json = Regex.matchSRowSField(body, "\\(\\((.*?)\\)\\)", false);
			
			JSONObject jSONObject = JSON.parseObject(json);
			
			JSONArray buyList = jSONObject.getJSONArray("buy");
			JSONArray sellList = jSONObject.getJSONArray("sell");
			
			if(buyList!= null && buyList.size()>0){
				this.saveLhbDataDetail(buyList,date,DataLhbDetail.getgetLhbReasonBySina(type));
				initOrganization(buyList);
			}
			if(sellList!= null && sellList.size()>0){
				this.saveLhbDataDetail(sellList,date,DataLhbDetail.getgetLhbReasonBySina(type));
				initOrganization(sellList);
			}
		}catch(Exception e){
			logger.error("getLhbDataDetail error!code:"+stockCode+",date="+date+",type="+type,e);
		}
	}
	
	private void saveLhbDataDetail(final JSONArray list,final Date date,final String typeStr){
		
		String sql = "replace into data_lhb_detail(stock_code,stock_date,org_code,lhb_reason,lhb_buy,lhb_sell,lhb_net_amount) values(?,?,?,?,?,?,?)";
		try{
			this.forecastDao.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					JSONObject obj = list.getJSONObject(i);
					
					ps.setString(1, obj.getString("SYMBOL"));
					ps.setDate(2, new java.sql.Date(date.getTime()));
					ps.setString(3, obj.getString("comCode"));
					ps.setString(4, typeStr);
					ps.setFloat(5, Float.valueOf(obj.getString("buyAmount")));
					ps.setFloat(6,  Float.valueOf(obj.getString("sellAmount")));
					ps.setFloat(7, obj.getFloat("netAmount"));
					
				}
				
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return list.size();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void initOrganization(final JSONArray list){
		String sql = "replace into organization(org_code,org_name) values(?,?)";
		
		this.forecastDao.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				JSONObject obj = list.getJSONObject(i);
				ps.setString(1, obj.getString("comCode"));
				ps.setString(2, obj.getString("comName"));
			}
			
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
	}
	
	
}
