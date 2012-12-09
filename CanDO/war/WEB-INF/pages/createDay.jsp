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

<table width="100%" border="1" cellspacing="" cellpadding="0"
       rules="all">
<tbody>
<tr>
    <td>
    <%
    for(int i = 0;i<24;i++){
    %>
    <div>
        <div><%=i%>:00</div>
        <div><%=i%>:30</div>
    </div>
    <%
    }
    %>
	</td>
	<td>
    <%
        for(int i = 0;i<24;i++){
    %>
    <div class="ui-widget-content"></div>
    <div class="ui-widget-content"></div>
    <%
        }
    %>
</td>
</tr>
</tbody>

</table>
