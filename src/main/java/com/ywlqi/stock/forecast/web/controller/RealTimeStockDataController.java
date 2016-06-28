package com.ywlqi.stock.forecast.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ywlqi.stock.forecast.service.QQStockService;
import com.zhaopin.common.util.DateUtil;

@Controller
@RequestMapping("/realtime")
public class RealTimeStockDataController {
	
	@Autowired
	public QQStockService qqStockService;

	@RequestMapping( value = "/stockdata",produces="application/xml;charset=UTF-8")
	@ResponseBody
	public String getRealTimeStockData(HttpServletRequest request) throws Exception{
		String fullStockCode = request.getParameter("stockCode");
		
		String data = qqStockService.getRealTimeStockDataFromNet(fullStockCode);
		
		String[] dataArr = data.split("~");
		
		String time = dataArr[30];
		String date = DateUtil.format( DateUtil.parse(time,"yyyyMMddHHmmss"),"yyyy-MM-dd");
		
		Document doc = DocumentFactory.getInstance().createDocument();
		doc.addElement("ROOT");
		doc.getRootElement().addElement("date").addText(date);
		doc.getRootElement().addElement("stockCode").addText(dataArr[2]);
		doc.getRootElement().addElement("stockName").addText(dataArr[1]);
		doc.getRootElement().addElement("new").addText(dataArr[3]);
		doc.getRootElement().addElement("start").addText(dataArr[5]);
		doc.getRootElement().addElement("yesterday").addText(dataArr[4]);
		doc.getRootElement().addElement("total").addText(dataArr[45]);
		doc.getRootElement().addElement("pe").addText(dataArr[39]);
		doc.getRootElement().addElement("pb").addText(dataArr[46]);
		doc.getRootElement().addElement("max").addText(dataArr[33]);
		doc.getRootElement().addElement("min").addText(dataArr[34]);
		doc.getRootElement().addElement("radio").addText(dataArr[32]);
		
		return doc.asXML();
	}
	
	public static void main(String[] args) {
		Document doc = DocumentFactory.getInstance().createDocument();
		doc.addElement("ROOT");
		doc.getRootElement().addElement("date").addText("2015-10-22");
		
		System.out.println(doc.asXML());
	}
}
