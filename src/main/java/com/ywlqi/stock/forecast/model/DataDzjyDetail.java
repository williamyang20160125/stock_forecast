package com.ywlqi.stock.forecast.model;

import java.util.Date;

public class DataDzjyDetail {
	private String stockCode;
	private Date stockDate;
	private String buyOrgName;
	private String sellOrgName;
	private Float dzjyPrice;
	private Float dzjyVolume;
	private Float dzjyAmount;
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public Date getStockDate() {
		return stockDate;
	}
	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	public String getBuyOrgName() {
		return buyOrgName;
	}
	public void setBuyOrgName(String buyOrgName) {
		this.buyOrgName = buyOrgName;
	}
	public String getSellOrgName() {
		return sellOrgName;
	}
	public void setSellOrgName(String sellOrgName) {
		this.sellOrgName = sellOrgName;
	}
	public Float getDzjyPrice() {
		return dzjyPrice;
	}
	public void setDzjyPrice(Float dzjyPrice) {
		this.dzjyPrice = dzjyPrice;
	}
	public Float getDzjyVolume() {
		return dzjyVolume;
	}
	public void setDzjyVolume(Float dzjyVolume) {
		this.dzjyVolume = dzjyVolume;
	}
	public Float getDzjyAmount() {
		return dzjyAmount;
	}
	public void setDzjyAmount(Float dzjyAmount) {
		this.dzjyAmount = dzjyAmount;
	}

	
}
