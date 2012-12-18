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

Date d_start = new Date (calendar.getTimeInMillis());
  %>


<div style=" width:100%; height:90%; border:1px solid  #09C;border-spacing:0px; overflow:auto; border-spacing:0px;
 font-size:11px;">
    <div class="dayCalendarHeader" style="float:left; width:6.5%; text-align:center;">
    <%
    for(int i = 0;i<24;i++){
    %>
    <div class="hour_element">
        <div class="hour_first_part_element ui-widget-content"><%=i%>:00</div>
        <div><%=i%>:30</div> 
    </div>
    <%
    }
    %>
	</div>
  	<%
	for(int w=0;w<7;w++){
	%>
	<div class="dayCalendarBody" style="float:left; width:13.08%">
    <%
        for(int i = 0;i<24;i++){
    %>
    <div class="halfHour ui-widget-content">&nbsp;
      <%
    for(Event e : events){
			if(e.getStart().getDate()==d_start.getDate() && e.getStart().getHours()==i&& e.getStart().getMinutes()>=0&& e.getStart().getMinutes()<30){
				%>
					<span class="eventOfDay"><%=e.getName()%> (<span class="eventTime"><%= formatter1.format(e.getStart()) %></span>); <span class="event_id">
					<%=KeyFactory.keyToString(e.getKey())%></span></span>
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
			if(e.getStart().getDate()==d_start.getDate() && e.getStart().getHours()==i&& e.getStart().getMinutes()>=30&& e.getStart().getMinutes()<60){
				%>
					<span class="eventOfDay"><%=e.getName()%> (<span class="eventTime"><%= formatter1.format(e.getStart()) %></span>); <span class="event_id">
				<%=KeyFactory.keyToString(e.getKey())%></span></span>
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
   	<div class="dayOfWeek" style="display:none;">i</div>
	</div>
	<%
	d_start.setDate(d_start.getDate()+1);
	}
	%>
</div>




<div id="currMonth" style="display: none;"><%=calendar.get(Calendar.MONTH)%></div>
<div id="currYear" style="display: none;"><%=calendar.get(Calendar.YEAR)%></div>
<div id="currDay" style="display: none;"><%=calendar.get(Calendar.DATE)%></div>