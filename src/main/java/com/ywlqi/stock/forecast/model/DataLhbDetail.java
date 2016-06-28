package com.ywlqi.stock.forecast.model;

import java.util.Date;

public class DataLhbDetail {
	
	private String stockCode;
	private Date stockDate;
	private String orgCode;
	private Float lhbBuy;
	private Float lhbSell;
	private Float lhbBuyPer;
	private Float lhbSellPer;
	private Float lhbNetAmount;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public Float getLhbBuyPer() {
		return lhbBuyPer;
	}
	public void setLhbBuyPer(Float lhbBuyPer) {
		this.lhbBuyPer = lhbBuyPer;
	}
	public Float getLhbSellPer() {
		return lhbSellPer;
	}
	public void setLhbSellPer(Float lhbSellPer) {
		this.lhbSellPer = lhbSellPer;
	}
	public Float getLhbNetAmount() {
		return lhbNetAmount;
	}
	public void setLhbNetAmount(Float lhbNetAmount) {
		this.lhbNetAmount = lhbNetAmount;
	}
	public String getLhbReason() {
		return lhbReason;
	}
	public void setLhbReason(String lhbReason) {
		this.lhbReason = lhbReason;
	}

	public static String getgetLhbReasonBySina(String type){
		if(type.equals("01")){
			return "涨幅偏离值达7%的证券";
		}else if(type.equals("02")){
			return "跌幅偏离值达7%的证券";
		}else if(type.equals("03")){
			return "振幅值达15%的证券";
		}else if(type.equals("04")){
			return "换手率达20%的证券";
		}else if(type.equals("05")){
			return "连续三个交易日内，涨幅偏离值累计达20%的证券";
		}else if(type.equals("06")){
			return "连续三个交易日内，跌幅偏离值累计达20%的证券";
		}else if (type.equals("25")){
			return "连续三个交易日内，跌幅偏离值累计达到12%的ST证券、*ST证券和未完成股改证券";
		}else if (type.equals("09")){
			return "连续三个交易日内，日均换手率与前五个交易日的日均换手率的比值达到30倍，且换手率累计达20%的股票";
		}else if (type.equals("11")){
			return "无价格涨跌幅限制的证券";
		}
		
		return type;
	}

}
