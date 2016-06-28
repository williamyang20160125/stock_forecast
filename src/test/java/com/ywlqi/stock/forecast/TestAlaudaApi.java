package com.ywlqi.stock.forecast;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ywlqi.stock.forecast.model.WeixinMsgBody;

public class TestAlaudaApi extends BaseTestCase{
	
	@Test
	public void testContainersConfig(){
		HttpClient httpClient = new HttpClient();
		HttpClientParams hparas = new HttpClientParams();
		
		hparas.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		hparas.setBooleanParameter("http.protocol.single-cookie-header",
				true);
		hparas.setParameter("Authorization:Token", "41bc03d4a72b9697ca82a63b8cd289684373b691");
		httpClient.setParams(hparas);
//		httpClient.getHostConfiguration().setProxy("192.168.20.6", 3128);
		
		try {
			
			String data = FileUtils.readFileToString(new File("d:/api_data.txt"), "UTF-8");
			
			PutMethod put = new PutMethod("https://api.alauda.cn/v1/services/zlzpadmin/exam"); 

			put.setRequestEntity(new StringRequestEntity(data,null,"UTF-8"));
			
			
//			post.setRequestBody(data);
			
			httpClient.executeMethod(put);
			
			String body = put.getResponseBodyAsString();
			System.out.println(body);
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
