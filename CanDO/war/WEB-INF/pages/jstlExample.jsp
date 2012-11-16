<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" 	  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"  %>

<table>
	<c:if test="${empty audioItems }">
		<tr><td style="text-align: center;">
		<c:choose>
			<c:when test="${EMPTY_MESSAGE != null }">
				${EMPTY_MESSAGE }
			</c:when>
			<c:otherwise>
				Nothing to display
			</c:otherwise>
		</c:choose>
		</td></tr>
	</c:if>
	
	<c:set var="alter" value="false"></c:set>
	<c:forEach items="${audioItems }" var="audioItem" varStatus="status">
	<tr id="audioItem${status.index+1 }" class="${alter ? 'alter' : '' }">
		<c:set var="alter" value="${!alter }"></c:set>
		<td class='col-artist'>${audioItem.artist }</td>
		<td class='col-title'>${audioItem.title }</td>
		<td class='col-genre'>${audioItem.genre }</td>
		<c:choose>
		<c:when test="${showScheduleFlag }">
		<td class='col-schedule' onclick="showScheduleEditor();" >${audioItem.schedule }</td>
			
		<td class='col-request'>
			<span class="request ${audioItem.dontPlayFlag ? '' : 'enabled'}" onclick="onAudioItem ($(this), ${audioItem.storeId }, '${audioItem.filename }', ${audioItem.id })">on</span>
		</td>
		<td class='col-stop'>
			<span class="stop ${audioItem.dontPlayFlag ? 'enabled' : ''}" onclick="offAudioItem ($(this), ${audioItem.storeId }, '${audioItem.filename }', ${audioItem.id })">off</span>
		</td>
		</c:when>
		<c:when test="${requestStopFlag }">
		<td class='col-request'>
			<span class="request" onclick="requestAudioItem ($(this), ${audioItem.storeId }, ${audioItem.id })">request</span>
		</td>
		<td class='col-stop'>
			<span class="stop" onclick="removeAudioItem ($(this), ${audioItem.storeId }, '${audioItem.filename }', ${audioItem.id })">stop</span>
		</td>
		</c:when>
		</c:choose>
	</tr>
	</c:forEach>
</table>