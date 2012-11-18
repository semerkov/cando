<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.Calendar"%>
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
	
	int dayOfMonth=1;
%>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	rules="all">
	<tr>
		<c:forEach var="i" begin="0" end="${offset}" step="1" varStatus ="status">
			<td class="day nonactive"></td>
		</c:forEach>
		<c:forEach var="i" begin="${dayOfMonth}" end="${6-offset}" step="1" varStatus ="status">
			<td class="day active">${i}</td>
		</c:forEach>
	</tr>
	<tr>
		<c:forEach var="i" begin="1" end="${dayOfMonth+7}" step="1" varStatus ="status">
			<td class="day active">${i}</td>
		</c:forEach>
	</tr>
	<tr>
		<c:forEach var="i" begin="1" end="${dayOfMonth+7}" step="1" varStatus ="status">
			<td class="day active">${i}</td>
		</c:forEach>
	</tr>
	<tr>
		<c:forEach var="i" begin="1" end="${dayOfMonth+7}" step="1" varStatus ="status">
			<td class="day active">${i}</td>
		</c:forEach>
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