$(document).ready(function() {
	
	init();
	
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
	
});

function init(){
	retrieveCalenderTable("Novemer", "Next");
}
function retrieveCalenderTable(month, monthAction){
	$('#calendarTableWrapper').html("Please wait...");
	$.ajax({
		url : 'calendar',
		type : 'POST',
		data : {
			'action' : 'retrieveCalenderTable',
			'currentMonth' : month,
			'monthAction' : monthAction
		},
		dataType: 'text',
		success : function(data) {
			$('#calendarTableWrapper').html(data);
		},
		error : function(data) {
			$('#calendarTableWrapper').html("<table><tr><td style='text-align:center;'>Can't load calendar</td></tr></table>");
			console.log(data);
		}
	});
	$("body").css("cursor", "auto");
};