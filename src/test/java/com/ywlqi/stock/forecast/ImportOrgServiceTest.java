package com.ywlqi.stock.forecast;

import java.sql.SQLException;

import org.junit.Test;

import com.ywlqi.stock.forecast.service.ImportOrgService;

public class ImportOrgServiceTest extends BaseTestCase{
	
	@Test
	public void test(){
		ImportOrgService importOrgService = (ImportOrgService)context.getBean("importOrgService");
		
		try {
			importOrgService.updateKeyOrg();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
