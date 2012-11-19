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
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	rules="all">
	<tr>
		<c:forEach var="i" begin="1" end="<%=offset%>" step="1"
			varStatus="status">
			<td class="day nonactive"></td>
		</c:forEach>
		<c:forEach var="i" begin="<%=dayOfMonth%>" end="<%=7 - offset%>"
			step="1" varStatus="status">
			<td class="day active"><%=dayOfMonth%><ul>
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
				<td class="day active"><%=dayOfMonth%><ul>
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
			<td class="day active"><%=dayOfMonth%><ul>
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
<div id="currMonth" style="display: none;">
	<%=calendar.get(Calendar.MONTH)%></div>
<div id="currYear" style="display: none;">
	<%=calendar.get(Calendar.YEAR)%></div>

<!-- 
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	rules="all">
	<tr>
		<td class="day nonactive">28</td>
		<td class="day nonactive">29
			<ul>
				<li>Buy gifts</li>
				<li>Happy birhday!</li>
			</ul>
		</td>
		<td class="day nonactive">30</td>
		<td class="day nonactive">31</td>
		<td class="day active">1</td>
		<td class="day active">2</td>
		<td class="day active">3</td>
	</tr>
	<tr>
		<td class="day active">4</td>
		<td class="day active">5</td>
		<td class="day active">6</td>
		<td class="day active">7</td>
		<td class="day active">8</td>
		<td class="day active">9</td>
		<td class="day active">10</td>
	</tr>
	<tr>
		<td class="day active">11</td>
		<td class="day active">12</td>
		<td class="day active">13</td>
		<td class="day active">14</td>
		<td class="day today">15
			<ul>
				<li>Send template</li>
			</ul>
		</td>
		<td class="day active">16</td>
		<td class="day active">17
			<ul>
				<li>Student`s day!</li>
			</ul>
		</td>
	</tr>
	<tr>
		<td class="day active">18
			<ul>
				<li>Buy a latter</li>
				<li>Train from Lviv</li>
				<li>Meeting</li>
			</ul>
		</td>
		<td class="day active">19</td>
		<td class="day active">20</td>
		<td class="day active">21</td>
		<td class="day active">22</td>
		<td class="day active">23</td>
		<td class="day active">24</td>
	</tr>
	<tr>
		<td class="day active">25</td>
		<td class="day active">26</td>
		<td class="day active">27</td>
		<td class="day active">28</td>
		<td class="day active">29</td>
		<td class="day active">30</td>
		<td class="day nonactive">1
			<ul>
				<li>First Winter day</li>
			</ul>
		</td>
	</tr>
</table>
 -->