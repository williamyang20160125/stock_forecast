package com.ywlqi.stock.forecast;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import com.ywlqi.stock.forecast.service.WeixinService;
import com.zhaopin.common.util.DateUtil;

public class WeixinTest  extends BaseTestCase{
	
//	@Test
//	public void testInitStock(){
//		WeixinService WeixinService = (WeixinService)context.getBean("weixinService");
//		
//		try {
//			WeixinService.sendYesterdayLhbTop();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testSendToPerson(){
		WeixinService WeixinService = (WeixinService)context.getBean("weixinService");
		
		try {
			Date date = DateUtil.parse("2016-06-15", "yyyy-MM-dd");
			WeixinService.sendKeyOrgStock(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendToPerson2(){
		WeixinService WeixinService = (WeixinService)context.getBean("weixinService");
		
		try {
			Date date = DateUtil.parse("2016-06-15", "yyyy-MM-dd");
			WeixinService.sendKeyOrgStock2(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendSelections(){
		WeixinService WeixinService = (WeixinService)context.getBean("weixinService");
		
		try {
			Date date = DateUtil.parse("2016-06-15", "yyyy-MM-dd");
			WeixinService.sendSelectionsByDate(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		String CropID="wx8dbca481cdb4f707";
		String Secret="df6eD7qgNjAxN0wA5JkAecbJcGrgr8tKHpk23KQcC6L63i0mdCZQmPGnVhUTdWHf";
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+CropID+"&corpsecret="+Secret;
		
//		String body = httpGet.getBodyToString(url, "UTF-8", null, "POST");
		
//		String access_token = JSON.parseObject(body).getString("access_token");
//		String PURL="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+access_token;
		
//		WeixinMsgBody weixin = new WeixinMsgBody();
//		weixin.setTouser("william.yang@zhaopin.com.cn");
//		weixin.setMsgtype("text");
//		weixin.setAgentid("2");
//		weixin.setText( JSONObject.parseObject("{\"content\":\"测试中文\"}"));
//		weixin.setSafe("0");
//		String data = JSON.toJSONString(weixin);
//		System.out.println(data);
//		Map map = new HashMap();
//		map.put("touser", "william.yang@zhaopin.com.cn");
//		map.put("msgtype", "text");
//		map.put("agentid", "2");
//		map.put("text", JSONObject.parseObject("{\"content\":\"测试中文\"}"));
//		map.put("safe", "0");
//		
//		String  bb = httpGet.getBodyToString(PURL, "UTF-8", map, "POST");
//		
//		System.out.println(bb);
		
		



		
		
	}
}
