$(document).ready(function(e) {
	loading();
	$('#warning_events_header').click(function() {
		$("#warning_events").toggle({
			'effect' : 'blind',
			'duration' : 'slow'
		});
	});
});
var timer = setTimeout(function run() {
		loading();
	    timer = setTimeout(run, 2000);
		},
		2000);

function loading() {
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

function init() {
	$("#warning_events_bar").accordion({
		header : '.active_accordion',
		autoHeight : true,
		collapsible : true,
		active : false
	/* event: 'mouseover' */
	});
}

function close_events_bar() {
	$('#warning_events_bar').fadeOut("slow");
	$('#warning_events').empty();
}