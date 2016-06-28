package com.ywlqi.stock.forecast.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.HttpGet;
import com.zhaopin.common.util.Regex;
import com.zhaopin.common.util.ResultSet;

@Service
public class DzjyService extends BaseService{
	
	private static final Logger logger = Logger.getLogger(DzjyService.class);
	
	public void getDzjyByToday(){
		try {
			Date today = DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
			this.getDzjyByDate(today, 1);
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}

	public void getDzjyByDate(Date date,int page) throws Exception{
		
		while(true){
			Boolean needNext = this.getDzjyByPage(page, date);
			
			if(!needNext){
				break;
			}else{
				page ++;
			}
		}
	}
	
	private Boolean getDzjyByPage(int page,Date date) throws Exception{
		Boolean needNext = true;
		
		String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vInvestConsult/kind/dzjy/index.phtml?p="+page;
		
		String body = this.httpGet.getBodyToString(url, "GB2312", null, "GET");
		
		body = body.replaceAll("\\n", "").replaceAll("\\r", "");
		String partten = "<tr >        <td>(.*?)</td>        <td style=\"text-align:center\"><a href=\"http://biz.finance.sina.com.cn/suggest/lookup_n.php\\?q=\\d+\" target=\"_blank\">(\\d+)</a></td>        <td style=\"text-align:center\"><a href=\"http://biz.finance.sina.com.cn/suggest/lookup_n.php\\??q=\\d+\" target=\"_blank\">.*?</a></td>        <td>(.*?)</td>        <td>(.*?)</td>        <td>(.*?)</td>        <td style=\"text-align:center\">(.*?)</td>        <td style=\"text-align:center\">(.*?)</td>        <td>.*?</td>        </tr>";
		List<ResultSet> list = Regex.matchMRowMField(body, partten);
		
		List<ResultSet> dzjyList = new ArrayList();
		
		if(CollectionUtils.isNotEmpty(list)){
			for(ResultSet row : list){
				Date d = row.getDate(0, "yyyy-MM-dd");
				
				if(d.before(date)){
					needNext = false;
					break;
				}
				
				dzjyList.add(row);
			}
			
			if(CollectionUtils.isNotEmpty(dzjyList)){
				this.saveDzjyDetail(dzjyList);
			}
		}
		
		return needNext;
		
	}
	
	private void saveDzjyDetail(final List<ResultSet> dzjyList){
		String sql = "insert into data_dzjy_detail(stock_code,stock_date,buy_org_name,sell_org_name,dzjy_price,dzjy_volume,dzjy_amount) values(?,?,?,?,?,?,?)";
		try{
		
			this.forecastDao.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ResultSet rs = dzjyList.get(i);
					ps.setString(1, rs.getString(1));
					ps.setDate(2, new java.sql.Date(rs.getDate(0,"yyyy-MM-dd").getTime()));
					ps.setString(3, rs.getString(5));
					ps.setString(4, rs.getString(6));
					ps.setDouble(5, rs.getDouble(2));
					ps.setDouble(6, rs.getDouble(3));
					ps.setDouble(7, rs.getDouble(4));
					
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return dzjyList.size();
				}
			});
		}catch(Exception e){
			logger.error("",e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vInvestConsult/kind/dzjy/index.phtml?p="+4;
		
		String body = new HttpGet().getBodyToString(url, "GB2312", null, "GET");
		
		body = body.replaceAll("\\n", "").replaceAll("\\r", "");
		String partten = "<tr >        <td>(.*?)</td>        <td style=\"text-align:center\"><a href=\"http://biz.finance.sina.com.cn/suggest/lookup_n.php\\?q=\\d+\" target=\"_blank\">(\\d+)</a></td>        <td style=\"text-align:center\"><a href=\"http://biz.finance.sina.com.cn/suggest/lookup_n.php\\??q=\\d+\" target=\"_blank\">.*?</a></td>        <td>(.*?)</td>        <td>(.*?)</td>        <td>(.*?)</td>        <td style=\"text-align:center\">(.*?)</td>        <td style=\"text-align:center\">(.*?)</td>        <td>.*?</td>        </tr>";
		List<ResultSet> list = Regex.matchMRowMField(body, partten);
		System.out.println("============="+list.size());
		for(ResultSet row : list){
			System.out.println(row.getString(0));
		}
		
	}
}
