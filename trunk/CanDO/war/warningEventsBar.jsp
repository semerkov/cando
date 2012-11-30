<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@page
	import="java.util.ArrayList,com.appspot.i_can_do.master.model.Event"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>


<%
	ArrayList<Event> events = (ArrayList<Event>) request
			.getAttribute("events");
	if (events != null) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		for (Event event : events) {
			String string = formatter.format(event.getStart().getTime());
			String description = "".equals(event.getDescription())?"not specified":event.getDescription();
%>

	<div class="event_name">
    <%=event.getName()%>
    	<div class="start_time"><%=string%></div>
	</div>
	
	<div class="event_description"><%=description%></div>

<%
		
	}
}
%>