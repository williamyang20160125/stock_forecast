package com.ywlqi.stock.forecast.model;

import java.util.Date;

public class StockTags extends DataDay{
		
	private Boolean threeDayUpDealNum;//连续三天成交量递增
	private Boolean threeDayUpDealAmount;//连续三天成交额递增
	private Float avg5Ratios;//收盘价与5天均价比值
	private Float avg10Ratios;//收盘价与10天均价比值
	private Float avg20Ratios;//收盘价与20天均价比值
	private Float avg30Ratios;//收盘价与30天均价比值
	private Float avg60Ratios;//收盘价与60天均价比值
	private Float avg120Ratios;//收盘价与120天均价比值
	private Float avg200Ratios;//收盘价与200天均价比值
	
	private Float avg5;//收盘价与5天均价
	private Float avg10;//收盘价与10天均价
	private Float avg20;//收盘价与20天均价
	private Float avg30;//收盘价与30天均价
	private Float avg60;//收盘价与60天均价
	private Float avg120;//收盘价与120天均价
	private Float avg200;//收盘价与200天均价


	private Boolean threeDayUpStockEnd;//连续三天上涨
	private Boolean threeDayRed;//连续三天收阳
	private Boolean twoDayLimitUp;//连续两天涨停
	private Boolean threeDayLimitDown;//连续三天跌停
	private Float dealNumPercent;//总成交量与前一天总成交量增幅百分比
	private Float dealAmountPercent;//总成交金额与前一天总成交金额增幅百分比
	private Float jumpPercent;//收盘价与前一天收盘价涨幅百分比
	private Float amplitudePercent;//收盘价与开盘价涨幅百分比
	public Boolean getThreeDayUpDealNum() {
		return threeDayUpDealNum;
	}
	public void setThreeDayUpDealNum(Boolean threeDayUpDealNum) {
		this.threeDayUpDealNum = threeDayUpDealNum;
	}
	public Boolean getThreeDayUpDealAmount() {
		return threeDayUpDealAmount;
	}
	public void setThreeDayUpDealAmount(Boolean threeDayUpDealAmount) {
		this.threeDayUpDealAmount = threeDayUpDealAmount;
	}
	public Float getAvg5Ratios() {
		return avg5Ratios;
	}
	public void setAvg5Ratios(Float avg5Ratios) {
		this.avg5Ratios = avg5Ratios;
	}
	public Float getAvg10Ratios() {
		return avg10Ratios;
	}
	public void setAvg10Ratios(Float avg10Ratios) {
		this.avg10Ratios = avg10Ratios;
	}
	public Float getAvg20Ratios() {
		return avg20Ratios;
	}
	public void setAvg20Ratios(Float avg20Ratios) {
		this.avg20Ratios = avg20Ratios;
	}
	public Float getAvg30Ratios() {
		return avg30Ratios;
	}
	public void setAvg30Ratios(Float avg30Ratios) {
		this.avg30Ratios = avg30Ratios;
	}
	public Float getAvg60Ratios() {
		return avg60Ratios;
	}
	public void setAvg60Ratios(Float avg60Ratios) {
		this.avg60Ratios = avg60Ratios;
	}
	public Float getAvg120Ratios() {
		return avg120Ratios;
	}
	public void setAvg120Ratios(Float avg120Ratios) {
		this.avg120Ratios = avg120Ratios;
	}
	public Float getAvg200Ratios() {
		return avg200Ratios;
	}
	public void setAvg200Ratios(Float avg200Ratios) {
		this.avg200Ratios = avg200Ratios;
	}
	public Boolean getThreeDayUpStockEnd() {
		return threeDayUpStockEnd;
	}
	public void setThreeDayUpStockEnd(Boolean threeDayUpStockEnd) {
		this.threeDayUpStockEnd = threeDayUpStockEnd;
	}
	public Boolean getThreeDayRed() {
		return threeDayRed;
	}
	public void setThreeDayRed(Boolean threeDayRed) {
		this.threeDayRed = threeDayRed;
	}
	public Boolean getTwoDayLimitUp() {
		return twoDayLimitUp;
	}
	public void setTwoDayLimitUp(Boolean twoDayLimitUp) {
		this.twoDayLimitUp = twoDayLimitUp;
	}
	public Boolean getThreeDayLimitDown() {
		return threeDayLimitDown;
	}
	public void setThreeDayLimitDown(Boolean threeDayLimitDown) {
		this.threeDayLimitDown = threeDayLimitDown;
	}
	public Float getDealNumPercent() {
		return dealNumPercent;
	}
	public void setDealNumPercent(Float dealNumPercent) {
		this.dealNumPercent = dealNumPercent;
	}
	public Float getDealAmountPercent() {
		return dealAmountPercent;
	}
	public void setDealAmountPercent(Float dealAmountPercent) {
		this.dealAmountPercent = dealAmountPercent;
	}
	public Float getJumpPercent() {
		return jumpPercent;
	}
	public void setJumpPercent(Float jumpPercent) {
		this.jumpPercent = jumpPercent;
	}
	public Float getAmplitudePercent() {
		return amplitudePercent;
	}
	public void setAmplitudePercent(Float amplitudePercent) {
		this.amplitudePercent = amplitudePercent;
	}
	public Float getAvg5() {
		return avg5;
	}
	public void setAvg5(Float avg5) {
		this.avg5 = avg5;
	}
	public Float getAvg10() {
		return avg10;
	}
	public void setAvg10(Float avg10) {
		this.avg10 = avg10;
	}
	public Float getAvg20() {
		return avg20;
	}
	public void setAvg20(Float avg20) {
		this.avg20 = avg20;
	}
	public Float getAvg30() {
		return avg30;
	}
	public void setAvg30(Float avg30) {
		this.avg30 = avg30;
	}
	public Float getAvg60() {
		return avg60;
	}
	public void setAvg60(Float avg60) {
		this.avg60 = avg60;
	}
	public Float getAvg120() {
		return avg120;
	}
	public void setAvg120(Float avg120) {
		this.avg120 = avg120;
	}
	public Float getAvg200() {
		return avg200;
	}
	public void setAvg200(Float avg200) {
		this.avg200 = avg200;
	}
	
	
	
}
