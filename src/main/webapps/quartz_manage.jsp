<%@page import="com.zhaopin.common.util.DateUtil"%>
<%@page import="com.zhaopin.common.util.BeanUtils"%>
<%@page import="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean"%>
<%@page import="org.quartz.core.QuartzScheduler"%>
<%@page import="org.quartz.JobDetail"%>
<%@page import="org.quartz.Trigger"%>
<%@page import="java.util.Date"%>
<%@page import="org.quartz.Scheduler"%>
<%@page import="org.quartz.impl.StdScheduler"%>

<%@page import="org.springframework.scheduling.quartz.SchedulerFactoryBean"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<head>
<title></title>
<% 
StdScheduler scheduler = (StdScheduler)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean(StdScheduler.class); 
%>
<script type="text/javascript">
function resumeTrigger(triggerName,groupName){
	jQuery.ajax({
		type:"post",
		url:web_root+"/interface/quartz_manage.jsp",
		data:{'triggerName':triggerName,'groupName':groupName},
		dataType:'json',
	    success: function(data){
			alert("执行成功");
	    }
	});
}

function restartScheduler(flag){
	jQuery.ajax({
		type:"post",
		url:web_root+"/interface/quartz_manage.jsp",
		data:{'restartScheduler':flag},
		dataType:'json',
	    success: function(data){
			alert("执行成功");
	    }
	});
}

</script>

</head>
<body>
<div>
<a href="javascript:restartScheduler(1)">stop scheduler</a>&nbsp;<a href="javascript:restartScheduler(2)">start scheduler</a>
</div>
<div>

</div>
<div>
<table>
<tr><td>任务名称</td><td>下次执行时间</td><td>状态</td><td>操作</td></tr>
<%


String restartScheduler=request.getParameter("restartScheduler");
String _triggerName = request.getParameter("triggerName");
String _groupName = request.getParameter("groupName");
if(restartScheduler!=null && restartScheduler.equals("1")){
	SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean(SchedulerFactoryBean.class);
	schedulerFactory.destroy();
}if(restartScheduler!=null && restartScheduler.equals("2")){
	SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean)WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean(SchedulerFactoryBean.class);
	
	QuartzScheduler qScheduler = (QuartzScheduler)BeanUtils.forceGetProperty(scheduler, "sched");
	BeanUtils.forceSetProperty(qScheduler, "closed", false);
	BeanUtils.forceSetProperty(qScheduler, "shuttingDown", false);
	schedulerFactory.afterPropertiesSet();
	scheduler.resumeAll();
	
	//schedulerFactory.start();

}else if(_triggerName!=null && _groupName!=null){
	System.out.println("_triggerName:"+_triggerName+",_groupName:"+_groupName);
	//scheduler.deleteJob(scheduler.getTrigger(_triggerName, _groupName).getJobName(), scheduler.getTrigger(_triggerName, _groupName).getJobGroup());
	//scheduler.ad
	//scheduler.triggerJob(scheduler.getTrigger(_triggerName, _groupName).getJobName(), scheduler.getTrigger(_triggerName, _groupName).getJobGroup());
	
	String jobName = scheduler.getTrigger(_triggerName, _groupName).getJobName();
	
	JobDetail job = (JobDetail) WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean(jobName);
	
	MethodInvokingJobDetailFactoryBean factory = (MethodInvokingJobDetailFactoryBean)job.getJobDataMap().get("methodInvoker");
	
	factory.invoke();
	
}else{
	try {
		if(scheduler!=null){
			String[] triggerGroupNames = scheduler.getTriggerGroupNames();
			for(String groupName:triggerGroupNames){
				String[] triggerNames = scheduler.getTriggerNames(groupName);
				
				for(String triggerName:triggerNames){
					Trigger trigger = scheduler.getTrigger(triggerName, groupName);
					JobDetail job = scheduler.getJobDetail(trigger.getJobName(), trigger.getJobGroup());
					
					
					
					Date nextFireTime = trigger.getNextFireTime();
					out.println("<tr><td>"+triggerName+"</td><td>"+DateUtil.format(nextFireTime, "yyyy-MM-dd HH:mm:ss")+"</td><td>"+job.isDurable()+","+job.isStateful()+","+job.isVolatile()+"</td><td><a href='javascript:resumeTrigger(\""+triggerName+"\",\""+groupName+"\")'>重新执行</a></td></tr>");
					
					
				}
			}
			
		}else{
			out.println("scheduler is null");
		}
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}



%>
</table>
</div>
</body>
</html>