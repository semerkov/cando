<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ page
        import="java.text.SimpleDateFormat,java.util.Calendar,java.util.List,java.util.ArrayList, com.appspot.i_can_do.master.model.EventCalendar, com.appspot.i_can_do.master.model.Event, com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="java.util.Date" %>
<%
    final Calendar calendar = (Calendar) request
            .getAttribute("calendar");
    final List<EventCalendar> calendars = (List<EventCalendar>) request
            .getAttribute("calendars");

    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    int lastDayOfMonth = dayOfMonth+Calendar.DAY_OF_WEEK;


    ArrayList<Event> events = new ArrayList<Event>();
    for (EventCalendar c : calendars) {
        for (Event e : c.getEvents())
            events.add(e);
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    boolean checkToday = false;
    Calendar now = Calendar.getInstance();
    int today = now.get(Calendar.DATE);
    if(calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) ==now.get(Calendar.MONTH)){
        checkToday=true;
    }
%>
<% for(int i = dayOfMonth; i<lastDayOfMonth;i++)
{
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(calendar.getTimeInMillis());
    c.set(Calendar.DATE,i);
    Date date = c.getTime();
%>
 <div>
<%
    for(int time =0;time<48;time++)   {

    }
 %>
     </div>
<%
}
  %>

<div id="currMonth" style="display: none;">
    <%=calendar.get(Calendar.MONTH)%></div>
<div id="currYear" style="display: none;">
    <%=calendar.get(Calendar.YEAR)%></div>