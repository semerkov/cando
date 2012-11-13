$(document).ready(function() {
	$('#warning_events_bar').text('ddfsdfsdfsfsf');


	 $.ajax(
			  { url : '/warningEventsBar', 
				  type : 'POST',
				  dataType: 'html',
				  success :
					 function(data) {
					  	if(data!=null){ 
					  		$(document).html("dsgdfgdfgd");
						  	$(document).ready( function() {
						  		$('#warning_events_bar').text(data);
						  		$('#warning_events_bar').fadeIn(300); 
						  	});
						  	}else{ 
						  		$('#warning_events_bar').style.display = 'none';
						  		} 
					  	}, 
					  	error :
					  		function(data) alert("Test internet connection!"); 
					  	} 
			  }
	);
 
});