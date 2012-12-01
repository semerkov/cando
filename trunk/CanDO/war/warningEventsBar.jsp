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
			String description = event.getDescription();
			boolean active = true;
			if("".equals(description)) active=false;
%>

	<div class="event_name <%=active?"active_accordion":"ui-accordion-header ui-state-default ui-corner-all"%>">
    <%=event.getName()%>
    	<div class="start_time"><%=string%></div>
	</div>
	
	<div class="event_description <%=active?"":"ui-helper-hidden"%>"><%=description%></div>

<%
		
	}
}
%>