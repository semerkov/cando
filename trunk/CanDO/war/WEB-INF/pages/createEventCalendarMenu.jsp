<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page
	import="java.util.List, com.appspot.i_can_do.master.model.EventCalendar, com.google.appengine.api.datastore.KeyFactory"%>
<%
	final List<EventCalendar> calendar = (List<EventCalendar>) request
			.getAttribute("calendars");
	int i = 0;
%>

<c:forEach var="i" begin="0" end="<%=calendar.size()-1%>" step="1"
	varStatus="status">
	<div class="item active">
		<div class="square active"></div>
		<div class="calendarEdit">▼</div>
		<div class="calendar_id"><%=KeyFactory.keyToString(calendar.get(i).getKey())%></div>
		<%=calendar.get(i++).getName()%>
	</div>
</c:forEach>

<div class="item active" id="myTodoItem">
	<div class="square active"></div>
	<div class="calendarEdit">▼</div>
	Tasks
</div>
