package com.ywlqi.stock.forecast.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.ywlqi.stock.forecast.model.Stock;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.lucene.solr.IndexManager;

@Service
public class SolrService extends BaseService{
	private IndexManager indexManager;
	private String stockSql = null;
	
	private String stockClassSql = null;
	
	public SolrService(){
		initSql();
		
		try {
			this.indexManager = new IndexManager("stock");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void initSql(){
		StringBuffer _stockSql = new StringBuffer();
		_stockSql.append(" select s.*,                                                                                                                                  ");
		_stockSql.append("  d.stock_end,                                                                                                                                ");
		_stockSql.append("  d.stock_start,                                                                                                                                ");
		_stockSql.append("  d.stock_date,                                                                                                                                ");
		_stockSql.append("  ((d.stock_end-d.STOCK_YESTERDAY_END)/d.STOCK_YESTERDAY_END * 100) gain_percent,                                                             ");
		_stockSql.append(" (select case count(*) when 5 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,5) t) avg5, ");
		_stockSql.append(" (select case count(*) when 10 then avg(stock_end) else null end avg10 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,10) t) avg10, ");
		_stockSql.append(" (select case count(*) when 20 then avg(stock_end) else null end avg20 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,20) t) avg20, ");
		_stockSql.append(" (select case count(*) when 30 then avg(stock_end) else null end avg30 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,30) t) avg30, ");
		_stockSql.append(" (select case count(*) when 60 then avg(stock_end) else null end avg60 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,60) t) avg60, ");
		_stockSql.append(" (select case count(*) when 120 then avg(stock_end) else null end avg120 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,120) t) avg120, ");
		_stockSql.append(" (select case count(*) when 200 then avg(stock_end) else null end  avg200 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,200) t) avg200, ");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? ) lhb_amount,                          ");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? and ORG_CODE='80032107') org_80032107,");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? and ORG_CODE='80190854') org_80190854,");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? and ORG_CODE='80363134') org_80363134 ");
		_stockSql.append(" from stock s, data_day d where s.stock_code=?                                                                                         ");
		_stockSql.append("  and s.stock_code = d.stock_code                                                                                                             ");
		_stockSql.append("  and d.stock_date=? ");
		
		this.stockSql = _stockSql.toString();
		
		this.stockClassSql = "select * from rel_stock_class where stock_code = ?";
		
	}

	private static final Logger logger = Logger.getLogger(SolrService.class);
	public void saveStockToSolr() throws ParseException{
		Date date = DateUtil.parse( DateUtil.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
//		date = DateUtil.addDay(date, -1);
		saveStockToSolr(date);
	}
	
	public void saveStockToSolr(Date date) throws ParseException{
		String sql ="select * from stock";
		
		try {
			List<Stock> stockList = this.forecastDao.findListBySQL(sql, Stock.class);
			
			List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
			
			for(Stock stock:stockList){
				SolrInputDocument doc = this.createSolrInputDocument(stock.getStockCode(), date);
				
				if(doc!=null){
					docList.add(doc);
				}
			}
			
			if(CollectionUtils.isNotEmpty(docList)){
				for(int i=0;i<docList.size();){
					int end = i+200>docList.size()?docList.size():i+200;
					List<SolrInputDocument> subDocList = docList.subList(i, end);
					this.indexManager.createOrUpdateIndex(subDocList);
					i=end;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
	} 
	
	private SolrInputDocument createSolrInputDocument(String stockCode,Date date){
		SolrInputDocument doc = null;
		
		try {
			List<Map<String, Object>> list = this.forecastDao.findListBySQL(
					stockSql.toString(), stockCode, stockCode, stockCode, stockCode,
					stockCode, stockCode, stockCode, stockCode, date, stockCode, date,
					stockCode, date, stockCode, date, stockCode, date);
			
			
			if(CollectionUtils.isNotEmpty(list)){
				Map<String, Object> row = list.get(0);
				
				if(row.get("stock_end") != null && ((Number)row.get("stock_end")).floatValue()>0 && row.get("stock_start") != null && ((Number)row.get("stock_start")).floatValue()>0){
					
					doc = new SolrInputDocument();
					doc.addField("stock_code", row.get("stock_code"));
					doc.addField("stock_date", row.get("stock_date"));
					doc.addField("stock_name", row.get("stock_name"));
					doc.addField("stock_start", row.get("stock_start"));
					doc.addField("stock_end", row.get("stock_end"));
					doc.addField("total_value", row.get("total_value"));
					doc.addField("circulation_value", row.get("circulation_value"));
					doc.addField("pb_ratios", row.get("pb_ratios"));
					doc.addField("pe_ratios", row.get("pe_ratios"));
					doc.addField("avg5", row.get("avg5"));
					doc.addField("avg10", row.get("avg10"));
					doc.addField("avg20", row.get("avg20"));
					doc.addField("avg30", row.get("avg30"));
					doc.addField("avg60", row.get("avg60"));
					doc.addField("avg120", row.get("avg120"));
					doc.addField("avg200", row.get("avg200"));
	
					doc.addField("avg5_ratios", row.get("avg5")!=null && row.get("stock_end")!=null && ((Number)row.get("stock_end")).floatValue()>0?(((Number)row.get("stock_end")).floatValue()/((Number)row.get("avg5")).floatValue()):null);
					doc.addField("avg10_ratios", row.get("avg10")!=null && row.get("stock_end")!=null && ((Number)row.get("stock_end")).floatValue()>0?(((Number)row.get("stock_end")).floatValue()/((Number)row.get("avg10")).floatValue()):null);
					doc.addField("avg20_ratios", row.get("avg20")!=null && row.get("stock_end")!=null && ((Number)row.get("stock_end")).floatValue()>0?(((Number)row.get("stock_end")).floatValue()/((Number)row.get("avg20")).floatValue()):null);
					doc.addField("avg30_ratios", row.get("avg30")!=null && row.get("stock_end")!=null && ((Number)row.get("stock_end")).floatValue()>0?(((Number)row.get("stock_end")).floatValue()/((Number)row.get("avg30")).floatValue()):null);
					doc.addField("avg60_ratios", row.get("avg60")!=null && row.get("stock_end")!=null && ((Number)row.get("stock_end")).floatValue()>0?(((Number)row.get("stock_end")).floatValue()/((Number)row.get("avg60")).floatValue()):null);
					doc.addField("avg120_ratios", row.get("avg120")!=null && row.get("stock_end")!=null && ((Number)row.get("stock_end")).floatValue()>0?(((Number)row.get("stock_end")).floatValue()/((Number)row.get("avg120")).floatValue()):null);
					doc.addField("avg200_ratios", row.get("avg200")!=null && row.get("stock_end")!=null && ((Number)row.get("stock_end")).floatValue()>0?(((Number)row.get("stock_end")).floatValue()/((Number)row.get("avg200")).floatValue()):null);
					
					
					doc.addField("lhb_amount", row.get("lhb_amount"));
					doc.addField("org_80032107", row.get("org_80032107"));
					doc.addField("org_80190854", row.get("org_80190854"));
					doc.addField("org_80363134", row.get("org_80363134"));
					
					List<Map<String, Object>> classList = this.forecastDao.findListBySQL(stockClassSql, stockCode);
					
					if(CollectionUtils.isNotEmpty(classList)){
						for(Map<String, Object> stockClass:classList){
							doc.addField("stock_class", stockClass.get("stock_class_name"));
						}
					}
				}
			}
			
		} catch (SQLException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return doc;
	}
	
	public static void main(String[] args) {
		StringBuffer _stockSql = new StringBuffer();
		_stockSql.append(" select s.*,                                                                                                                                  ");
		_stockSql.append("  d.stock_end,                                                                                                                                ");
		_stockSql.append("  d.stock_start,                                                                                                                                ");
		_stockSql.append("  d.stock_date,                                                                                                                                ");
		_stockSql.append("  ((d.stock_end-d.STOCK_YESTERDAY_END)/d.STOCK_YESTERDAY_END * 100) gain_percent,                                                             ");
		_stockSql.append(" (select case count(*) when 5 then avg(stock_end) else null end avg5 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,5) t) avg5, ");
		_stockSql.append(" (select case count(*) when 10 then avg(stock_end) else null end avg10 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,10) t) avg10, ");
		_stockSql.append(" (select case count(*) when 20 then avg(stock_end) else null end avg20 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,20) t) avg20, ");
		_stockSql.append(" (select case count(*) when 30 then avg(stock_end) else null end avg30 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,30) t) avg30, ");
		_stockSql.append(" (select case count(*) when 60 then avg(stock_end) else null end avg60 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,60) t) avg60, ");
		_stockSql.append(" (select case count(*) when 120 then avg(stock_end) else null end avg120 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,120) t) avg120, ");
		_stockSql.append(" (select case count(*) when 200 then avg(stock_end) else null end  avg200 from (select stock_end from data_day where stock_code=? order by stock_date desc limit 0,200) t) avg200, ");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? ) lhb_amount,                          ");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? and ORG_CODE='80032107') org_80032107,");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? and ORG_CODE='80190854') org_80190854,");
		_stockSql.append(" (select sum(LHB_NET_AMOUNT) from data_lhb_detail where STOCK_CODE=? and STOCK_DATE=? and ORG_CODE='80363134') org_80363134 ");
		_stockSql.append(" from stock s, data_day d where s.stock_code=?                                                                                         ");
		_stockSql.append("  and s.stock_code = d.stock_code                                                                                                             ");
		_stockSql.append("  and d.stock_date=? ");
		
		System.out.println(_stockSql.toString());
	}
}
