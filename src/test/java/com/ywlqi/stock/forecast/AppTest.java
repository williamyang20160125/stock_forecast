package com.ywlqi.stock.forecast;

import java.util.Date;

import com.zhaopin.common.util.DateUtil;
import com.zhaopin.common.util.HttpGet;
import com.zhaopin.common.util.ThreadPool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) throws Exception {
//		ThreadPool.putTask(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					Thread.sleep(10000);
//					System.out.println("thread 1!!!!!!!!!!!!!!!");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		System.out.println("================================");
//		
//		System.out.println(ThreadPool.getInstance().isTerminated());
//		
//		Thread.sleep(30 * 1000);
//		System.out.println("main end");
//    	
//    	Date startDate = DateUtil.parse("2015-10-22","yyyy-MM-dd");
//		Date endDate = DateUtil.parse("2015-01-05","yyyy-MM-dd");
//		
//		long days = (startDate.getTime() - endDate.getTime())/1000/3600/24;
//		
//		System.out.println(days);
	}
}
