$(document).ready(function() {
	$.ajax({
		url : 'warning_events',
		type : 'GET',
		dataType : 'html',
		data : {
			'action':'getWarningEvents'
		},
		success : function(data) {
			if (data != "notFoundWarningEvents") {
				$('#warning_events').empty().html(data);
				$('#warning_events_bar').fadeIn("slow");
			} else {
				$('#warning_events_bar').style.display = 'none';
			}
		},
		error : function(data) {
			alert("Test internet connection!");
		}
	});

});