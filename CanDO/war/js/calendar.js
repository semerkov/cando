var calendar_id = "";
var curCalendarName="";

var arr_active_calendar_id;
var arr_active_calendar_name;

function getActiveCalendars()
{
	var c = $('.myCalendar');
	if(c!=null)
	{
		var t = $(c).find('.item.active .calendar_id');
		arr_active_calendar_id = new Array(t.length);
			t.each(function(index, element) {
                arr_active_calendar_id[index]=element.innerText;
            });
		
		var n = $(c).find('.item.active .calendar_name');
		arr_active_calendar_name = new Array(n.length);
		alert(n.length);
			n.each(function(index, element) {
                arr_active_calendar_name[index]=element.innerText;
            });
	}
		
}


function calendarTableClicks(){
	﻿$('.day.active').click(function(e) {
		$(this).addClass('selected');
		var s = this.getBoundingClientRect();
		showPopupDialog('msg',s.top + (Math.abs(s.top - s.bottom) / 2) - 185,  s.left + (Math.abs(s.left - s.right) / 2) - 200);
		/*
		if(($(this).hasClass('day active') || $(this).hasClass('day today')) && !$(this).hasClass('select')) 
			{
				jQuery('#msg').remove();
				$('.select').toggleClass('select', false);
				$(this).toggleClass('select', true);
				$(this).append('');
			}*/
		e.stopPropagation();
	});

	$('.bubbleclose').click(function(e) {
		alert("close click");
		$('#msg').remove();
		// $('.select').toggleClass('select', false);
		e.stopPropagation();
	});
	
	$("#eventEditDateStart").datetimepicker();
	$("#eventEdirDateFinish").datetimepicker();
	$("#evntAddDateStart").datetimepicker();
	$("#eventAddDateFinish").datetimepicker();
};

$(document).ready(
		function() {
			retrieveEventCalendarMenu();
			retrieveCalender("this");

			calendarMenuClicks();
			calendarTableClicks();
			
			$(".calendarSidebar li").prepend(
					'<span class="arrow-u navarrows">&nbsp;</span>');
			$(".todoSidebar li").prepend(
					'<span class="arrow-u navarrows">&nbsp;</span>');


			$('#saveCalendarButton').click(function(e){
				
				
				var name = $('#editEventAddNameInput').val();
				alert(curCalendarName);
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
			
			$('.todoArrows').click(function() {
				var calendar = $('.calendar');
				var arrow = $('.todoArrows span');
				var slider = $('.todoSidebar');
				if (arrow.hasClass('arrow-r')) {
					slider.css('display', 'none');
					$('.todoWrapper').css('width', '0%');
					arrow.toggleClass('arrow-l', true);
					arrow.toggleClass('arrow-r', false);
					calendar.css('width', '82%');
					$(this).css('left', '-10px');
				} else {
					slider.css('display', 'block')
					$('.todoWrapper').css('width', '15%');
					arrow.toggleClass('arrow-r', true);
					arrow.toggleClass('arrow-l', false);
					calendar.css('width', '68%');
					$(this).css('left', '-22px');

				}
			});

			$("li").click(function() {
				var arrow = $(this).children('span');
				var div = $(this).next('div').first();
				if ($(this).hasClass('active')) {
					$(this).toggleClass('active', false);
					arrow.toggleClass('arrow-u', false);
					arrow.toggleClass('arrow-d', true);
					div.hide("slide", {
						direction : "up"
					}, 300);
				} else {
					$(this).toggleClass('active', true);
					arrow.toggleClass('arrow-u', true);
					arrow.toggleClass('arrow-d', false);
					div.show("slide", {
						direction : "up"
					}, 300);
				}
			});

			$('.todoWrapper .todoSidebar .close').click(function() {
				$("#myTodoItem").click();
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
			
			
			
			
		});
<<<<<<< .mine
		
		
		
		
=======

>>>>>>> .r77
function showEditCalendarForm(){
		hidePopupDialog();
		var H = $(window).height();
		var W = $(window).width();
		var t = $('#editCalendarForm');
		showPopupDialog('editCalendarForm',(H - t.height())/2, (W - t.width())/2);
	};
	
function showOnlyThisCalendar() {
	alert("Not completed yet");
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

function retrieveCalenderTable(year, month, monthAction) {
	$('#calendarTableWrapper')
			.html(
					"<table><tr><td style='text-align:center;'>Please wait...</td></tr></table>");
	$
			.ajax({
				url : 'calendar',
				type : 'POST',
				data : {
					// 'action' : 'addCalendar'
					'action' : 'retrieveCalenderTable',
					'currentMonth' : month,
					'monthAction' : monthAction,
					'currentYear' : year
				},
				success : function(data) {
					$('#calendarTableWrapper').html(data);
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
	
	$('.day.selected').removeClass('selected');//remove selected after msg
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
		},
		error : function(data) {
			alert("Error retrieve EventCalendarMenu")
		}
	});
	$("body").css("cursor", "auto");
};

function calendarMenuClicks() {
	$(".item").click(function() {
		var square = $(this).children('div').first();
		if ($(this).hasClass('active')) {
			$(this).toggleClass('active', false);
			square.toggleClass('active', false);
		} else {
			$(this).toggleClass('active', true);
			square.toggleClass('active', true);
		}
	});
	
	$("#myTodoItem").click(function() {
		var calendar = $('.calendar');
		if ($(this).hasClass('active')) {
			$(".todoWrapper").css('display', "block");
			if ($('.todoSidebar').css('display') == 'block') {
				calendar.css('width', '68%');
			} else {
				calendar.css('width', '82%');
			}

		} else {
			$(".todoWrapper").css('display', "none");
			calendar.css('width', '83%');
		}
	});

	$('.calendarAdd').click(
			function(e) {
				var t = $(this);
				showPopupDialog('calendarAddForm', t.offset().top + t.height(),
						t.offset().left);
				e.stopPropagation();
			});

	
	$('.item').mouseenter(function(){
		$(this).find('.calendarEdit').css('display', 'block');
	}).mouseleave(function(){
		$(this).find('.calendarEdit').css('display', 'none');
	});
	$('.calendarEdit').click(
			function(e) {
				var t = $(this);
				curCalendarName = t.parent().text();
				calendar_id = t.next('.calendar_id').first();
				showPopupDialog('calendarEditForm',
						t.offset().top + t.height(), t.offset().left);
				e.stopPropagation();
			});
};