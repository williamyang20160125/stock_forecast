package com.ywlqi.stock.forecast.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ywlqi.stock.forecast.model.WeixinMsgBody;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.HttpGet;

@Service
public class WeixinService  extends BaseService{
	private static final Logger logger = Logger.getLogger(WeixinService.class);
	
	@Autowired
	private SelectionService selectionService;
	
	private String keyOrgs = "'19999990',"+
			"'19999991',"+
			"'19999992',"+
			"'19999993',"+
			"'19999994',"+
			"'80094766',"+
			"'80032107',"+
			"'80154611',"+
			"'80120033',"+
			"'80190854',"+
			"'80032851',"+
			"'80095368',"+
			"'80034119',"+
			"'80152193',"+
			"'80033261',"+
			"'80119604',"+
			"'80174625',"+
			"'80086086',"+
			"'80096656',"+
			"'80033660' ";
	
	private String importantOrgs1 = "'19999990',"+
			"'19999991',"+
			"'19999992',"+
			"'19999993',"+
			"'19999994'";
	
	private String importantOrgs2 = "'80174625',"+
			"'80190854',"+
			"'80154611',"+
			"'80032107',"+
			"'80094766'";
	
	private String ZGBFT_CropID="wxb4f20ef16a85a34c";
	private String ZGBFT_Secret="NtZywX6NQJUkh4kgxHmjpnf0LcsFU8nAxC8EqDRKPP5uucjUECOqyqkTFgkfMkAQ";
	
	private String QPHY_CropID="wx8dbca481cdb4f707";
	private String QPHY_Secret="df6eD7qgNjAxN0wA5JkAecbJcGrgr8tKHpk23KQcC6L63i0mdCZQmPGnVhUTdWHf";
	
//	private String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+CropID+"&corpsecret="+Secret;
	
	public void sendLhbTop() throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t.stock_code, s.stock_name, sum(lhb_net_amount) s");
		sql.append("  from data_lhb_detail t, stock s                      ");
		sql.append(" where t.stock_date = ?                     ");
		sql.append("   and t.stock_code = s.stock_code                     ");
		sql.append(" group by t.stock_code,s.stock_name,t.stock_date       ");
		sql.append(" order by sum(lhb_net_amount) desc                     ");
		
		
		Date date = DateUtil.parse(DateUtil.format(new Date(),"yyyyMMdd"),"yyyyMMdd");
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(), date);
		if(CollectionUtils.isNotEmpty(list)){
			String content = DateUtil.format(date,"yyyy-MM-dd")+" 龙虎榜买入排行:\r\n";
			
			for(int i=0;i<list.size();i++){
				Map<String,Object> row = list.get(i);
				content +=row.get("stock_code")+","+row.get("stock_name")+","+row.get("s")+"\r\n";
				
				
				if(i>0&&i%40==0){
//					this.send(content);
//					send2(content);
					
					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
					
					content=  DateUtil.format(date,"yyyy-MM-dd")+" 龙虎榜买入排行:\r\n";
				}
			}
			if(StringUtils.isNotBlank(content)){
//				this.send(content);
//				send2(content);
				
				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
			}
			
			
		}
	}
	
	public void sendYesterdayLhbTop() throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t.stock_code, s.stock_name, sum(lhb_net_amount) s");
		sql.append("  from data_lhb_detail t, stock s                      ");
		sql.append(" where t.stock_date = ?                     ");
		sql.append("   and t.stock_code = s.stock_code                     ");
		sql.append(" group by t.stock_code,s.stock_name,t.stock_date       ");
		sql.append(" order by sum(lhb_net_amount) desc                     ");
		
		
		Date date = DateUtil.parse(DateUtil.format(new Date(),"yyyyMMdd"),"yyyyMMdd");
		date = DateUtil.addDay(date, -1);
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(), date);
		if(CollectionUtils.isNotEmpty(list)){
			String content = DateUtil.format(date,"yyyy-MM-dd")+" 龙虎榜买入排行:\r\n";
			
			for(int i=0;i<list.size();i++){
				Map<String,Object> row = list.get(i);
				content +=row.get("stock_code")+","+row.get("stock_name")+","+row.get("s")+"\r\n";
				
				
				if(i>0&&i%40==0){
//					this.send(content);
					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
					content=  DateUtil.format(date,"yyyy-MM-dd")+" 龙虎榜买入排行:\r\n";
				}
			}
			if(StringUtils.isNotBlank(content)){
//				this.send(content);
				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
			}
			
			
		}
	}
	
	private void send2Party(String content,String CropID,String Secret,Integer anentid,String party){
		
//		String CropID="wx8dbca481cdb4f707";
		//在微信后台--设置--权限管理里设置新组获得
//		String Secret="df6eD7qgNjAxN0wA5JkAecbJcGrgr8tKHpk23KQcC6L63i0mdCZQmPGnVhUTdWHf";
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+CropID+"&corpsecret="+Secret;
		
		
		HttpGet httpGet = new HttpGet();
		String _body = null;
		try {
			_body = httpGet.getBodyToString(url, "UTF-8", null, "GET");
			String access_token = JSON.parseObject(_body).getString("access_token");
			String PURL="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+access_token;
			
			
			WeixinMsgBody weixin = new WeixinMsgBody();
//			weixin.setTouser("william.yang");
			weixin.setToparty(party);
			weixin.setMsgtype("text");
			weixin.setAgentid(anentid);
			weixin.setText( JSONObject.parseObject("{\"content\":\""+content+"\"}"));
			weixin.setSafe("0");
			
			String data = JSON.toJSONString(weixin);
			StringEntity entity = new StringEntity(data,"UTF-8");
			String body = httpGet.getBodyToString(PURL, "UTF-8", "POST", true, entity);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void sendKeyOrgStock() throws SQLException, Exception{
		Date toDay = DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
		
		this.sendKeyOrgStock(toDay);
	}
	
	public void sendKeyOrgStock(Date date) throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select s.stock_code, s.stock_name, count(*) c ");
		sql.append("  from (select d.STOCK_CODE, d.org_code       ");
		sql.append("          from data_lhb_detail d              ");
		sql.append("         where LHB_NET_AMOUNT > 0             ");
		sql.append("           and d.ORG_CODE in ("+keyOrgs+")               ");
		sql.append("           and d.STOCK_DATE = ?               ");
		sql.append("         group by STOCK_CODE, d.org_code) t,  ");
		sql.append("       stock s                                ");
		sql.append(" where s.stock_code = t.stock_code            ");
		sql.append(" group by s.stock_code                        ");
		sql.append(" order by c desc                        ");
		
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(), date);
		
		if(CollectionUtils.isNotEmpty(list)){
			String content = DateUtil.format(date,"yyyy-MM-dd")+":\r\n";
			content +="股票代码,股票名称,重点机构数量,超重点,买卖差额\r\n";
			
			for(int i=0;i<list.size();i++){
				Map<String,Object> row = list.get(i);
				
				Boolean isImport1 = checkImportant((String) row.get("stock_code"),date,this.importantOrgs1);
				Boolean isImport2 = checkImportant((String) row.get("stock_code"),date,this.importantOrgs2);
				Double amount = this.getLhbAmount((String) row.get("stock_code"), date);
				String important = (isImport1==true?"机构":"") +":" + (isImport2==true?"游资":"");
				
				content +=row.get("stock_code")+","+row.get("stock_name")+","+row.get("c")+","+important+","+amount+"\r\n";
				
				this.saveSelectedTrace(row.get("stock_code").toString(),date,"龙虎榜-超重点");
				
				if(i>0&&i%40==0){
					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
					content=  DateUtil.format(date,"yyyy-MM-dd")+":\r\n";
					content +="股票代码,股票名称,重点机构数量,超重点,买卖差额\r\n";
				}
			}
			if(StringUtils.isNotBlank(content)){
				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
			}
			
			
		}
	}
	
	private Double getLhbAmount(String stockCode,Date date) throws SQLException, Exception{
		Double amount = 0D;
		String sql = "select sum(lhb_net_amount) lhb_net_amount from data_lhb_detail t where t.stock_code = ? and t.stock_date = ?";
		
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql, stockCode,date);
		
		if(CollectionUtils.isNotEmpty(list)){
			Map<String,Object> row = list.get(0);
			amount = ((Number)row.get("lhb_net_amount")).doubleValue();
		}
		
		return amount;
	}
	
	private Boolean checkImportant(String stockCode,Date date,String orgs) throws SQLException, Exception{
		Boolean isImport = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) c           ");
		sql.append("  from data_lhb_detail t    ");
		sql.append(" where t.org_code in ("+orgs+")     ");
		sql.append("   and t.stock_date = ?     ");
		sql.append("   and t.stock_code = ?     ");
		sql.append("   and t.LHB_NET_AMOUNT > 0 ");
		
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(), date,stockCode);
		
		if(CollectionUtils.isNotEmpty(list)){
			Map<String,Object> row = list.get(0);
			Number c = (Number) row.get("c");
			
			if(c.intValue()>0){
				isImport = true;
			}
		}
		
		return isImport;
		
	}                                           
	
	private void sendToUser(String content,String user,String CropID,String Secret,Integer agentid){
		
//		String CropID="wx8dbca481cdb4f707";
		//在微信后台--设置--权限管理里设置新组获得
//		String Secret="df6eD7qgNjAxN0wA5JkAecbJcGrgr8tKHpk23KQcC6L63i0mdCZQmPGnVhUTdWHf";
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+CropID+"&corpsecret="+Secret;
		
		
		HttpGet httpGet = new HttpGet();
		String _body = null;
		try {
			_body = httpGet.getBodyToString(url, "UTF-8", null, "GET");
			String access_token = JSON.parseObject(_body).getString("access_token");
			String PURL="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+access_token;
			
			
			WeixinMsgBody weixin = new WeixinMsgBody();
			weixin.setTouser(user);
//			weixin.setToparty("1");
			weixin.setMsgtype("text");
			weixin.setAgentid(agentid);
			weixin.setText( JSONObject.parseObject("{\"content\":\""+content+"\"}"));
			weixin.setSafe("0");
			
			String data = JSON.toJSONString(weixin);
			System.out.println(data);
			StringEntity entity = new StringEntity(data,"UTF-8");

			String body = httpGet.getBodyToString(PURL, "UTF-8", "POST", true, entity);
			
			System.out.println(body);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public void sendKeyOrgStock2() throws SQLException, Exception{
		Date toDay = DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
		
		this.sendKeyOrgStock2(toDay);
	}
	
	public void sendKeyOrgStock2(Date date) throws SQLException, Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select s.stock_code, s.stock_name, count(*) c ");
		sql.append("  from (select d.STOCK_CODE, d.org_code       ");
		sql.append("          from data_lhb_detail d              ");
		sql.append("         where LHB_NET_AMOUNT > 0             ");
		sql.append("           and d.ORG_CODE in (select org_code from key_org )               ");
		sql.append("           and d.STOCK_DATE = ?               ");
		sql.append("         group by STOCK_CODE, d.org_code) t,  ");
		sql.append("       stock s                                ");
		sql.append(" where s.stock_code = t.stock_code            ");
		sql.append(" group by s.stock_code                        ");
		sql.append(" order by c desc                        ");
		
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(), date);
		
		if(CollectionUtils.isNotEmpty(list)){
			String content = DateUtil.format(date,"yyyy-MM-dd")+":\r\n";
			content +="股票代码,股票名称,重点机构数量,买卖差额\r\n";
			
			for(int i=0;i<list.size();i++){
				Map<String,Object> row = list.get(i);
				
				Double amount = this.getLhbAmount((String) row.get("stock_code"), date);
				
				content +=row.get("stock_code")+","+row.get("stock_name")+","+row.get("c")+","+amount+"\r\n";
				
				this.saveSelectedTrace(row.get("stock_code").toString(),date,"龙虎榜-重点机构");
				
				if(i>0&&i%40==0){
					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
					content=  DateUtil.format(date,"yyyy-MM-dd")+":\r\n";
					content +="股票代码,股票名称,重点机构数量,买卖差额\r\n";
				}
			}
			if(StringUtils.isNotBlank(content)){
				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
			}
			
			
		}
	}
	
	public void sendSelections() throws Exception{
		Date toDay = DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
		this.sendSelectionsByDate(toDay);
	}
	
	public void sendSelectionsByDate(Date toDay) throws Exception{
		
		
		List<Map<String,Object>> list1 = selectionService.selection1(toDay);
		List<Map<String,Object>> list2 = selectionService.selection2(toDay);
//		List<Map<String,Object>> list3 = selectionService.selection3(toDay);
		List<Map<String,Object>> list4 = selectionService.selection4(toDay);
		if(CollectionUtils.isNotEmpty(list1)){
			String content = DateUtil.format(toDay,"yyyy-MM-dd")+"当天放量:\r\n";
			content +="股票代码,股票名称\r\n";
			
			for(int i=0;i<list1.size();i++){
				Map<String,Object> row = list1.get(i);
				content +=row.get("stock_code")+","+row.get("stock_name")+"\r\n";
				
				this.saveSelectedTrace(row.get("stock_code").toString(),toDay,"当天放量");
				
				if(i>0&&i%40==0){
					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
					content=  DateUtil.format(toDay,"yyyy-MM-dd")+"当天放量:\r\n";
					content +="股票代码,股票名称\r\n";
				}
				
				
			}
			if(list1.size()%40 != 0){
				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
			}
			
		}
		
		if(CollectionUtils.isNotEmpty(list2)){
			String content = DateUtil.format(toDay,"yyyy-MM-dd")+"三天收红:\r\n";
			content +="股票代码,股票名称\r\n";
			
			for(int i=0;i<list2.size();i++){
				Map<String,Object> row = list2.get(i);
				content +=row.get("stock_code")+","+row.get("stock_name")+"\r\n";
				
				this.saveSelectedTrace(row.get("stock_code").toString(),toDay,"三天收红");
				
				if(i>0&&i%40==0){
					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
					content=  DateUtil.format(toDay,"yyyy-MM-dd")+"三天收红:\r\n";
					content +="股票代码,股票名称\r\n";
				}
				
				
			}
			if(list2.size()%40 != 0){
				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
			}
			
		}
		
//		if(CollectionUtils.isNotEmpty(list3)){
//			String content = DateUtil.format(toDay,"yyyy-MM-dd")+"五日穿十日:\r\n";
//			content +="股票代码,股票名称\r\n";
//			
//			for(int i=0;i<list3.size();i++){
//				Map<String,Object> row = list3.get(i);
//				content +=row.get("stock_code")+","+row.get("stock_name")+"\r\n";
//				
//				this.saveSelectedTrace(row.get("stock_code").toString(),toDay,"五日穿十日");
//				
//				if(i>0&&i%40==0){
//					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
//					content=  DateUtil.format(toDay,"yyyy-MM-dd")+"五日穿十日:\r\n";
//					content +="股票代码,股票名称\r\n";
//				}
//			}
//			if(list3.size()%40 != 0){
//				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
//			}
//			
//		}
		
		if(CollectionUtils.isNotEmpty(list4)){
			String content = DateUtil.format(toDay,"yyyy-MM-dd")+"高换手:\r\n";
			content +="股票代码,股票名称\r\n";
			
			for(int i=0;i<list4.size();i++){
				Map<String,Object> row = list4.get(i);
				content +=row.get("stock_code")+","+row.get("stock_name")+"\r\n";
				
				this.saveSelectedTrace(row.get("stock_code").toString(),toDay,"高换手");
				
				if(i>0&&i%40==0){
					this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
					content=  DateUtil.format(toDay,"yyyy-MM-dd")+"高换手:\r\n";
					content +="股票代码,股票名称\r\n";
				}
			}
//			if(list3.size()%40 != 0){
//				this.sendToUser(content, "william.yang@zhaopin.com.cn", this.ZGBFT_CropID, this.ZGBFT_Secret, 2);
//			}
			
		}
		
	}
	
	public void saveSelectedTrace(String stockCode,Date stockDate,String selectedName) throws SQLException, Exception{
		
		String sql = "insert into selected_trace(stock_code,stock_date,selected_name) values(?,?,?)";
		
		this.forecastDao.executeSQL(sql, stockCode,stockDate,selectedName);
		
		//save to thx self
		saveToThxSelf(stockCode);
	}
	
	private void saveToThxSelf(String stockCode){
		String url = "http://stock.10jqka.com.cn/self.php?stockcode="+stockCode+"&op=add&&jsonp=jQuery18307530131668636242_1465397475321&_=1465397639313";
		
		HttpGet httpGet = new HttpGet();
		
		Map<String,String> headers = new HashMap();
		
		headers.put("Cookie", "user=MDp3aWxsaWFtMDYwODo6Tm9uZTo1MDA6MzQ5NzE5NTk1OjcsMTExMTExMTExMTEsNDA7NDQsMTEsNDA7NiwxLDQwOzUsMSw0MDo6OjozMzk3MTk1OTU6MTQ2NTM5Mzc1Mjo6OjE0NjUzOTM3NDA6MzQyNDI0ODowOmE1YWJlMmY3Nzg1MGZkMDZlNWUwZmFkNTgyNDc3YTBlOmRlZmF1bHRfMjow; userid=339719595; u_name=william0608; escapename=william0608; ticket=b27b685c8f6b262e1b6183d1bb395b9a; BAIDU_SSP_lcr=https://www.baidu.com/link?url=olm9m1KL7j4iMVTtBpANvn4W44ERCTrlQjzk4s6WzF5Bloim9ebeT-JECNyQd3bw&wd=&eqid=e02df57b000e2c6f0000000357583044; Hm_lvt_78c58f01938e4d85eaf619eae71b4ed1=1465393703,1465397323; Hm_lpvt_78c58f01938e4d85eaf619eae71b4ed1=1465397475; Hm_lvt_f79b64788a4e377c608617fba4c736e2=1465397332,1465397476; Hm_lpvt_f79b64788a4e377c608617fba4c736e2=1465397476");
		headers.put("Accept-Encoding", "gzip, deflate, sdch");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.19 Safari/537.36");
		headers.put("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
		headers.put("Referer", "http://stock.10jqka.com.cn/my/");
		headers.put("X-Requested-With", "XMLHttpRequest");
		headers.put("Connection", "keep-alive");
		
		
		try {
			String body = httpGet.getBodyToString(url, "UTF-8", null, "GET", true, headers);
			logger.info("body=="+body);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		
	}
	
	public static void main(String[] args) {
		WeixinService service = new WeixinService();

		service.sendToUser("sss", "william.yang@zhaopin.com.cn", service.ZGBFT_CropID, service.ZGBFT_Secret, 2);
		service.sendToUser("sss", "william.yang", service.QPHY_CropID, service.QPHY_Secret, 1);
	}
}
