package com.ywlqi.stock.forecast.model;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.Regex;

public class DataDay {
	private String stockCode;
	private Float stockStart;
	private Float stockEnd;
	private Float stockMin;
	private Float stockMax;
	private Float stockYesterdayEnd;
	private Date stockDate;
	private Float stockDealNum;
	private Float stockDealAmount;

	private Float totalValue;
	private Float circulationValue;
	private Float peRatios;
	private Float pbRatios;
	private Float turnoverRate;

	

	public static DataDay toBeanBySinaAPI(String sinaApiData,String stockCode) throws ParseException{
		String data = Regex.matchSRowSField(sinaApiData, "\"(.*?)\"", false);
		String[] dataArr = data.split(",");
		
		DataDay dataDay = new DataDay();
		
		dataDay.setStockCode(stockCode);
		dataDay.setStockDate(DateUtil.parse(dataArr[30],"yyyy-MM-dd"));
		dataDay.setStockEnd(Float.valueOf(dataArr[3]));
		dataDay.setStockMax(Float.valueOf(dataArr[4]));
		dataDay.setStockMin(Float.valueOf(dataArr[5]));
		dataDay.setStockStart(Float.valueOf(dataArr[1]));
		dataDay.setStockYesterdayEnd(Float.valueOf(dataArr[2]));
		
		return dataDay;
	}
	
	public static DataDay toBeanByQQAPI(String qqApiData,String stockCode) throws ParseException{
		String data = Regex.matchSRowSField(qqApiData, "\"(.*?)\"", false);
		
		String[] dataArr = data.split("~");
		
		DataDay dataDay = new DataDay();
		
		dataDay.setStockCode(stockCode);
		
		String time = dataArr[30];
		Date date = DateUtil.parse( DateUtil.format( DateUtil.parse(time,"yyyyMMddHHmmss"),"yyyyMMdd"),"yyyyMMdd");
		dataDay.setStockDate(date);
		dataDay.setStockEnd(Float.valueOf(dataArr[3]));
		dataDay.setStockMax(Float.valueOf(dataArr[33]));
		dataDay.setStockMin(Float.valueOf(dataArr[34]));
		dataDay.setStockStart(Float.valueOf(dataArr[5]));
		dataDay.setStockYesterdayEnd(Float.valueOf(dataArr[4]));
		dataDay.setStockDealNum(Float.valueOf(dataArr[36])*100);
		dataDay.setStockDealAmount(Float.valueOf(dataArr[37])*10000);
		
		dataDay.setCirculationValue(StringUtils.isBlank(dataArr[44])?null:Float.valueOf(dataArr[44]));
		dataDay.setTotalValue(StringUtils.isBlank(dataArr[45])?null:Float.valueOf(dataArr[45]));
		dataDay.setPbRatios(StringUtils.isBlank(dataArr[46])?null:Float.valueOf(dataArr[46]));
		dataDay.setPeRatios(StringUtils.isBlank(dataArr[39])?null:Float.valueOf(dataArr[39]));
		dataDay.setTurnoverRate(StringUtils.isBlank(dataArr[38])?null:Float.valueOf(dataArr[38]));
		
		
		return dataDay;
	}
	
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Float getStockStart() {
		return stockStart;
	}

	public void setStockStart(Float stockStart) {
		this.stockStart = stockStart;
	}

	public Float getStockEnd() {
		return stockEnd;
	}

	public void setStockEnd(Float stockEnd) {
		this.stockEnd = stockEnd;
	}

	public Float getStockMin() {
		return stockMin;
	}

	public void setStockMin(Float stockMin) {
		this.stockMin = stockMin;
	}

	public Float getStockMax() {
		return stockMax;
	}

	public void setStockMax(Float stockMax) {
		this.stockMax = stockMax;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public Float getStockDealNum() {
		return stockDealNum;
	}

	public void setStockDealNum(Float stockDealNum) {
		this.stockDealNum = stockDealNum;
	}

	public Float getStockDealAmount() {
		return stockDealAmount;
	}

	public void setStockDealAmount(Float stockDealAmount) {
		this.stockDealAmount = stockDealAmount;
	}

	public Float getStockYesterdayEnd() {
		return stockYesterdayEnd;
	}

	public void setStockYesterdayEnd(Float stockYesterdayEnd) {
		this.stockYesterdayEnd = stockYesterdayEnd;
	}

	public Float getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Float totalValue) {
		this.totalValue = totalValue;
	}

	public Float getCirculationValue() {
		return circulationValue;
	}

	public void setCirculationValue(Float circulationValue) {
		this.circulationValue = circulationValue;
	}

	public Float getPeRatios() {
		return peRatios;
	}

	public void setPeRatios(Float peRatios) {
		this.peRatios = peRatios;
	}

	public Float getPbRatios() {
		return pbRatios;
	}

	public void setPbRatios(Float pbRatios) {
		this.pbRatios = pbRatios;
	}
	
	public Float getTurnoverRate() {
		return turnoverRate;
	}

	public void setTurnoverRate(Float turnoverRate) {
		this.turnoverRate = turnoverRate;
	}

	public static void main(String[] args) throws ParseException {
		String time = "20151105150523";
		
		Date date = DateUtil.parse(time, "yyyyMMddHHmmss");
		
		System.out.println(date);
		
		
	}

}
