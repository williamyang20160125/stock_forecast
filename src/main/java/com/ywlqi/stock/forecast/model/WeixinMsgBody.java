package com.ywlqi.stock.forecast.model;

import com.alibaba.fastjson.JSONObject;

public class WeixinMsgBody {
	private String touser;
	private String toparty;
	private String totag;
	private String msgtype;
	private Integer agentid;
	private JSONObject text;
	private String safe;
	private JSONObject image;
	private JSONObject voice;
	private JSONObject video;
	private JSONObject news;
	private JSONObject mpnews;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public Integer getAgentid() {
		return agentid;
	}
	public void setAgentid(Integer agentid) {
		this.agentid = agentid;
	}
	public JSONObject getText() {
		return text;
	}
	public void setText(JSONObject text) {
		this.text = text;
	}
	public String getSafe() {
		return safe;
	}
	public void setSafe(String safe) {
		this.safe = safe;
	}
	public JSONObject getImage() {
		return image;
	}
	public void setImage(JSONObject image) {
		this.image = image;
	}
	public JSONObject getVoice() {
		return voice;
	}
	public void setVoice(JSONObject voice) {
		this.voice = voice;
	}
	public JSONObject getVideo() {
		return video;
	}
	public void setVideo(JSONObject video) {
		this.video = video;
	}
	public JSONObject getNews() {
		return news;
	}
	public void setNews(JSONObject news) {
		this.news = news;
	}
	public JSONObject getMpnews() {
		return mpnews;
	}
	public void setMpnews(JSONObject mpnews) {
		this.mpnews = mpnews;
	}
	
	
	
}
