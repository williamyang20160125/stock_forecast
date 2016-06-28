package com.ywlqi.stock.forecast;

import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import com.ywlqi.stock.forecast.service.SolrService;
import com.zhaopin.common.util.DateUtil;
import com.zhaopin.lucene.solr.SearchManager;

public class SolrServiceTest extends BaseTestCase{

	@Test
	public void testInitSolr(){
		SolrService solrService = (SolrService)context.getBean("solrService");
		
//		stockService.initStock();
		try {
			Date date = DateUtil.parse("2015-12-18", "yyyy-MM-dd");
			solrService.saveStockToSolr(date);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearch(){
		String condition = "total_value:[0 TO 120] AND pe_ratios:[0 TO 120] AND avg20_ratios:[0.8 TO 1.1] AND pb_ratios:[0 TO 6]";
		SolrQuery query = new SolrQuery(condition);
		query.setStart(0); 
        query.setRows(1000); 
        query.setIncludeScore(true);
        
        SearchManager searchManager = new SearchManager("stock");
        
        try {
			SolrDocumentList documentList = searchManager.query(query);
			
			if(CollectionUtils.isNotEmpty(documentList)){
				for(int i=0;i<documentList.size();i++){
					SolrDocument doc = documentList.get(i);
					
					System.out.println(doc.get("stock_code"));
				}
			}
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
