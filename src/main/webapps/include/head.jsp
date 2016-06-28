<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- 为了让 IE 浏览器运行最新的渲染模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 可以让部分国产浏览器默认采用高速模式渲染页面 -->
    <meta name="renderer" content="webkit">
    
    <link href="<%=request.getContextPath() %>/css/vendor/bootstrap.min.css" rel="stylesheet">
    
    <link href="<%=request.getContextPath() %>/css/flat-ui.min.css" rel="stylesheet">
    <!-- 浏览器兼容 -->
    <link href="<%=request.getContextPath() %>/css/compatible.css" rel="stylesheet">

    <link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico">
    <!--[if lt IE 9]>
      <script src="<%=request.getContextPath() %>/js/vendor/html5shiv.js"></script>
      <script src="<%=request.getContextPath() %>/js/vendor/respond.min.js"></script>
    <![endif]-->
    <style>
      body {
          padding-bottom: 20px;
          font-family: "Arial","Microsoft YaHei","宋体",sans-serif;
      }
      
     
      
      .container-fluid{
      	padding-right:0px;
      	padding-left:0px;
      }
    </style>
    
    <script src="<%=request.getContextPath() %>/js/vendor/jquery.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/flat-ui.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/application.js"></script>
    <script src="<%=request.getContextPath() %>/js/compatible.js"></script>
    <script src="<%=request.getContextPath() %>/js/iscroll4/iscroll.js"></script>
    <script src="<%=request.getContextPath() %>/js/page.js"></script>
    <script src="<%=request.getContextPath() %>/js/orientation.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.validate.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jq-validate-ext.js"></script>
	<script src="<%=request.getContextPath()%>/js/messages_zh.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.formFill.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootbox.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/monitor-drag.js"></script>
	<script src="<%=request.getContextPath()%>/js/stickUp.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/monitor-orientation-change.js"></script>
	
	<!-- for portal -->
	<script src="<%=request.getContextPath()%>/js/Chart.min.js"></script>
	
	<script >
	var orientationAction;
	</script>
	