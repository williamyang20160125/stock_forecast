package com.ywlqi.stock.forecast.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ywlqi.stock.forecast.model.DataDay;
import com.ywlqi.stock.forecast.model.Stock;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.HttpGet;
import com.zhaopin.common.util.Regex;
import com.zhaopin.common.util.ResultSet;
import com.zhaopin.common.util.ThreadPool;

@Service
public class StockService extends BaseService{
	private static final Logger logger = Logger.getLogger(StockService.class);
	
	private Map<Integer,String> pageCache = new HashMap<Integer,String>();
	
	public void initStock() throws Exception{
		int pages = this.getPages();
		logger.info("pages===="+pages);
		for(int i=1;i<=pages;i++){
			try {
				initStockByPage(i);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private int getPages() throws Exception{
		String url="http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/index.aspx?type=s&sortType=C&sortRule=-1&pageSize=40&page=1&jsName=quote_123&style=33&token=44c9d251add88e27b65ed86506f6e5da&_g=0.47358110168675904";
		String body = httpGet.getBodyToString(url, "UTF-8", null, "GET");
		
		String jsonStr = body.substring(body.indexOf("=")+1);
		
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		
		return jsonObj.getIntValue("pages");
	}
	
	private void initStockByPage(Integer page) throws SQLException, Exception{
		String url="http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/index.aspx?type=s&sortType=C&sortRule=-1&pageSize=40&page="+page+"&jsName=quote_123&style=33&token=44c9d251add88e27b65ed86506f6e5da&_g=0.47358110168675904";
		String body = httpGet.getBodyToString(url, "UTF-8", null, "GET");
		
		String jsonStr = body.substring(body.indexOf("=")+1);
		
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		
		final JSONArray array = jsonObj.getJSONArray("rank");
		
		String sql = "replace into stock(stock_name,stock_code,total_value,circulation_value,pe_ratios,pb_ratios) values(?,?,?,?,?,?)";
//		String sql = "update stock set stock_name = ? where stock_code = ?";
		
		for(int i=0;i<array.size();i++){
			String row = array.getString(i);
			String[] rowArr = row.split(",");
			
//			List list = this.forecastDao.findListBySQL("select 1 from stock where stock_code = ?", objects)
			
			this.forecastDao.executeSQL(sql,rowArr[2], rowArr[1],32,4,64,4);
		}
		/*try{
			forecastDao.batchUpdate("insert into stock(stock_code,stock_name) values(?,?)", new BatchPreparedStatementSetter() {
				
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					String row = array.getString(i);
					String[] rowArr = row.split(",");
					ps.setString(1, rowArr[1]);
					ps.setString(2, rowArr[2]);
				}
				
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return array.size();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}*/
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
		String url = "http://hq.sinajs.cn/list="+stock.getFullStockCode();
		String body = httpGet.getBodyToString(url, "GBK", null, "GET");
		
		DataDay dataDay = DataDay.toBeanBySinaAPI(body,stock.getStockCode());
		
		return dataDay;

//		return null;
	}
	
	public void getTodayStockData(){
		try {
			Date today = DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd"));
			Date end = DateUtil.addDay(today, -3);
			for(;end.before(today);end = DateUtil.addDay(end, 1)){
				this.getHistoryData(end);
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
	
	
	public void getHistoryData(final Date date) throws CannotGetJdbcConnectionException, SQLException, Exception{
		List<Stock> stockList = this.forecastDao.findListBySQL("select * from stock", Stock.class);
		for(final Stock stock :stockList){
			
			DataDay data = getHistoryData(stock.getStockCode(), date);
			if(data != null){
				try {
					saveDataDay(data);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private String parseJidu(Integer month){
		if(month<=3){
			return "1";
		}else if (month >3 && month <=6){
			return "2";
		}else if(month>6 && month <=9){
			return "3";
		}else{
			return "4";
		}
	}
	
	public DataDay getHistoryData(String stockCode,Date date) throws Exception{
		String year = DateUtil.format(date,"yyyy");
		Integer month = Integer.valueOf(DateUtil.format(date,"MM"));
		String jidu = this.parseJidu(month);
		String url = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/"+stockCode+".phtml?year="+year+"&jidu="+jidu;
		String body = this.pageCache.get(url.hashCode());
//		String body = null;
		
		if(body == null){
			logger.info("url===="+url);
//			body =  new HttpGet("192.168.20.6",3128).getBodyToString(url, "GB2312", null, "GET");
			body =  this.httpGet.getBodyToString(url, "GB2312", null, "GET");
			body = body.replaceAll("\\n", "").replaceAll("\\r", "");
			this.pageCache.put(url.hashCode(), body);
		}
		
		String partten = "<tr >\\s+<td><div align=\"center\">\\s+<a .*?>\\s+"+DateUtil.format(date,"yyyy-MM-dd")+"\\s+</a>\\s+</div></td>\\s+<td><div align=\"center\">(.*?)</div></td>\\s+<td><div align=\"center\">(.*?)</div></td>\\s+<td><div align=\"center\">(.*?)</div></td>\\s+<td class=\"tdr\"><div align=\"center\">(.*?)</div></td>\\s+<td class=\"tdr\"><div align=\"center\">(.*?)</div></td>\\s+<td class=\"tdr\"><div align=\"center\">(.*?)</div></td>\\s+</tr>";
		
		ResultSet rs = Regex.matchSRowMField(body, partten);
		DataDay data = null;
		if(rs!= null){
			data = new DataDay();
			data.setStockCode(stockCode);
			data.setStockDate(date);
			data.setStockStart(Double.valueOf(rs.getDouble(0)).floatValue());
			data.setStockMax(Double.valueOf(rs.getDouble(1)).floatValue());
			data.setStockEnd(Double.valueOf(rs.getDouble(2)).floatValue());
			data.setStockMin(Double.valueOf(rs.getDouble(3)).floatValue());
			data.setStockDealNum(Double.valueOf(rs.getDouble(4)).floatValue());
			data.setStockDealAmount(Double.valueOf(rs.getDouble(5)).floatValue());
			
		}
		
		return data;
		
	}
	
	public void saveDataDay(DataDay dataDay) throws SQLException, Exception{
		String sql = "replace into data_day(stock_code,stock_start,stock_end,stock_min,stock_max,stock_date,stock_deal_num,stock_deal_amount,stock_yesterday_end) values(?,?,?,?,?,?,?,?,?)";
		
		StringBuffer yesterday = new StringBuffer();
		
		yesterday.append("select stock_end                                 ");
		yesterday.append("  from data_day dd                               ");
		yesterday.append(" where dd.stock_code = ?                         ");
		yesterday.append("   and dd.stock_date = (select max(stock_date)   ");
		yesterday.append("                          from data_day d        ");
		yesterday.append("                         where d.stock_code = ?  ");
		yesterday.append("                           and d.stock_date < ?) ");
		
		Float yesterdayEnd = null;
		
		List<Map<String,Object>> yesterdayList = this.forecastDao.findListBySQL(yesterday.toString(), dataDay.getStockCode(),dataDay.getStockCode(),dataDay.getStockDate());
		
		if(CollectionUtils.isNotEmpty(yesterdayList)){
			try{
				yesterdayEnd = ((Number)yesterdayList.get(0).get("stock_end")).floatValue();
			}catch(Exception e){
				logger.error("get yesterday end error!!!",e);
			}
		}
		
		
		this.forecastDao.executeSQL(sql, 
				dataDay.getStockCode(),
				dataDay.getStockStart(),
				dataDay.getStockEnd(),
				dataDay.getStockMin(),
				dataDay.getStockMax(),
				dataDay.getStockDate(),
				dataDay.getStockDealNum(),
				dataDay.getStockDealAmount(),
				yesterdayEnd);
	}
	
	public void initYesterDay(Date startDate){
		
		String sql = "select * from data_day t where STOCK_DATE>=? order by t.STOCK_CODE ,STOCK_DATE ";
		
		String update = "update data_day set STOCK_YESTERDAY_END = ? where STOCK_CODE = ? and STOCK_DATE = ?";
		
		try {
			List<DataDay> list = this.forecastDao.findListBySQL(sql, DataDay.class,startDate);
			
			if(CollectionUtils.isNotEmpty(list)){
				String tempCode = list.get(0).getStockCode();
				for(int i=1;i<list.size();i++){
					DataDay data = list.get(i);
					DataDay before = list.get(i-1);
					
					if(data.getStockCode().equals(tempCode)){
						this.forecastDao.executeSQL(update, before.getStockEnd(),data.getStockCode(),data.getStockDate());
					}else{
						tempCode = data.getStockCode();
					}
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
	
	public void initStockClass(){
		String sql = "select stock_code from stock ";
		
		try {
			List<Stock> list = this.forecastDao.findListBySQL(sql, Stock.class);
			
			for(Stock stock:list){
				this.getStockClass(stock.getStockCode());
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
	
	private void getStockClass(String stockCode) throws Exception{
		String url = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CorpOtherInfo/stockid/"+stockCode+"/menu_num/5.phtml";
		
		String body = this.httpGet.getBodyToString(url, "GB2312", null, "GET");
		
		body = body.replaceAll("\\n", "").replaceAll("\\r", "");
		String p1 = "<td class=\"ct\" align=\"center\">(.*?)</td>		<td class=\"ct\" align=\"center\"><a target=\"_blank\" href=\"/corp/view/vCI_CorpInfoLink\\.php.*?\">";
		
		String p2 = "<td class=\"ct\" align=\"center\">(.*?)</td>		<td class=\"ct\" align=\"center\"><a target=\"_blank\" href=\"http://vip\\.stock\\.finance\\.sina\\.com\\.cn/mkt/.*?\">";
		
		List<String> classList1 = Regex.matchMRowSField(body, p1, false);
		List<String> classList2 = Regex.matchMRowSField(body, p2, false);
		
		for(String name:classList1){
			if(StringUtils.isBlank(name)){
				logger.error("name is blank!!! stockCode is "+stockCode);
				continue;
			}
			try {
				this.saveStockClass(stockCode, name, "证监会");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
				logger.error("name=="+name,e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("name=="+name,e);
			}
		}
		for(String name:classList2){
			if(StringUtils.isBlank(name)){
				logger.error("name is blank!!! stockCode is "+stockCode);
				continue;
			}
			try {
				this.saveStockClass(stockCode, name, "新浪概念板块");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("name=="+name,e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("name=="+name,e);
			}
		}
		
	}
	
	private void saveStockClass(String stockCode,String name ,String type) throws SQLException, Exception{
		String sql1 = "replace into stock_class(stock_class_name,stock_class_type) values(?,?)";
		String sql2 = "replace into rel_stock_class(stock_code,stock_class_name) values(?,?)";
		
		this.forecastDao.executeSQL(sql1, name,type);
		this.forecastDao.executeSQL(sql2, stockCode,name);
		
	}
	
	
	
	public static void main(String[] args) {	
		System.out.println(Integer.valueOf("09"));
	}
}
