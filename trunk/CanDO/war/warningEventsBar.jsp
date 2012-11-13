<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@page
	import="java.util.ArrayList,com.appspot.i_can_do.master.security.Event"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%
	ArrayList<Event> events = (ArrayList<Event>) request
			.getAttribute("events");
	if (events != null) {
%>

<style type="text/css">
</style>


<%
	SimpleDateFormat df = new SimpleDateFormat();
		for (Event event : events) {
			String string = df.format(event.getStart().getTime());
%>
<div class="singleEvent">
	<div class="event_name"><%=event.getName()%></div>
	<div class="event_description"><%=event.getDescription()%></div>
	<div class="start_time"><%=string%></div>
</div>
<%
	}
	}
%>