<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
	#warning_events_bar
	{
	display: none;
	width: 200px;
	height: 300px;
	background-color: #DFDFDF;
	color: #1A1A1A;
	position: fixed;
	padding: 6px;
	z-index: 3000;
	margin: 2px;
	border: 1px solid #2D2D2D;
	overflow: auto;
	}
	.single_event
	{
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 1px;
	border-left-width: 0px;
	border-top-color: #333;
	border-right-color: #333;
	border-bottom-color: #333;
	border-left-color: #333;
	margin: 1px;
	padding: 1px;
	}
	.event_name
	{
	
	}
	.event_description
	{
		
	}
	.start_time
	{
	
	}
</style>

<!-- JQuery Framework -->
<script src="js/jquery-1.8.2.js"></script>
<!-- CanDO Javascript -->
<script src="js/warningeventsbar.js"></script>
</head>
<body>
<div id="warning_events_bar">
	<div id="warning_events" >
        <div class="single_event">
            <div class="event_name">dsfgsdfg</div>
            <div class="event_description">sdfg</div>
            <div class="start_time">20.10.2012</div>
        </div>
    </div>
</div>
</body>
</html>