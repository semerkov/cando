var calendar_id="";


$(document).ready(function() {
	
	retrieveCalender("this");
	
	$(".calendarSidebar li").prepend('<span class="arrow-u navarrows">&nbsp;</span>');
	$(".todoSidebar li").prepend('<span class="arrow-u navarrows">&nbsp;</span>');

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
			$(".todoWrapper").css('display',"block");
			if($('.todoSidebar').css('display') == 'block'){
				calendar.css('width', '68%');
			}else{
				calendar.css('width', '82%');
			}
			
		} else {
			$(".todoWrapper").css('display',"none");
			calendar.css('width', '83%');
		}
	});
	
	$('.todoWrapper .todoSidebar .close').click(function() {
		$("#myTodoItem").click();
	});
	
	$('.calendarAdd').click( function(e){
			var t = $(this);
			showPopupDialog('calendarAddForm',t.offset().top + t.height(),t.offset().left);
			e.stopPropagation();
	});
	
	$('.calendarEdit').click( function(e){
			var t = $(this);
			calendar_id=t.next('.calendar_id').first();
			showPopupDialog('calendarEditForm',t.offset().top + t.height(),t.offset().left);
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
});

function showOnlyThisCalendar(){
	alert(calendar_id.text());
}

function retrieveCalender(monthAction){
	if(monthAction == "this"){
		retrieveCalenderTable((new Date()).getFullYear(), (new Date()).getMonth(), monthAction);
	}else if(monthAction == "next"){
		retrieveCalenderTable( $('#currYear').text(), $('#currMonth').text() ,monthAction);
	}else if(monthAction == "previous"){
		retrieveCalenderTable( $('#currYear').text(), $('#currMonth').text(), monthAction);
	}
}

function retrieveCalenderTable(year, month, monthAction){
	$('#calendarTableWrapper').html("<table><tr><td style='text-align:center;'>Please wait...</td></tr></table>");
	$.ajax({
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
			var calendarHeaderText = getMonthNameByNumber(parseInt($('#currMonth').text())) + $('#currYear').text();
			$('#currentMonth').html(calendarHeaderText);
		},
		error : function(data) {
			$('#calendarTableWrapper').html("<table><tr><td style='text-align:center;'>Can't load calendar</td></tr></table>");
			console.log(data);
		}
	});
	$("body").css("cursor", "auto");
};

var showPopupDialog = function (dialogId, topPosition, leftPosition) {
    var maskHeight = $(document).height();
    var maskWidth = $(window).width();
 
    $('#popupMask').css({'width':maskWidth,'height':maskHeight});
    $('#popupMask').css('display',  'block'); 
        
    $("#" + dialogId).css('top',  topPosition);
    $("#" + dialogId).css('left', leftPosition);
    $("#" + dialogId).toggleClass('popupDialog',true);
    
    $("#" + dialogId).fadeIn(200);
};

var hidePopupDialog = function (){
	$('#popupMask').css('display',  'none');   
	var pD = $(".popupDialog");
    if(pD != null) {
    	pD.fadeOut(200);
		pD.toggleClass('popupDialog',false);
    }
};

var month=new Array();
month[0]="January";
month[1]="February";
month[2]="March";
month[3]="April";
month[4]="May";
month[5]="June";
month[6]="July";
month[7]="August";
month[8]="September";
month[9]="October";
month[10]="November";
month[11]="December";
function getMonthNameByNumber(number){
	return month[number];
}