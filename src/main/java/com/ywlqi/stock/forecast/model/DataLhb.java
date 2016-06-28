package com.ywlqi.stock.forecast.model;

import java.util.Date;

public class DataLhb {
	private String stockCode;
	private Date stockDate;
	private Float lhbBuy;
	private Float lhbSell;
	private String lhbReason;
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
	public Float getLhbBuy() {
		return lhbBuy;
	}
	public void setLhbBuy(Float lhbBuy) {
		this.lhbBuy = lhbBuy;
	}
	public Float getLhbSell() {
		return lhbSell;
	}
	public void setLhbSell(Float lhbSell) {
		this.lhbSell = lhbSell;
	}
	public String getLhbReason() {
		return lhbReason;
	}
	public void setLhbReason(String lhbReason) {
		this.lhbReason = lhbReason;
	}

}
