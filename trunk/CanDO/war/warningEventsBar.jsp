<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@page
	import="java.util.ArrayList,com.appspot.i_can_do.master.security.Event"%>

<%
	ArrayList<Event> events = (ArrayList<Event>) request
			.getAttribute("events");
	if (events != null) {
%>

<style type="text/css">
</style>



<%
	for (Event event : events) {
%>
<div>
	<span class="event_name"><%=event.getName()%></span> <span
		class="event_description"><%=event.getDescription()%></span> <span
		class="start_time"><%=event.getStart().toString()%></span>
</div>
<%
	}
	}
%>