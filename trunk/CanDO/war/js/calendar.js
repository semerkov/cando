var calendar_id = "";
var curCalendarName="";

var arr_active_calendar_id;
var arr_active_calendar_name;

var todo_id;
var curTodoName;

var curSelectedDate = new Date();

function removeUiSelected()
{
	$(".dayCalendarBody .ui-selected").toggleClass('ui-selected',false);
}

function dayCalendarClicksInit(){
		//$('#daysNameTable').css('display','none');
		
        $( ".dayCalendarBody" ).selectable({
			start: function(){
				removeUiSelected();
			},
            stop: function() {
				var el = $( ".ui-selected", this );
				var el_first = el.first();
				var el_last = el.last();
                var start_hours = el_first.find('.start_hours').first().text();
				var start_minutes = el_first.find('.start_minutes').first().text();
				var finish_hours = el_last.find('.finish_hours').first().text();
				var finish_minutes = el_last.find('.finish_minutes').first().text();
				//alert(start_hours+":"+start_minutes+"-"+finish_hours+":"+finish_minutes);

		
			initAddEventForm();
			
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
			$('#eventAddDateFinish').css('border-color', 'white');
			$('#eventAddName').val("");
			$('#eventAddDesc').val("");
			var date = $('#currDay').text()+'.'+ $('#currMonth').text()+'.'+$('#currYear').text();
			$('#eventAddDateStart').val(date+' '+start_hours+':'+start_minutes);
			$('#eventAddDateFinish').val(date+' '+finish_hours+':'+finish_minutes);
			showPopupDialog('addEventsForm', top, left);
			}
        });
 
}

function weekCalendarClicksInit(){
		
        $( ".dayCalendarBody" ).selectable({
			start: function(){
				removeUiSelected();
			},
            stop: function() {
                var start_hours = $( ".ui-selected", this ).first().find('.start_hours').first().text();
				var start_minutes = $( ".ui-selected", this ).first().find('.start_minutes').first().text();
				var finish_hours = $( ".ui-selected", this ).last().find('.finish_hours').first().text();
				var finish_minutes = $( ".ui-selected", this ).last().find('.finish_minutes').first().text();
				//alert(start_hours+":"+start_minutes+"-"+finish_hours+":"+finish_minutes);
				
				
		
			initAddEventForm();
			
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
			var date = $('#currDay').text()+'.'+ $('#currMonth').text()+'.'+$('#currYear').text();
			$('#eventAddDateStart').val(date+' '+start_hours+':'+start_minutes);
			$('#eventAddDateFinish').val(date+' '+finish_hours+':'+finish_minutes);
			showPopupDialog('addEventsForm', top, left);
            }
        });

}
function fillHeader(type){
	if(type=="month"||type=="day"){
		$('#daysNameTable').css('width','93%');
		$('#daysNameTable').css('margin-left','3%');
	}else if(type=="week"){
		$('#daysNameTable').css('width','93%');
		$('#daysNameTable').css('margin-left','3%');
	}
	
	if(type=="month"){
		$('#daysNameTable th').css('display','table-cell');
	}else if(type=="week"){
		$('#daysNameTable th').css('display','table-cell');
	}else if(type=="day"){
		weekDay = curSelectedDate.getDay();
		$('#daysNameTable th').css('display','none');
		$('#daysNameTable th:eq('+weekDay+')').css('display','block');
	}
}
$(document).ready(
		function() {
			retrieveEventCalendarMenu();
			viewTasks();

            $('#radioCalendarView').buttonset();

            $('#radioCalendarView').change(function(e){
                var str = $(this).find("label[aria-pressed='true']").attr('for');
                if(str=='radio1'){
                    retrieveCalenderTable($('#currYear').text(), $('#currMonth').text(),"this");
                    fillHeader('month');
                }else if(str=='radio2'){
					viewCalenderWeek($('#currYear').text(), $('#currMonth').text(),$('#currDay').text(),"this");
					fillHeader('week');
                }else if(str=='radio3'){
                    viewDayCalendar($('#currYear').text(), $('#currMonth').text(),$('#currDay').text(),"this");
                    fillHeader('day');
                }
                e.stopPropagation();
            });

			$( ".ui-draggable" ).draggable();
			$( "button" ).button();
			$("input").addClass('ui-corner-all');
			$( "#calendarSideBarDatepicker" ).datepicker({
		        changeMonth: true,
		        changeYear: true,
				onSelect: function(dateText,inst){
					setCalendarDayByClick(dateText,inst);
				}
			});
			$('#editTaskButton').click(function(e){
                    editTaskButtonClick();
                e.stopPropagation();
            });
            $('#todosEditForm').keypress(function(e){
                if(e.which==13) editTaskButtonClick();
                e.stopPropagation();
            });


			$('#saveCalendarButton').click(function(e){
                saveCalendar();
				e.stopPropagation();
			});

			$('#addEventSubmit').click(function(e){
                addEvent();
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
			
			$('#editEvent').click(function(e) {
				alert("edit event");
			});
			
			$('.beforeDays').spinner({min:'0',max:'30'});
			$('.beforeHours').spinner({min:'0',max:'23'});
			$('.beforeMinutes').spinner({min:'0',max:'59',step:'5'});
			
			$('#exit').click(function(e){
				$.ajax({
						url : '/login',
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

function addEvent(){
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

};

function saveCalendar(){
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
};

function editTaskButtonClick(){
    if($('#taskNameEdit').val()==""){
        $('#taskNameEdit').css('border-color','red');
    }
    else{
        $('#taskNameEdit').css('border-color','gray');

        var taskName =$('#taskNameEdit').val();
        var todo_date = $('#taskDateEdit').val();
        var todo_description = $('#taskDescriptionEdit').val();
        $.ajax({
            url : 'calendar',
            type : 'POST',
            data : {
                'action' : 'updateTask',
                'taskKey': todo_id,
                'taskName': taskName,
                'todo_date': todo_date,
                'todo_description':todo_description
            },
            success : function(data) {
                viewTasks();
                hidePopupDialog();
            },
            error : function(data) {
                alert("Error edit task");
            }
        });
    }
};

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
		
};

function getCurrentCalendarName()
{
	return $(".myCalendar div:contains("+$(calendar_id).text()+")").next(".calendar_name").text();
};

function initAddEventForm(){
	var eventAddDateStart = $('#eventAddDateStart').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDate: new Date(),
		stepHours: 1, 
		stepMinute: 5,
		onClose: function(datetimeText, datepickerInstance){
                eventAddDateFinish.datetimepicker('option','minDate', $(this).datetimepicker('getDate'));
				$('#eventAddDateFinish').val(datetimeText);			
		}
	});
	var eventAddDateFinish = $('#eventAddDateFinish').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDate: new Date(),
		stepMinute: 5,
		onClose:function(datetimeText, datepickerInstance){
			if($(this).datetimepicker('getDate')<eventAddDateStart.datetimepicker('getDate')){
			$('#eventAddDateStart').val(datetimeText);	
			}
		}
	});
};
function initEditEventForm(){
	var eventEditDateStart = $('#eventEditDateStart').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDate: new Date(),
		stepHours: 1, 
		stepMinute: 5,
		onClose: function(datetimeText, datepickerInstance){
                eventEditDateFinish.datetimepicker('option','minDate', $(this).datetimepicker('getDate'));
				$('#eventEditDateFinish').val(datetimeText);			
		}
		});
	var eventEditDateFinish = $('#eventEditDateFinish').datetimepicker({
		dateFormat: 'dd.mm.yy',
		minDate: new Date(),
		stepMinute: 5,
		onClose:function(datetimeText, datepickerInstance){
			if($(this).datetimepicker('getDate')<eventEditDateStart.datetimepicker('getDate')){
			$('#eventEditDateStart').val(datetimeText);	
			}
		}
		});
};
function calendarTableClicks(){
	// add new event
	$('.day.active').click(function(e) {
		
		initAddEventForm();
		
		
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
		
	$('#calendarTableWrapper li').click(function(e){
		initEditEventForm();
			
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

function setCalendarDayByClick(dateText,instance){
	var selectedDate = new Date(dateText);
	if($('#currYear').text()!=selectedDate.getFullYear()
		||$('#currMonth').text()!=selectedDate.getMonth())
		{
			retrieveCalenderTable(selectedDate.getFullYear(), selectedDate.getMonth(),"this");
		}
	$(".day.active.ui-state-active").removeClass('ui-state-active');
	$(".day.active").each(function(index, element) {
        if($(element).text()==selectedDate.getDate()){
			$(element).addClass('ui-state-active');
			selectedDate.setDate($(element).text());
		}
    });
		
	curSelectedDate = selectedDate;
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
};

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
};

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
};

function retrieveCalenderWeek(action) {
    if (action == "this") {
        viewCalenderWeek((new Date()).getFullYear(), (new Date())
            .getMonth(),(new Date()).getDate(), action);
    } else if (action == "next") {
        viewCalenderWeek($('#currYear').text(), $('#currMonth').text(),$('#currDay'),
            action);
    } else if (action == "previous") {
        viewCalenderWeek($('#currYear').text(), $('#currMonth').text(),$('#currDay'),
            action);
    }
}

function retrieveCalenderDay(action) {
    if (action == "this") {
        viewDayCalendar((new Date()).getFullYear(), (new Date())
            .getMonth(),1, action);
    } else if (action == "next") {
        viewDayCalendar($('#currYear').text(), $('#currMonth').text(),$('#currDay').text(),
            action);
    } else if (action == "previous") {
        viewDayCalendar($('#currYear').text(), $('#currMonth').text(),$('#currDay').text(),
            action);
    }
};
function viewDayCalendar(year, month,day, action) {
    $.ajax({
        url : 'calendar',
        type : 'POST',
        async: false,
        data : {
            'action' : 'retrieveDayCalender',
            'currentMonth' : month,
            'dayAction' : action,
            'currentYear' : year,
            'currentDay': day,
            'selectedCalendars' : arr_active_calendar_id.join(',')
        },
        success : function(data) {
            $('#calendarTableWrapper').html(data);

            var calendarHeaderText =$('#currDay').text()+","+getMonthNameByNumber(parseInt($(
                '#currMonth').text())) + " "
                + $('#currYear').text();
            $('#currentMonth').html(calendarHeaderText);
			dayCalendarClicksInit();
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

function viewCalenderWeek(year, month,day, action) {
    $.ajax({
        url : 'calendar',
        type : 'POST',
        async: false,
        data : {
            'action' : 'retrieveCalenderWeek',
            'currentMonth' : month,
            'weekAction' : action,
            'currentYear' : year,
            'currentDay': day,
            'selectedCalendars' : arr_active_calendar_id.join(',')
        },
        success : function(data) {
            $('#calendarTableWrapper').html(data);

            var calendarHeaderText = getMonthNameByNumber(parseInt($(
                '#currMonth').text()))
                + $('#currYear').text();
            $('#currentMonth').html(calendarHeaderText);

			weekCalendarClicksInit();
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

function selectedCalendarsChanged(){
	retrieveCalenderTable((new Date()).getFullYear(), (new Date()).getMonth(), "this");
};

function retrieveCalenderTable(year, month, monthAction) {
	$.ajax({
				url : 'calendar',
				type : 'POST',
				async: false,
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
							'#currMonth').text()))+ " "
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
};

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

function viewTasks() {
	$.ajax({
		url : 'calendar',
		type : 'POST',
		data : {
			'action' : 'viewTasks'
		},
		success : function(data) {
			$('#myTodos').html(data);
			todoMenuClicks();
		},
		error : function(data) {
			alert("Error retrieve Tasks")
		}
	});
	$("body").css("cursor", "auto");
}

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
	
	$('#calendarName').blur(function(e){
		addCalendar();
		e.stopPropagation();
	}).keypress(function(e){
            if ( e.which == 13 ) {
            addCalendar();
            }
            e.stopPropagation()});

	
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
}

function addCalendar(){
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
				alert("Error addCalendar");
			}
		});
	}
}

function todoMenuClicks() {
	$(".todoSidebar .item").click(function(e) {
		todo_id = $(this).find('.todo_id').first().text();
		changeTaskState(todo_id);
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
	$('.todoEdit').button();
	$('.todoRemove').button();
	$('.todoSidebar .item').mouseenter(function(){
		$(this).find('.todoEdit').css('display', 'block');
		$(this).find('.todoRemove').css('display', 'block');
		$(this).find('.todo_date').css('display', 'none');
	}).mouseleave(function(){
		$(this).find('.todoEdit').css('display', 'none');
		$(this).find('.todoRemove').css('display', 'none');
		$(this).find('.todo_date').css('display', 'block');
	});
	
	
	$('.todoSidebar .todoEdit').click(
			function(e) {
				$('#taskDateEdit').datetimepicker({dateFormat: 'dd.mm.yy'});
				var t = $(this);
				todo_id = $(t.parent()).find('.todo_id').first().text();
				var taskName = $(t.parent()).find('.taskName').first().text();
				var todo_date = $(t.parent()).find('.todo_date').first().text();
				var todo_description = $(t.parent()).find('.todo_description').first().text();
				$('#taskNameEdit').val(taskName);
				$('#taskDateEdit').val(todo_date);
				$('#taskDescriptionEdit').val(todo_description);
				showPopupDialog('todosEditForm',
						t.offset().top + t.height(), t.offset().left - $('#todosEditForm').width());
				e.stopPropagation();
			});
	$('.todoSidebar .todoRemove').click(
			function(e) {
				var t = $(this);
				var todo_id = $(t.parent()).find('.todo_id').first().text();
				removeTaskConfirm(todo_id);
				e.stopPropagation();
			});	
	$('#addTaskField').blur(function(e){
		addTask();
		e.stopPropagation();
	}).keypress(function(e){
            if ( e.which == 13 ) {
            addTask();
            }
            e.stopPropagation()});

}
function addTask(){
		var str = $('#addTaskField').val();
		if(str!=""){
			$.ajax({
			url : 'calendar',
			type : 'POST',
			data : {
				'action' : 'addTask',
				'taskName': str
			},
			success : function(data) {
				viewTasks();
			},
			error : function(data) {
				alert("Error add task");
			}
			});
		}
}
function changeTaskState(taskKey){
			$.ajax({
			url : 'calendar',
			type : 'POST',
			data : {
				'action' : 'changeTaskState',
				'taskKey': taskKey
			},
			success : function(data) {
				//viewTasks();
			},
			error : function(data) {
				alert("Error change Task State");
			}
			});
}
function removeTaskConfirm(todo_id) {
	hidePopupDialog();
	$("#confirm").dialog({
		position : [ "center", "center" ],
		title: "Remove this task?",
		buttons : {
			"Yes" : function() {
				$.ajax({
					url : 'calendar',
					type : 'POST',
					data : {
						'action' : 'removeTask',
						'taskKey' : todo_id
					},
					success : function(data) {
						viewTasks();
						/*hidePopupDialog();*/

					},
					error : function(data) {
						alert("Error remove task");
					}
				});
				$(this).dialog("close");
			},
			"No" : function() {
				$(this).dialog("close");
				/*hidePopupDialog();*/
			}
		}
	});
}

function seeMyProfile(){
	document.location.href = "/profile";
}
function seeMyContactList(){
    document.location.href = "/contactList";
}
