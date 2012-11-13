$(document).ready(function() {
	$.ajax({
		url : 'warning_events',
		type : 'POST',
		dataType : 'html',
		data : {
			'action' : 'getWarningEvents'
		},
		success : function(data) {
			if (data != "") {
				$('#warning_events_bar').html(data).fadeIn("slow");
			} else {
				$('#warning_events_bar').style.display = 'none';
			}
		},
		error : function(data) {
			alert("Test internet connection!");
		}
	});

});