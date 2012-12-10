<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page
        import="java.text.SimpleDateFormat,java.util.Calendar,java.util.List,java.util.ArrayList, com.appspot.i_can_do.master.model.EventCalendar, com.appspot.i_can_do.master.model.Event, com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="java.util.Date" %>




<%
    final Calendar calendar = (Calendar) request.getAttribute("calendar");
    final List<EventCalendar> calendars = (List<EventCalendar>) request.getAttribute("calendars");
    final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
    ArrayList<Event> events = new ArrayList<Event>();
    for (EventCalendar c : calendars) {
        for (Event e : c.getEvents())
            events.add(e);
    }
  %>


<div style=" width:100%; height:90%; border:1px solid  #09C;border-spacing:0px; overflow:auto; border-spacing:0px;
 font-size:13px;">
    <div class="dayCalendarHeader" style="float:left; width:7%; text-align:center; text-align:center;">
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
	<div class="dayCalendarBody" style="width:92%; float:left;">
    <%
        for(int i = 0;i<24;i++){
    %>
    <div class="halfHour ui-widget-content">&nbsp;
    <%
    for(Event e : events){
					if(formatter.format(e.getStart()).equals(formatter.format(calendar.getTime()))){
						%>
						<span class="eventOfDayCalendar"><%=e.getName()%>; <div class="event_id">
						<%=KeyFactory.keyToString(e.getKey())%></div></span>
						<%
					}
				}
				%>
    		<div class="start_hours" style="display:none;"><%=i%></div>
    		<div class="finish_hours" style="display:none;"><%=i%></div>
            <div class="start_minutes" style="display:none;">0</div>
            <div class="finish_minutes" style="display:none;">30</div>
    </div>
    <div class="halfHour ui-widget-content">&nbsp;
        <%
    for(Event e : events){
					if(formatter.format(e.getStart()).equals(formatter.format(calendar.getTime()))){
						%>
						<span class="eventOfDayCalendar"><%=e.getName()%>; <div class="event_id">
						<%=KeyFactory.keyToString(e.getKey())%></div></span>
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
</div>
</div>
</div>

</div>


<div id="currMonth" style="display: none;"><%=calendar.get(Calendar.MONTH)%></div>
<div id="currYear" style="display: none;"><%=calendar.get(Calendar.YEAR)%></div>
<div id="currDay" style="display: none;">1</div>