$(document).ready(function(e) {
	init();
	loading();
	$('#warning_events_header').click(function() {
		$("#warning_events").toggle({
			'effect' : 'blind',
			'duration' : 'slow'
		});
	});
});

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
				//$('#warning_events_bar').css('display', 'none');
				$('#warning_events').html(data);
				$('#warning_events_bar').fadeIn("slow");
				$("#warning_events_bar").accordion('destroy');
				init();
			} else {
				$('#warning_events_bar').css('display', 'none');
			}
		},
		error : function(data) {
			alert("Test internet connection!");
		}
	});
	var timer = setTimeout(loading,10000);
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