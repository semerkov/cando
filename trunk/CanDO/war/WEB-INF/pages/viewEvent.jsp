<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ page import="java.util.Date,com.appspot.i_can_do.master.model.Event,java.text.SimpleDateFormat, com.google.appengine.api.datastore.KeyFactory" %>
<% 
	final Event event = (Event) request.getAttribute("event");
	final String calendarName = (String)request.getAttribute("calendarName");
	final SimpleDateFormat formatter = (SimpleDateFormat) request.getAttribute("formatter");
	final Date warningTime = event.getWarningTime();
%>
<table style="width: 100%;">
	<tr>
		<td><label>Name of event:</label></td>
		<td><%=event.getName() %></td>
	</tr>
	<tr>
		<td><label>Description:</label></td>
		<td><%=event.getDescription() %></td>
	</tr>
	<tr>
		<td><label>Start time:</label></td>
		<td><%=formatter.format(event.getStart()) %></td>

	</tr>
	<tr>
		<td><label>Finish time:</label></td>
		<td><%=formatter.format(event.getFinish()) %></td>
	</tr>
	<tr>
		<td><label>Event calendar:</label></td>
		<td><%=calendarName %></td>
	</tr>
</table>
<div style="text-align: center; width: 100%">Notification time</div>
<div style="text-align: center;">
	<label>Days: </label><%=warningTime.getDate()-1%> <label>Hours: </label><%=warningTime.getHours() %> <label>Minutes:
	</label><%=warningTime.getMinutes() %>
</div>
<div id="selectedViewEventKey" style="display:none"><%=KeyFactory.keyToString(event.getKey())%></div>