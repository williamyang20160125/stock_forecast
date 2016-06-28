package com.ywlqi.stock.forecast.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaopin.common.dao.SqlDao;
import com.zhaopin.common.util.HttpGet;

public abstract class BaseService {
	
	protected HttpGet httpGet = new HttpGet(10000);
	
	@Autowired
	protected SqlDao forecastDao;
	
}
