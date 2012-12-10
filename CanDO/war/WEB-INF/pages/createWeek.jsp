<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page
        import="java.text.SimpleDateFormat,java.util.Calendar,java.util.List,java.util.ArrayList, com.appspot.i_can_do.master.model.EventCalendar, com.appspot.i_can_do.master.model.Event, com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="java.util.Date" %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/calendar.css"/>
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/WeekCalendar.css"/>

<%
    final Calendar calendar = (Calendar) request.getAttribute("calendar");
    final List<EventCalendar> calendars = (List<EventCalendar>) request.getAttribute("calendars");
    final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    
    ArrayList<Event> events = new ArrayList<Event>();
    for (EventCalendar c : calendars) {
        for (Event e : c.getEvents())
            events.add(e);
    }
  %>


<div style=" width:100%; height:90%; border:1px solid  #09C;border-spacing:0px; overflow:auto; border-spacing:0px;
 font-size:13px;">
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
    <div class="ui-widget-content">&nbsp;</div>
    <div class="ui-widget-content">&nbsp;</div>
    <%
        }
    %>
	</div>
	<%
	}
	%>
</div>
</div>

</div>


<div id="currMonth" style="display: none;"><%=calendar.get(Calendar.MONTH)%></div>
<div id="currYear" style="display: none;"><%=calendar.get(Calendar.YEAR)%></div>
<div id="currDay" style="display: none;">1</div>