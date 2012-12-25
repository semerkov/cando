<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page
        import="java.text.SimpleDateFormat,java.util.Calendar,java.util.List,java.util.ArrayList, com.appspot.i_can_do.master.model.EventCalendar, com.appspot.i_can_do.master.model.Event, com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="java.util.Date" %>




<%
	final Calendar calendar = (Calendar) request.getAttribute("calendar");
	final List<Event> events = (List<Event>) request.getAttribute("events");
	final SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
	final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

  %>


<div style="overflow:auto; border-spacing:0px; width:100%; height:90%;">
<table class="ui-widget-content ui-corner-all"  border="1" cellspacing="0" cellpadding="0" style="font-size:14px; table-layout: fixed;">

 <tr>
    <td class="dayCalendarHeader" style="table-layout: fixed;">
    <%
    for(int i = 0;i<24;i++){
    %>
    <div class="hour_element">
        <div class="hour_first_part_element ui-widget-content"><%=i%>:00</div>
        <div class="ui-widget-content"><%=i%>:30</div> 
    </div>
    <%
    }
    %>
	</td>
	<td class="dayCalendarBody" style="table-layout: fixed;">
    <%
  	int height=100;
    int half =0;
        for(int i = 0;i<24;i++){
    %>
    <div class="halfHour ui-widget-content">&nbsp;
    <%
    for(Event e : events){
			if(e.getStart().getHours()==i&& e.getStart().getMinutes()>=0&& e.getStart().getMinutes()<30){
				half = ((e.getFinish().getMinutes()-e.getStart().getMinutes())>=30)?1:0;
				height = 100*((e.getFinish().getHours()-e.getStart().getHours())*2+half);
				if(height<100)height=100;
				%>
					<div class="eventOfDay" style="height:<%=height%>%;"><%=e.getName()%> <span class="eventTime"><%= formatter1.format(e.getStart()) %>-<%= formatter1.format(e.getFinish())%></span>
					<span class="event_id">
					<%=KeyFactory.keyToString(e.getKey())%></span></div>
					<%
					}
				}
				%>
    		<div class="start_hours" style="display:none;"><%=i%></div>
    		<div class="finish_hours" style="display:none;"><%=i%></div>
            <div class="start_minutes" style="display:none;">00</div>
            <div class="finish_minutes" style="display:none;">30</div>
    </div>
    <div class="halfHour ui-widget-content">&nbsp;
        <%
        for(Event e : events){
			if(e.getStart().getHours()==i&& e.getStart().getMinutes()>=30&& e.getStart().getMinutes()<60){
				half = ((e.getFinish().getMinutes()-e.getStart().getMinutes())>=30)?1:0;
				height = 100*((e.getFinish().getHours()-e.getStart().getHours())*2+half);
				if(height<100)height=100;
				%>
				<div class="eventOfDay" style="height:<%=height%>%;"><%=e.getName()%> <span class="eventTime"><%= formatter1.format(e.getStart()) %>-<%= formatter1.format(e.getFinish())%></span>
				 <span class="event_id">
				<%=KeyFactory.keyToString(e.getKey())%></span></div>
			<%
			}
		}
		%>
    		<div class="start_hours" style="display:none;"><%=i%></div>
    		<div class="finish_hours" style="display:none;"><%=(i+1)%></div>
            <div class="start_minutes" style="display:none;">30</div>
            <div class="finish_minutes" style="display:none;">00</div>
    </div>
    <%
        }
    %>
	</td>
	</tr>
</table>
</div>


<div id="currMonth" style="display: none;"><%=calendar.get(Calendar.MONTH)%></div>
<div id="currYear" style="display: none;"><%=calendar.get(Calendar.YEAR)%></div>
<div id="currDay" style="display: none;"><%=calendar.get(Calendar.DATE)%></div>