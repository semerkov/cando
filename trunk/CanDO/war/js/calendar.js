var calendar_id = "";
var curCalendarName="";

var arr_active_calendar_id;
var arr_active_calendar_name;

var curTodoName="";
var todo_id="";

function getActiveCalendars()
{
	var c = $('.myCalendar');
	if(c!=null)
	{
		var t = $(c).find('.item.active .calendar_id');
		arr_active_calendar_id = new Array(t.length);
			t.each(function(index, element) {
                arr_active_calendar_id[index]=$(element).text();
            });
		
		var n = $(c).find('.item.active .calendar_name');
		arr_active_calendar_name = new Array(n.length);
			n.each(function(index, element) {
                arr_active_calendar_name[index]=$(element).text();
            });
	}
		
}

function getCurrentCalendarName()
{
	return $(".myCalendar div:contains("+$(calendar_id).text()+")").next(".calendar_name").text();
}

function calendarTableClicks(){
	// add new event
	$('.day.active').click(function(e) {
		$(this).addClass('ui-state-active');
		//set position
		var s = this.getBoundingClientRect();
		var top = s.top + (Math.abs(s.top - s.bottom) / 2) - 350;
		top = top > 0? top : 20;
		var left = s.left + (Math.abs(s.left - s.right) / 2)- 190;
		left = left < $(window).width() - 390 ? left : left - 125;
		// set select calendars
		var options = "";
		for(i=0; i < arr_active_calendar_id.length; i++ ){
			var option = "<option value="+arr_active_calendar_id[i]+">"+arr_active_calendar_name[i]+"</option>";
			options += option;
		}
		//prepare add event form
		$('#selectEventsCalendar').html(options);
		$('#eventAddName').css('border-color', 'white');
		$('#eventAddDateStart').css('border-color', 'white');
		$('#eventAddName').val("");
		$('#eventAddDesc').val("");
		$('#eventAddDateStart').val("");
		$('#eventAddDateFinish').val("");
		showPopupDialog('addEventsForm', top, left);
		e.stopPropagation();
	});
	
	<!--не доконца работает-->
	var dateS = new Date();
	var dateF = new Date();
	/*date.setDate(Date.parse());*/
	$('#eventAddDateStart').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDateTime: dateS,
		onClose: function(datetimeText, datepickerInstance){
				var d = new Date();
				/*d.setDate(Date.parse(datetimeText));*/
                $('#eventEdirDateFinish',document).datetimepicker({minDateTime: d});			
		}
	});
	$('#eventAddDateStart').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDateTime: dateS
		});
	$('#eventAddDateFinish').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDateTime: dateS
		});
	$('#eventEditDateStart').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDateTime: dateS
		});
	$('#eventEditDateFinish').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDateTime: dateS
		});
	$('#calendarTableWrapper li').click(function(e){	
		var eventKey = $(this).children('div.event_id').text();
		$.ajax({
			url : 'calendar',
			type : 'POST',
			data : {
				'action' : 'viewEvent',
				'eventKey' : eventKey
			},
			success : function(data) {
				$('#viewEventContainer').html(data);
				var H = $(window).height();
				var W = $(window).width();
				var t = $('#viewEventForm');
				showPopupDialog('viewEventForm',(H - t.height())/2, (W - t.width())/2);
			},
			error : function(data) {
				alert("Error updateCalendar")
			}
		});
		e.stopPropagation();
	});
	
	$('#viewShowOkButton').click(function(e){
		hidePopupDialog();
		e.stopPropagation();
	});
};

$(document).ready(
		function() {
			retrieveEventCalendarMenu();

			todoMenuClicks();
			$( ".ui-draggable" ).draggable();
			$( "button" ).button();
			$( "#calendarSideBarDatepicker" ).datepicker();
			$("input").addClass('ui-corner-all');
			
			$('#saveCalendarButton').click(function(e){	
				var name = $('#editEventAddNameInput').val();
				if (name == "") {
					$('#editEventAddNameInput').css('border-color', 'red');
				} else {
					$('#editEventAddNameInput').css('border-color', 'green');
					
					$.ajax({
						url : 'calendar',
						type : 'POST',
						data : {
							'action' : 'updateCalendar',
							'calendarKey' : calendar_id.text(),
							'calendarName' : name
						},
						success : function(data) {
							retrieveEventCalendarMenu();
							hidePopupDialog();

						},
						error : function(data) {
							alert("Error updateCalendar")
						}
					});
				}					
				e.stopPropagation();
			});
			$('#addEventSubmit').click(function(e){
				var name = $('#eventAddName').val();
				var finishTime = $('#eventAddDateFinish').val();
				if (name == "") {
					$('#eventAddName').css('border-color', 'red');
					return;
				} else {
					$('#eventAddName').css('border-color', 'green');
				}
				if(finishTime == ""){
					$('#eventAddDateFinish').css('border-color', 'red');
					return;
				}else{
					$('#eventAddDateFinish').css('border-color', 'green');
				}
				
				$.ajax({
					url : 'calendar',
					type : 'POST',
					data : {
						'action' : 'addEvent',
						'calendarKey' : $('#selectEventsCalendar :selected').val(),
						'eventName' : name,
						'eventDesc' : $('#eventAddDesc').val(),
						'eventStartDay' : $('#eventAddDateStart').val(),
						'eventFinishDay' : finishTime,
						'warningTimeDays' : $('#warningTimeDaysAddForm').val(),
						'warningTimeHours' : $('#warningTimeHoursAddForm').val(),
						'warningTimeMinutes' : $('#warningTimeMinutesAddForm').val()
					},
					success : function(data) {
						retrieveCalender("this");
						hidePopupDialog();

					},
					error : function(data) {
						alert("Error add event")
					}
				});
				
				e.stopPropagation();
			});
			$('#viewShowEditEventButton').click(function(e){
				hidePopupDialog();
				var H = $(window).height();
				var W = $(window).width();
				var t = $('#editEventsForm');
				
				$.ajax({
					url : 'calendar',
					type : 'POST',
					data : {
						'action' : 'getEvent',
						'eventKey' : $('#selectedViewEventKey').text()
					},
					dataType: 'json',
					success : function(data) {
						$('#eventEditName').val(data[0]);
						$('#eventEditDesc').val(data[1]);
						$('#eventEditDateStart').val(data[2]);
						$('#eventEditDateFinish').val(data[3]);
						
						var index = arr_active_calendar_name.indexOf(data[4]);
						var options = "<option value="+arr_active_calendar_id[index]+">"+data[4]+"</option>";
						for(var i=0; i < arr_active_calendar_id.length; i++ ){
							if(i != index) {
								var option = "<option value="+arr_active_calendar_id[i]+">"+arr_active_calendar_name[i]+"</option>";
								options += option;
							}
						}
						$('#selectEventsCalendarEditForm').html(options);
						$('#warningTimeDaysEditForm').val(data[5]);
						$('#warningTimeHoursEditForm').val(data[6]);
						$('#warningTimeMinutesEditForm').val(data[7]);
					},
					error : function(data) {
						alert("Error edit event form!");
					}
				});
				
				showPopupDialog('editEventsForm',(H - t.height())/2, (W - t.width())/2);
				e.stopPropagation();
			});	
			$('#editEventSubmit').click(function(e){
				$.ajax({
					url : 'calendar',
					type : 'POST',
					data : {
						'action' : 'updateEvent',
						'eventKey' : $('#selectedViewEventKey').text(),
						'eventName' : $('#eventEditName').val(),
						'eventDesc' : $('#eventEditDesc').val(),
						'eventStartDay' : $('#eventEditDateStart').val(),
						'eventFinishDay' : $('#eventEditDateFinish').val(),
						'warningTimeDays' : $('#warningTimeDaysEditForm').val(),
						'warningTimeHours' : $('#warningTimeHoursEditForm').val(),
						'warningTimeMinutes' : $('#warningTimeMinutesEditForm').val()
					},
					success : function(data) {
						retrieveCalender("this");
						hidePopupDialog();
						//$('#selectEventsCalendarEditForm').html(options);
						
					},
					error : function(data) {
						alert("Error edit event form!");
					}
				});
				e.stopPropagation();
			});
			
			$('.todoArrows').click(function() {
				var calendar = $('.calendar');
				var arrow = $('.todoArrows span');
				var slider = $('.todoSidebar');
				if (arrow.hasClass('arrow-r')) {
					slider.css('display', 'none');
					$('.todoWrapper').css('width', '0%');
					arrow.toggleClass('arrow-l', true);
					arrow.toggleClass('arrow-r', false);
					calendar.css('width', '77%');
					$(this).css('left', '-10px');
				} else {
					slider.css('display', 'block')
					$('.todoWrapper').css('width', '20%');
					arrow.toggleClass('arrow-r', true);
					arrow.toggleClass('arrow-l', false);
					calendar.css('width', '58%');
					$(this).css('left', '-22px');

				}
			});

			$("nav>button").click(function(e) {
				var div = $(this).next('div').first();
				if ($(this).hasClass('active')) {
					$(this).toggleClass('active', false);
					div.hide("slide", {
						direction : "up"
					}, 300);
				} else {
					$(this).toggleClass('active', true);
					div.show("slide", {
						direction : "up"
					}, 300);
				}
				e.stopPropagation();
			});

			$("#nextMonth").click(function(e) {
				retrieveCalender("next");
				e.stopPropagation();
			});
			$("#prevMonth").click(function(e) {
				retrieveCalender("previous");
				e.stopPropagation();
			});
			$("#todayButton").click(function(e) {
				retrieveCalender("this");
				e.stopPropagation();
			});

			$('#addCalendarSubmit').click(function(e) {
				var name = $('#calendarName').val();
				if (name == "") {
					$('#calendarName').css('border-color', 'red');
				} else {
					$('#calendarName').css('border-color', 'green');

					$.ajax({
						url : 'calendar',
						type : 'POST',
						async : 'false',
						data : {
							'action' : 'addCalendar',
							'calendarName' : name
						},
						success : function(data) {
							retrieveEventCalendarMenu();
							hidePopupDialog();

						},
						error : function(data) {
							alert("Error addCalendar")
						}
					});
				}
				e.stopPropagation();
			});	
			
			
			$('#editEvent').click(function(e) {
				alert("edit event");
			});
			
			$('.beforeDays').spinner({min:'0',max:'30'});
			$('.beforeHours').spinner({min:'0',max:'23'});
			$('.beforeMinutes').spinner({min:'0',max:'59',step:'5'});
			
			$('#exit').click(function(e){
				$.ajax({
						url : '',
						type : 'POST',
						async : 'false',
						data : {
							'action' : 'exit'
						},
						success : function(data){
							window.location.replace("/");
						},
						error : function(data) {
							alert("error exit");
						}
					});
			});
		});

function showEditCalendarForm(){
		hidePopupDialog();
		var H = $(window).height();
		var W = $(window).width();
		var t = $('#editCalendarForm');
		showPopupDialog('editCalendarForm',(H - t.height())/2, (W - t.width())/2);
		$("#editEventAddNameInput").css('border-color','white');
		$("#editEventAddNameInput").val(getCurrentCalendarName());
	};
	
function showOnlyThisCalendar() {
	$('.myCalendar').find('.item.active').each(function(index, element) {
		$(element).toggleClass('active', false);
		$(element).children('div.square').toggleClass('active', false);
    });
	var sItem = $(".myCalendar div:contains("+$(calendar_id).text()+")").parent();
	$(sItem).toggleClass('active', true);
	$(sItem).children('div.square').toggleClass('active', true);
	getActiveCalendars();
	selectedCalendarsChanged();
	hidePopupDialog();
	//retrive table for selected items
	
};


function removeCalendarConfirm() {
	hidePopupDialog();
	$("#confirm").dialog({
		position : [ "center", "center" ],
		title: "Remove this calendar",
		buttons : {
			"Yes" : function() {
				$.ajax({
					url : 'calendar',
					type : 'POST',
					async : 'false',
					data : {
						'action' : 'removeCalendar',
						'calendarKey' : calendar_id.text()
					},
					success : function(data) {
						retrieveEventCalendarMenu();
						hidePopupDialog();

					},
					error : function(data) {
						alert("Error remove calendar")
					}
				});
				$(this).dialog("close");
			},
			"No" : function() {
				$(this).dialog("close");
				hidePopupDialog();
			}
		}
	});
}

function removeEventConfirm() {
	hidePopupDialog();
	$("#confirm").dialog({
		position : [ "center", "center" ],
		title: "Remove this event",
		buttons : {
			"Yes" : function() {
				$.ajax({
					url : 'calendar',
					type : 'POST',
					async : 'false',
					data : {
						'action' : 'removeEvent',
						'eventKey' : $('#selectedViewEventKey').text()
					},
					success : function(data) {
						retrieveCalender("this");
						hidePopupDialog();

					},
					error : function(data) {
						alert("Error remove event")
					}
				});
				$(this).dialog("close");
			},
			"No" : function() {
				$(this).dialog("close");
				hidePopupDialog();
			}
		}
	});
}

function retrieveCalender(monthAction) {
	if (monthAction == "this") {
		retrieveCalenderTable((new Date()).getFullYear(), (new Date())
				.getMonth(), monthAction);
	} else if (monthAction == "next") {
		retrieveCalenderTable($('#currYear').text(), $('#currMonth').text(),
				monthAction);
	} else if (monthAction == "previous") {
		retrieveCalenderTable($('#currYear').text(), $('#currMonth').text(),
				monthAction);
	}
}
function selectedCalendarsChanged(){
	retrieveCalenderTable((new Date()).getFullYear(), (new Date()).getMonth(), "this");
}

function retrieveCalenderTable(year, month, monthAction) {
	$
			.ajax({
				url : 'calendar',
				type : 'POST',
				data : {
					// 'action' : 'addCalendar'
					'action' : 'retrieveCalenderTable',
					'currentMonth' : month,
					'monthAction' : monthAction,
					'currentYear' : year,
					'selectedCalendars' : arr_active_calendar_id.join(',')
				},
				success : function(data) {
					$('#calendarTableWrapper').html(data);
					$('#calendarTableWrapper table').css('height','90%');
					if($('#calendarTableWrapper table tr').length==5){
						// 5 weeks
						$('#calendarTableWrapper table tr').css('height','20%');
					}else{
						// 6 weeks
						$('#calendarTableWrapper table tr').css('height','16.6%');
					}
					
					var calendarHeaderText = getMonthNameByNumber(parseInt($(
							'#currMonth').text()))
							+ $('#currYear').text();
					$('#currentMonth').html(calendarHeaderText);
					
					calendarTableClicks();
				},
				error : function(data) {
					$('#calendarTableWrapper')
							.html(
									"<table><tr><td style='text-align:center;'>Can't load calendar</td></tr></table>");
					console.log(data);
				}
			});
	$("body").css("cursor", "auto");	
};

var showPopupDialog = function(dialogId, topPosition, leftPosition) {
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();

	$('#popupMask').css({
		'width' : maskWidth,
		'height' : maskHeight
	});
	$('#popupMask').css('display', 'block');

	$("#" + dialogId).css('top', topPosition);
	$("#" + dialogId).css('left', leftPosition);
	$("#" + dialogId).toggleClass('popupDialog', true);

	$("#" + dialogId).fadeIn(200);
};

var hidePopupDialog = function() {
	$('#popupMask').css('display', 'none');
	var pD = $(".popupDialog");
	if (pD != null) {
		pD.fadeOut(200);
		pD.toggleClass('popupDialog', false);
	}
	
	$('.day.ui-state-active').removeClass('ui-state-active');//remove selected after msg
};

var month = new Array();
month[0] = "January";
month[1] = "February";
month[2] = "March";
month[3] = "April";
month[4] = "May";
month[5] = "June";
month[6] = "July";
month[7] = "August";
month[8] = "September";
month[9] = "October";
month[10] = "November";
month[11] = "December";
function getMonthNameByNumber(number) {
	return month[number];
}
function retrieveEventCalendarMenu() {
	$.ajax({
		url : 'calendar',
		type : 'POST',
		data : {
			'action' : 'retrieveEventCalendarMenu'
		},
		success : function(data) {
			$('.myCalendar').html(data);
			calendarMenuClicks();
			getActiveCalendars();
			retrieveCalender("this");
		},
		error : function(data) {
			alert("Error retrieve EventCalendarMenu")
		}
	});
	$("body").css("cursor", "auto");
};

function calendarMenuClicks() {
	$(".myCalendar .item").click(function(e) {
		var square = $(this).children('div').first();
		if ($(this).hasClass('active')) {
			$(this).toggleClass('active', false);
			square.toggleClass('active', false);
		} else {
			$(this).toggleClass('active', true);
			square.toggleClass('active', true);
		}
		getActiveCalendars();
		selectedCalendarsChanged();
		e.stopPropagation();
	});
	
	$("#myTodoItem").click(function() {
		var calendar = $('.calendar');
		if ($(this).hasClass('active')) {
			$(".todoWrapper").css('display', "block");
			if ($('.todoSidebar').css('display') == 'block') {
				calendar.css('width', '58%');
			} else {
				calendar.css('width', '77%');
			}

		} else {
			$(".todoWrapper").css('display', "none");
			calendar.css('width', '77%');
		}
	});

	$('#calendarAdd').click(
			function(e) {
				var t = $(this);
				showPopupDialog('calendarAddForm', t.offset().top + t.height(),
						t.offset().left);
				e.stopPropagation();
			});

	
	$('.calendarSidebar .item').mouseenter(function(){
		$(this).find('.calendarEdit').css('display', 'block');
	}).mouseleave(function(){
		$(this).find('.calendarEdit').css('display', 'none');
	});
	$('.calendarSidebar .calendarEdit').click(
			function(e) {
				var t = $(this);
				curCalendarName = t.parent().text();
				calendar_id = t.next('.calendar_id').first();
				showPopupDialog('calendarEditForm',
						t.offset().top + t.height(), t.offset().left);
				e.stopPropagation();
			});
};

function todoMenuClicks() {
	$(".todoSidebar .item").click(function(e) {
		var square = $('.square',this).first();
		if ($(this).hasClass('active')) {
			$(this).toggleClass('active', false);
			square.toggleClass('active', false);
		} else {
			$(this).toggleClass('active', true);
			square.toggleClass('active', true);
		}
		e.stopPropagation();
	});
	$('.todoSidebar .item').mouseenter(function(){
		$(this).find('.todoEdit').css('display', 'block');
	}).mouseleave(function(){
		$(this).find('.todoEdit').css('display', 'none');
	});
	
	$('.todoSidebar .todoEdit').click(
			function(e) {
				var t = $(this);
				curTodoName = t.parent().text();
				todo_id = t.find('.todo_id').first();
				showPopupDialog('todosEditForm',
						t.offset().top + t.height(), t.offset().left - $('#todosEditForm').width());
				e.stopPropagation();
			});
};