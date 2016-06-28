package com.ywlqi.stock.forecast.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.zhaopin.common.util.DateUtil;

@Service
public class ImportOrgService extends BaseService {

	public void updateKeyOrg() throws SQLException, Exception{
		Date now = new Date();
		
		Date date = DateUtil.addMonth(DateUtil.parse(DateUtil.format(now, "yyyy-MM-01"),"yyyy-MM-dd"),-3);
		
		String sql = "insert into key_org(org_code) values(?)";
		
		List<Map<String,Object>> list = this.findImportOrg(date);
		
		if(CollectionUtils.isNotEmpty(list)){
			this.forecastDao.executeSQL("delete from key_org");
			
			int i=0 ,j=0;
			
			for(Map<String,Object> row : list){
				String orgCode = row.get("org_code").toString();
				
				this.forecastDao.executeSQL(sql, orgCode);
			}
			
			
		}
	}
	
	
	
	private List<Map<String,Object>> findImportOrg(Date startDate ) throws SQLException, Exception{
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("	select t2.org_code,                                          ");
		sql.append("       o.org_name,                                               ");
		sql.append("       sum(case                                                  ");
		sql.append("             when t2.day3 > STOCK_END then                       ");
		sql.append("              1                                                  ");
		sql.append("             else                                                ");
		sql.append("              0                                                  ");
		sql.append("           end) c1,                                              ");
		sql.append("       sum(1) c2,                                                ");
		sql.append("       sum(case                                                  ");
		sql.append("             when t2.day3 > STOCK_END then                       ");
		sql.append("              1                                                  ");
		sql.append("             else                                                ");
		sql.append("              0                                                  ");
		sql.append("           end) / sum(1) c                                       ");
		sql.append("  from (select t1.*,                                             ");
		sql.append("               (select stock_end                                 ");
		sql.append("                  from data_day                                  ");
		sql.append("                 where stock_code = t1.stock_code                ");
		sql.append("                   and stock_date > t1.stock_date                ");
		sql.append("                 order by stock_date limit 0, 1) day2,           ");
		sql.append("               (select stock_end                                 ");
		sql.append("                  from data_day                                  ");
		sql.append("                 where stock_code = t1.stock_code                ");
		sql.append("                   and stock_date > t1.stock_date                ");
		sql.append("                 order by stock_date limit 1, 1) day3,           ");
		sql.append("               (select stock_end                                 ");
		sql.append("                  from data_day                                  ");
		sql.append("                 where stock_code = t1.stock_code                ");
		sql.append("                   and stock_date > t1.stock_date                ");
		sql.append("                 order by stock_date limit 3, 1) day5            ");
		sql.append("                                                                 ");
		sql.append("          from (select d.STOCK_CODE,                             ");
		sql.append("                       d.STOCK_DATE,                             ");
		sql.append("                       d.ORG_CODE,                               ");
		sql.append("                       dd.STOCK_YESTERDAY_END,                   ");
		sql.append("                       dd.STOCK_END                              ");
		sql.append("                  from data_lhb_detail d, data_day dd            ");
		sql.append("                 where d.LHB_NET_AMOUNT > 0                      ");
		sql.append("                   and d.STOCK_CODE = dd.STOCK_CODE              ");
		sql.append("                   and d.STOCK_DATE = dd.STOCK_DATE              ");
		sql.append("                   and d.stock_date >= ? ) t1) t2,     ");
		sql.append("       organization o                                            ");
		sql.append(" where t2.org_code = o.org_code                                  ");
		sql.append(" group by t2.org_code                                            ");
		sql.append("having c1 > 5 and c >= 0.7                                       ");
		sql.append(" order by c desc, c1 desc                                        ");

		
		List<Map<String,Object>> list = this.forecastDao.findListBySQL(sql.toString(), startDate);
		
		return list;
		
		
	}
	
	
}
