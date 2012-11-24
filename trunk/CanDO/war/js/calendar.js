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
			n.each(function(index, element) {
                arr_active_calendar_name[index]=element.innerText;
            });
	}
		
}

function getCurrentCalendarName()
{
	return $(".myCalendar div:contains("+$(calendar_id).text()+")").next(".calendar_name").text();
}

function calendarTableClicks(){
	$('.day.active').click(function(e) {
		$(this).addClass('selected');
		var s = this.getBoundingClientRect();
		showPopupDialog('addEventsForm',s.top + (Math.abs(s.top - s.bottom) / 2) - 260,  s.left + (Math.abs(s.left - s.right) / 2) - 170);
		e.stopPropagation();
	});
	<!--не доконца работает-->
	var dateS = new Date();
	var dateF = new Date();
	/*date.setDate(Date.parse());*/
	$('#eventEditDateStart').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDateTime: dateS,
		onClose: function(datetimeText, datepickerInstance){
				var d = new Date();
				/*d.setDate(Date.parse(datetimeText));*/
				alert(d.toLocaleDateString());
                $('#eventEdirDateFinish',document).datetimepicker({minDateTime: d});			
		}
	});
	$('#eventEdirDateFinish').datetimepicker({
		dateFormat: 'dd.mm.yy'
		});
	$('#evntAddDateStart').datetimepicker({});
	$('#eventAddDateFinish').datetimepicker({});
	
	$('#calendarTableWrapper li').click(function(e){
		var H = $(window).height();
		var W = $(window).width();
		var t = $('#viewEventForm');
		showPopupDialog('viewEventForm',(H - t.height())/2, (W - t.width())/2);
		e.stopPropagation();
	});
	
	$('#viewShowOkButton').click(function(e){
		hidePopupDialog();
		e.stopPropagation();
	});
	$('#viewShowEditEventButton').click(function(e){
		hidePopupDialog();
		var H = $(window).height();
		var W = $(window).width();
		var t = $('#editEventsForm');
		showPopupDialog('editEventsForm',(H - t.height())/2, (W - t.width())/2);
		e.stopPropagation();
	});
	$('#editEventsUpdateButton').click(function(e){
		hidePopupDialog();
		e.stopPropagation();
	});
	
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

			$("nav button").click(function() {
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
	$('.myCalendar').find('.item.active .calendar_id').each(function(index, element) {
		$(element).click();
    });
	$(".myCalendar div:contains("+$(calendar_id).text()+")").parent().click();
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
			getActiveCalendars();
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