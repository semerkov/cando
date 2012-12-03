<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page
	import="java.util.ArrayList,com.appspot.i_can_do.master.model.Task"%>
<%@page
	import="java.util.ArrayList,com.appspot.i_can_do.master.security.State"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>

<%
ArrayList<Task> tasks = (ArrayList<Task>) request.getAttribute("tasks");
if (tasks != null) {
SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	for (Task task : tasks) {
		String active="";
		String key = KeyFactory.keyToString(task.getKey());
		if(task.getState()==State.Done) active="active";

%>
<div class="item <%=active%>">
	<div class="square"></div>
	<button class="todoRemove ui-icon-trash ui-icon"></button>
	<button class="todoEdit ui-icon-pencil ui-icon"></button>
	<span class="taskName"><%=task.getName()%></span>
	<div class="todo_id" style="display: none;"><%=key%></div>
	<div class="todo_description" style="display: none;"><%=task.getDescription()!=null?task.getDescription():""%></div>
	<div class="todo_date" style="float:right; font-size: 9px;color: grey;margin-top: 5px; margin-right: 3px;"><%=task.getDate()!=null?formatter.format(task.getDate()):""%></div>
</div>
<%
	}
}
%>
<input type="text" name="addTaskField" id="addTaskField" placeholder="Task name" />