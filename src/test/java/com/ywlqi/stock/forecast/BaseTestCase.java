package com.ywlqi.stock.forecast;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTestCase extends TestCase{
	
	protected ApplicationContext context;
	protected void setUp() {
		try{
		context = new ClassPathXmlApplicationContext( "classpath:spring-bean-all.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(context);
	}
}
