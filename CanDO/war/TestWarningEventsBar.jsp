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
	/*display: none;*/
	width: 200px;
	height: 350px;
	background-color: #FFFFFF;
	color: #1A1A1A;
	position: fixed;
	padding: 0px;
	z-index: 3000;
	margin: 0px;
	border: 1px solid #2D2D2D;
	overflow: auto;
	}
	.single_event
	{
	margin: 0px;
	padding: 0px;
	border-top-width: 1px;
	border-top-color: #000;
	border-top-style: groove;
	}
	.event_name
	{
	background-color: #CCC;
	border-bottom-color: #000;
	font-family: "Comic Sans MS", cursive;
	font-size: 18px;
	font-weight: 500;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: dotted;
	}
	.event_description
	{
	font-family: Verdana, Geneva, sans-serif;
	font-size: 13px;
	padding-right: 10px;
	padding-top: 3px;
	padding-bottom: 10px;
	padding-left: 5px;
	}
	.start_time
	{
	font-family: "Comic Sans MS", cursive;
	font-size: 15px;
	text-align: right;
	}
</style>

<!-- JQuery Framework -->
<script src="js/jquery-1.8.2.js"></script>
<!-- CanDO Javascript -->
<script src="js/warningeventsbar.js"></script>
</head>
<body style="padding:0px!important;">
<div id="warning_events_bar">
	<div>Immediate events
    <img src="IMG/close_icon.png" style="float:right;"/></div>
	<div id="warning_events" >
        <div class="single_event">
            <div class="event_name">Саенко контрольная работа
           		<div class="start_time">20.10.2012</div>
          </div>
            <div class="event_description">это ёпт Саенко</div>
        </div>
    </div>
</div>
</body>
</html>