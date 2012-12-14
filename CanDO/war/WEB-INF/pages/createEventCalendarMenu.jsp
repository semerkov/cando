<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page
	import="java.util.List, com.appspot.i_can_do.master.model.EventCalendar,com.appspot.i_can_do.master.security.User, com.google.appengine.api.datastore.KeyFactory"%>
<%
	final List<EventCalendar> calendar = (List<EventCalendar>) request
			.getAttribute("calendars");
	int i = 0;
%>

<% if(calendar.size() != 0) { %>
<c:forEach var="i" begin="0" end="<%=calendar.size()-1%>" step="1"
	varStatus="status">
	<div class="item active">
		<div class="square active"></div>
		<button class="calendarEdit ui-icon ui-icon-pencil ui-button ui-widget ui-state-default ui-corner-all"></button>
		<div class="calendar_id"><%=KeyFactory.keyToString(calendar.get(i).getKey())%></div>
		<div class="calendar_name"><%=calendar.get(i++).getName()%></div>
	</div>
</c:forEach>

<% } %>

<div class="item" id="myTodoItem">
	<div class="square"></div>
	<button class="calendarEdit ui-icon ui-icon-pencil ui-button ui-widget ui-state-default ui-corner-all"></button>
	<div class="calendar_id"></div>
	<div class="calendar_name">Tasks</div>
</div>

<input type="text" name="calendarName" id="calendarName" class="ui-corner-all"
					style="width: 98%;" placeholder="Calendar name" />
