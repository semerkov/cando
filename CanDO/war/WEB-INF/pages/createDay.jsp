<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ page
        import="java.text.SimpleDateFormat,java.util.Calendar,java.util.List,java.util.ArrayList, com.appspot.i_can_do.master.model.EventCalendar, com.appspot.i_can_do.master.model.Event, com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="java.util.Date" %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/calendar.css"/>" type="text/css"/>
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/WeekCalendar.css"/>" type="text/css"/>

<%
    final Calendar calendar = (Calendar) request
            .getAttribute("calendar");
    final List<EventCalendar> calendars = (List<EventCalendar>) request
            .getAttribute("calendars");

    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    int lastDayOfMonth = dayOfMonth+Calendar.DAY_OF_WEEK;
  %>

<table>

    <%
        for(int i = 0; i<24;i++){

        }
    %>
</table>
