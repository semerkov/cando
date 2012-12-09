<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page
	import="java.text.SimpleDateFormat,java.util.Calendar,java.util.List,java.util.ArrayList, com.appspot.i_can_do.master.model.EventCalendar, com.appspot.i_can_do.master.model.Event, com.google.appengine.api.datastore.KeyFactory"%>
<%
	final Calendar calendar = (Calendar) request
			.getAttribute("calendar");
	calendar.set(Calendar.DATE, 1);
	int firstDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
	int offset = 0;
	if (firstDayOfTheWeek == Calendar.SUNDAY) {
		offset = 0;
	} else if (firstDayOfTheWeek == Calendar.MONDAY) {
		offset = 1;
	} else if (firstDayOfTheWeek == Calendar.TUESDAY) {
		offset = 2;
	} else if (firstDayOfTheWeek == Calendar.WEDNESDAY) {
		offset = 3;
	} else if (firstDayOfTheWeek == Calendar.THURSDAY) {
		offset = 4;
	} else if (firstDayOfTheWeek == Calendar.FRIDAY) {
		offset = 5;
	} else if (firstDayOfTheWeek == Calendar.SATURDAY) {
		offset = 6;
	}

	int dayOfMonth = 1;
	int lastDayOfMonth = calendar
			.getActualMaximum(Calendar.DAY_OF_MONTH);
	int endOffsetVariable = 0;
	int weeks = offset + lastDayOfMonth <= 35 ? 5 : 6;

	final List<EventCalendar> calendars = (List<EventCalendar>) request
			.getAttribute("calendars");
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

<table width="100%" border="1" cellspacing="" cellpadding="0"
	rules="all">
	<tr>
		<c:forEach var="i" begin="1" end="<%=offset%>" step="1"
			varStatus="status">
			<td class="day nonactive"></td>
		</c:forEach>
		<c:forEach var="i" begin="<%=dayOfMonth%>" end="<%=7 - offset%>"
			step="1" varStatus="status">
			<td class="day active ui-state-default <%if(checkToday && dayOfMonth==today){ %> ui-state-highlight <% } %>"><%=dayOfMonth%><ul>
				<%
					calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				for(Event e : events){
					if(formatter.format(e.getStart()).equals(formatter.format(calendar.getTime()))){
						%>
						<li><%=e.getName()%><div class="event_id"><%=KeyFactory.keyToString(e.getKey())%></div></li>
						<%
					}
				}
						dayOfMonth++;
				%></ul></td>
		</c:forEach>
	</tr>

	<c:forEach var="i" begin="1" end="<%=weeks - 2%>" step="1"
		varStatus="status">
		<tr>
			<c:forEach var="i" begin="<%=dayOfMonth%>" end="<%=dayOfMonth + 6%>"
				step="1" varStatus="status">
				<td class="day active ui-state-default <%if(checkToday && dayOfMonth==today){ %> ui-state-highlight <% } %>"><%=dayOfMonth%><ul>
				<%
					calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				for(Event e : events){
					if(formatter.format(e.getStart()).equals(formatter.format(calendar.getTime()))){
						%>
						<li><%=e.getName()%><div class="event_id"><%=KeyFactory.keyToString(e.getKey())%></div></li>
						<%
					}
				}
						dayOfMonth++;
				%></ul></td>
			</c:forEach>
		</tr>
	</c:forEach>
	<tr>
		<c:forEach var="i" begin="<%=dayOfMonth%>" end="<%=lastDayOfMonth%>"
			step="1" varStatus="status">
			<td class="day active ui-state-default <%if(checkToday && dayOfMonth==today){ %> ui-state-highlight <% } %>"><%=dayOfMonth%><ul>
				<%
					calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				for(Event e : events){
					if(formatter.format(e.getStart()).equals(formatter.format(calendar.getTime()))){
						%>
						<li><%=e.getName()%><div class="event_id"><%=KeyFactory.keyToString(e.getKey())%></div></li>
						<%
					}
				}
						dayOfMonth++;
				%></ul></td>
			<%
				endOffsetVariable++;
			%>
		</c:forEach>
		<c:forEach var="i" begin="<%=endOffsetVariable%>" end="6" step="1"
			varStatus="status">
			<td class="day nonactive"></td>
		</c:forEach>
	</tr>
</table>
<div id="currMonth" style="display: none;"><%=calendar.get(Calendar.MONTH)%></div>
<div id="currYear" style="display: none;"><%=calendar.get(Calendar.YEAR)%></div>
<div id="currDay" style="display: none;">1</div>