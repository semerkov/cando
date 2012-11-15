function hideShowTodo() {
	var sidebar = $('.todoSidebar');
	if (sidebar.css('display') == 'block') {
		sidebar.css('display', 'none');
	} else {
		sidebar.css('display', 'block');
	}
};

$(document).ready(function() {
	
	$("li").prepend('<span class="arrow-u navarrows">&nbsp;</span>');

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
			}, 1000);
		} else {
			$(this).toggleClass('active', true);
			arrow.toggleClass('arrow-u', true);
			arrow.toggleClass('arrow-d', false);
			div.show("slide", {
				direction : "up"
			}, 1000);
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
			$('.todoSidebar').css('display',"block");
			calendar.css('width', '68%');
		} else {
			$(".todoWrapper").css('display',"none");
			calendar.css('width', '83%');
		}
	});

});