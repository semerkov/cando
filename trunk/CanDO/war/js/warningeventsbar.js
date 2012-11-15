$(document).ready(function(e) {
	loading();
});

function loading(){
		$.ajax({
		url : 'warning_events',
		type : 'GET',
		dataType : 'html',
		data : {
			'action' : 'getWarningEvents'
		},
		success : function(data) {
			if (data != "notFoundWarningEvents") {
				$('#warning_events_bar').css('display', 'none');
				$('#warning_events').empty().html(data);
				$('#warning_events_bar').fadeIn("slow");
				init();
			} else {
				$('#warning_events_bar').css('display', 'none');
			}
		},
		error : function(data) {
			alert("Test internet connection!");
		}
	});
}

function init(){
	$('.event_description').css('display', 'none');
	$('.event_name').click(function(e) {
		var div = $(this).next('.event_description').first();
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
}

function close_events_bar(){
	$('#warning_events_bar').fadeOut("slow");
	$('#warning_events').empty();
}