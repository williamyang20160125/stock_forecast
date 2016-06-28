package com.ywlqi.stock.forecast.model;

public class Stock {
	private String stockCode;
	private String stockName;
	private Float totalValue;
	private Float circulationValue;
	private Float peRatios;
	private Float pbRatios;

	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
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
	public String getFullStockCode(){
		String pre = stockCode.startsWith("6")?"sh":"sz";
		return pre+stockCode;
	}
}
