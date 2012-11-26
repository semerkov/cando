﻿<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CanDO - Calendar</title>
<link href="CSS/jquery-ui-1.9.1.custom.min.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="CSS/calendar.css" />
<link rel="stylesheet" type="text/css" href="CSS/warning_events.css" />

<!-- JQuery Framework -->
<script src="js/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/warningeventsbar.js"></script>


</head>

<body>
	<div class="container">

		<div class="header">
			<div class="account">
				<span class="header_login_area;"
					style="vertical-align: bottom; text-height: 14; font-style: oblique;">
					<a href="login.jsp">Login</a>/<a href="register.jsp">Register</a>
				</span>
			</div>

			<div id="logo" onclick="self.location='home.jsp'">CanDO</div>

			<div class="clearfloat"></div>
		</div>

		<div class="content">

			<div class="calendarWrapper">
				<div class="calendarSidebar ui-corner-all">
					<div id="calendarSideBarDatepicker"></div>
					<nav>
						<button class="active">
							<div class="calendarAdd ui-icon ui-icon-triangle-1-ne"></div>
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
					<button name="todayButton" id="todayButton">Today</button>
					<button name="prevMonth" id="prevMonth">&lt;</button>
					<button name="nextMonth" id="nextMonth">&gt;</button>
					<span id="currentMonth">November 2012</span>
				</div>
				<table id="daysNameTable" width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th>Sun</th>
						<th>Mon</th>
						<th>Tue</th>
						<th>Wed</th>
						<th>Thu</th>
						<th>Fri</th>
						<th>Sat</th>
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
						<button class="active">My to-do`s</button>
						<div class="todoAdd ui-icon ui-icon-triangle-1-nw"></div>
						<div class="myTodos">
							<div class="item">
								<div class="square"></div>
                                <button class="todoRemove ui-icon-trash ui-icon"></button>
                                <button class="todoEdit ui-icon-pencil ui-icon"></button>
								Sergey to-do
                                <div class="todo_id" style="display:none;">23</div>
							</div>							
							<div class="item active">
								<div class="square active"></div>
                                <button class="todoRemove ui-icon-trash ui-icon"></button>
                                <button class="todoEdit ui-icon-pencil ui-icon"></button>
								Read books
                                <div class="todo_id" style="display:none;">23</div>
							</div>
						</div>
						<button class="active">Others to-do`s</button>
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

	<div id="popupMask" class="popupMask" onclick="hidePopupDialog();"></div>

	<!-- add new calendar -->
	<div id="calendarAddForm" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Add new calendar
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all" onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addForm">
				<input type="text" name="calendarName" id="calendarName"
					style="width: 98%;" placeholder="Calendar name" />
				<button name="addCalendarButton" id="addCalendarSubmit">Create</button>
		</div>
	</div>
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
	
		
	<div id="editEventsForm">
		<form>
			<div style="text-align: center; padding-bottom: 10px;">Edit
				event</div>
			<div class="eventEditForm">
				<div>
					<div style="width: 50%; float: right;">
						<input type="text" name="eventEditName" id="eventEditName"
							placeholder="Event name" /><br> <input type="text"
							name="eventEditDesc" id="eventEditDesc"
							placeholder="Event description" /><br> <input type="text"
							name="eventEditDateStart" id="eventEditDateStart" /><br> <input
							type="text" name="eventEdirDateFinish" id="eventEdirDateFinish" />
					</div>
					<div>
						<label>Name of event:</label><br> <label>Description:</label><br>
						<label>Start time:</label><br> <label>Finish time:</label>
					</div>
				</div>
				<br>
				<div style="text-align: center; width: 100%">Notification time
				</div>
				<div style="text-align: center;">
					<label>Days</label><input type="text" name="" class="beforeDays" size="2"
						value="0" /> <label>Hours</label><input type="text" name="" class="beforeHours"
						size="2" value="0" /> <label>Minutes</label><input type="text"
						name="" class="beforeMinutes" size="2" value="0" />
				</div>
				<div style="text-align: center">
					<input type="button" name="editEventsUpdateButton"
						id="editEventsUpdateButton" value="Update" />
				</div>
			</div>
		</form>
	</div>
	
	<div id="addEventsForm">
		<form>
			<div style="text-align: center; padding-bottom: 10px;">Add
				event</div>
			<div class="addEditForm">
				<div>
					<div style="width: 50%; float: right;">
						<input type="text" name="eventAddName" id="eventAddName"
							placeholder="Event name" /><br> <input type="text"
							name="eventAddDesc" id="eventAddDesc"
							placeholder="Event description" /><br> <input type="text"
							name="evntAddDateStart" id="evntAddDateStart" /><br> <input
							type="text" name="eventAddDateFinish" id="eventAddDateFinish" />
					</div>
					<div>
						<label>Name of event:</label><br> <label>Description:</label><br>
						<label>Start time:</label><br> <label>Finish time:</label>
					</div>
				</div>
				<br>
				<div style="text-align: center; width: 100%">Notification time
				</div>
				<div style="text-align: center;">
					<label>Days</label><input type="text" class="beforeDays" size="2"
						value="0" /> <label>Hours</label><input type="text" name="" class="beforeHours"
						size="2" value="0" /> <label>Minutes</label><input type="text"
						name="" class="beforeMinutes" size="2" value="0" />
				</div>
				<div style="text-align: center">
					<input type="button" name="addCalendarButton"
						id="addCalendarSubmit" value="Create event!" />
				</div>
			</div>
		</form>
	</div>
	
	<div id="viewEventForm">
		<form>
			<div style="text-align: center; padding-bottom: 10px;">Edit
				event</div>
			<div class="eventEditForm">
				<div>
					<div style="width: 50%; float: right;">
						<label>events name</label> <label>description</label><br> <label>start
							time</label><br> <label>finish time</label><br>
					</div>
					<div>
						<label>Name of event:</label><br> <label>Description:</label><br>
						<label>Start time:</label><br> <label>Finish time:</label>
					</div>
				</div>
				<br>
				<div style="text-align: center; width: 100%">Notification time
				</div>
				<div style="text-align: center;">
					<label>Days: </label>00 <label>Hours: </label>00 <label>Minutes:
					</label>00
				</div>
				<div style="text-align: center">
					<input type="button" style="width: 60px;" name="viewShowOkButton"
						id="viewShowOkButton" value="Ok" /> <input type="button"
						name="viewShowEditEventButton" id="viewShowEditEventButton"
						value="Edit event" />
				</div>
			</div>
		</form>
	</div>


	<div id="confirm"></div>
</body>
</html>