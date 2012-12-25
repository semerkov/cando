<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@page
	import="com.appspot.i_can_do.master.security.User"%>

<%
User user = (User) request.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CanDO - Calendar</title>
<link href="CSS/jquery-ui-1.9.1.custom.min.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="CSS/calendar.css" />
<link rel="stylesheet" type="text/css" href="CSS/warning_events.css" />
<link rel="stylesheet" type="text/css" href="CSS/WeekCalendar.css"/>
<!-- JQuery Framework -->
<script src="js/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/warningeventsbar.js"></script>


</head>

<body>
	<div class="container">

        <!-- <div class="header">
            <div class="account">

            </div>

             <div id="logo" onclick="self.location='home.jsp'">CanDO</div>

			<div class="clearfloat"></div>
		</div>  -->

		<div class="content">

			<div class="calendarWrapper">
                <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                    <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
                         onclick="seeMyProfile();" style="cursor: pointer;"/>
                </div>
                <div>
                    <span style="font-size: 120%; cursor: pointer;"
                          onclick="seeMyProfile();"><%=user.getName() +" "+ user.getSername()%></span>
                    <button onClick="seeMyContactList();" style="float: left; font-size: 95%;">Contact
                        List</button>
                    <img src="IMG/log_out.png" id="exit"/>
                </div>
				<div class="calendarSidebar ui-corner-all">
					<div id="calendarSideBarDatepicker"></div>
					<nav>
					
						<button class="active">
							My calendars
						</button>
						<div class="myCalendar"></div>
						<button class="active">Others calendars</button>
						<div class="#othersCalendar">
							<div class="item active">
								<div class="square active"></div>
								<button class="calendarEdit ui-icon ui-icon-pencil "></button>
								New Year Parties!
								<div class="owner_name">Artem Pashenko</div>
							</div>
							<div class="item">
								<div class="square"></div>
								<button class="calendarEdit ui-icon ui-icon-pencil "></button>
								Happy Birthday`s
								<div class="owner_name">Natalia Chistotina</div>
							</div>
							<div class="item">
								<div class="square"></div>
								<button class="calendarEdit ui-icon ui-icon-pencil "></button>
								Study calendar
								<div class="owner_name">Vlidimir Semerkov</div>
							</div>
							<div class="item active">
								<div class="square active"></div>
								<button class="calendarEdit ui-icon ui-icon-pencil "></button>
								Work calendar
								<div class="owner_name">Boss</div>
							</div>
						</div>
					</nav>
					<!-- warning events bar -->
					<div id="warning_events_bar">
						<div id="warning_events_header">Immediate events</div>
						<div id="warning_events"></div>
					</div>
					<div class="clearfloat"></div>
				</div>
			</div>

			<div class="calendar">
				<div class="calendarHeader">
					<button name="todayButton" id="todayButton" >Today</button>
					<button name="prevMonth" id="prevMonth" >&lt;</button>
					<button name="nextMonth" id="nextMonth" >&gt;</button>
					<span id="currentMonth" class="ui-state-default"></span>
                    <div id="radioCalendarView" style="float: right; margin-right: 5%; font-size: 14px; ">
                            <input type="radio" id="radio1" name="radio" checked /><label for="radio1" >Month</label>
                            <input type="radio" id="radio2" name="radio"  /><label for="radio2">Week</label>
                            <input type="radio" id="radio3" name="radio" /><label for="radio3">Day</label>
                    </div>
				</div>
				<table id="daysNameTable" width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th><span class="date"></span>Sun</th>
						<th><span class="date"></span>Mon</th>
						<th><span class="date"></span>Tue</th>
						<th><span class="date"></span>Wed</th>
						<th><span class="date"></span>Thu</th>
						<th><span class="date"></span>Fri</th>
						<th><span class="date"></span>Sat</th>
					</tr>
				</table>
				<div id="calendarTableWrapper"></div>
			</div>

			<div class="todoWrapper">
				<div class="todoArrows">
					<span class="arrow-r arrows"></span>
				</div>
				<div class="todoSidebar ui-corner-all">
					<nav>
						<button class="active">My tasks</button>
						<div id="myTodos">
							
                            
                            
						</div>
						<button class="active">Others tasks</button>
						<div class="#othersTodos">
							<div class="item">
								<div class="square"></div>
                                <button class="todoRemove ui-icon-trash ui-icon"></button>
								See films
                                <div class="owner_name">Vladislav Mazur</div>
							</div>
						</div>
					</nav>
					<div class="todoActions"></div>
				</div>
			</div>

		</div>
	</div>

	<div id="popupMask" class="popupMask ui-widget-overlay" onclick="hidePopupDialog();"></div>
	
	<!-- calendar change pop-up-->
	<div id="calendarEditForm" class="ui-corner-all">
		<button onClick="showOnlyThisCalendar();">Show only this
			calendar</button>
		<img src="IMG/close_icon.png"
			style="float: right; background-color: #FFF; cursor: pointer; top: -12px; right: 1px; position: absolute;"
			onClick="hidePopupDialog();" />
		<button onClick="showEditCalendarForm();">Edit settings</button>
		<button onClick="removeCalendarConfirm();">Remove calendar</button>
	</div>

	<!-- edit settings of calendar -->
	<div id="editCalendarForm" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Edit calendar
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all" onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addEditForm">
			<div>
				<div style="width: 50%; float: right;">
					<input type="text" name="editEventAddNameInput"
						id="editEventAddNameInput" placeholder="Calendar name" />
				</div>
				<div>
					<label>Name:</label><br>
				</div>
			</div>
			<br>
			<div style="text-align: center; width: 100%">Shared settings</div>
			<div>
				<div style="width: 50%; float: right;">
					<input type="text" name="" id="" placeholder="Friend name" />
				</div>
				<div>
					<label>Friend: </label>
				</div>
			</div>
			<div style="text-align: center">
				<br> <button type="button" name="saveCalendarButton"
					id="saveCalendarButton">Save</button>
			</div>
		</div>
	</div>
	
	<!-- Add new event form -->
	<div id="addEventsForm" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Add new event
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all" onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addEditForm">
			<table style="width: 100%;">
				<tr>
					<td><label>Name of event:</label></td>
					<td><input type="text" name="eventAddName" id="eventAddName"
						placeholder="Event name" /></td>
				</tr>
				<tr>
					<td><label>Description:</label></td>
					<td><input type="text"
						name="eventAddDesc" id="eventAddDesc"
						placeholder="Event description" /></td>
				</tr>
				<tr>
					<td><label>Start time:</label></td>
					<td><input type="text"
						name="eventAddDateStart" id="eventAddDateStart" /></td>
					
				</tr>
				<tr>
					<td><label>Finish time:</label></td>
					<td><input
						type="text" name="eventAddDateFinish" id="eventAddDateFinish" /></td>
				</tr>
				<tr>
					<td><label>Event calendar:</label></td>
					<td><select id="selectEventsCalendar" class="ui-corner-all"></select></td>
				</tr>
			</table>
			<div style="margin: 0 32%;">Notification time</div>
			<div style="text-align: center;">
				<label>Days</label><input type="text" id="warningTimeDaysAddForm" class="beforeDays" size="2"
					value="0" /> <label>Hours</label><input type="text" name="" id="warningTimeHoursAddForm" class="beforeHours"
					size="2" value="0" /> <label>Minutes</label><input type="text"
					name="" id="warningTimeMinutesAddForm" class="beforeMinutes" size="2" value="0" />
			</div>
			<div style="text-align: center">
					<button type="button" name="addEventButton"
					id="addEventSubmit">Create event!</button>
			</div>
		</div>
	</div>
	
	<!-- Edit event form -->
	<div id="editEventsForm" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Edit event
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all" onClick="hidePopupDialog();">close</span>
			</div>
		</div>
				<div class="addEditForm">
			<table style="width: 100%;">
				<tr>
					<td><label>Name of event:</label></td>
					<td><input type="text" name="eventEditName" id="eventEditName"
						placeholder="Event name" /></td>
				</tr>
				<tr>
					<td><label>Description:</label></td>
					<td><input type="text"
						name="eventEditDesc" id="eventEditDesc"
						placeholder="Event description" /></td>
				</tr>
				<tr>
					<td><label>Start time:</label></td>
					<td><input type="text"
						name="eventEditDateStart" id="eventEditDateStart" /></td>
					
				</tr>
				<tr>
					<td><label>Finish time:</label></td>
					<td><input
						type="text" name="eventEditDateFinish" id="eventEditDateFinish" /></td>
				</tr>
				<tr>
					<td><label>Event calendar:</label></td>
					<td><select id="selectEventsCalendarEditForm" class="ui-corner-all"></select></td>
				</tr>
			</table>
			<div style="margin: 0 32%;">Notification time</div>
			<div style="text-align: center;">
				<label>Days</label><input type="text" id="warningTimeDaysEditForm" class="beforeDays" size="2"
					value="0" /> <label>Hours</label><input type="text" id="warningTimeHoursEditForm" class="beforeHours"
					size="2" value="0" /> <label>Minutes</label><input type="text"
					id="warningTimeMinutesEditForm" class="beforeMinutes" size="2" value="0" />
			</div>
			<div style="text-align: center">
					<button type="button" id="editEventSubmit">Save event</button>
			</div>
		</div>
	</div>
	<!-- View event form -->
	<div id="viewEventForm" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Event
			<div class="form_title" style="float: right;position: relative;top: 4px;">
				<span class="ui-icon ui-icon-closethick ui-corner-all" style="float:right;" onClick="hidePopupDialog();">close</span>
				<span class="ui-icon ui-icon-trash ui-corner-all" style="float:right; margin-right: 8px;" onClick="removeEventConfirm();">delete</span>	
			</div>
		</div>
		<div class="eventEditForm">
			<div id="viewEventContainer">
				
			</div>
			<div style="text-align: center">
				<button type="button" name="viewShowOkButton"
				id="viewShowOkButton">Ok</button>
				<button type="button" name="viewShowEditEventButton"
				id="viewShowEditEventButton">Edit event</button>
			</div>
		</div>
	</div>
		<!-- edit task form -->
	<div id="todosEditForm" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable" role="dialog">
		<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Edit task
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all" onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="editTaskForm" style="margin: 10px;">
			<input type="text" name="taskNameEdit" id="taskNameEdit"
				style="width: 98%;"/>
            <br/>
			<input type="text" name="taskDateEdit" id="taskDateEdit" placeholder="date"
				style="width: 98%;"/>
            <br/>
			<textarea name="taskDescriptionEdit" id="taskDescriptionEdit" placeholder="description" rows="6" cols="50"
				style="width: 98%; font-size: 14px;"></textarea>
                <div style="text-align: center">
			<button name="editTaskButton" id="editTaskButton">Confirm</button>
            </div>
		</div>
	</div>
	
	<div id="confirm"></div>
</body>
</html>