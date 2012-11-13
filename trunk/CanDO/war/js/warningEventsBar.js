$.ajax({
	url : 'warningEventsBar',
	type : 'POST',
	data : {
		'action' : 'getWarningEvents'
	},
	dataType: 'html',
	success : function(data) {
		if(data!="empty"){
			$('#warningEventsBar').replaseWith(data);
		}else{
			$('#warningEventsBar').style.display = 'none';
		}
	},
	error : function(data) {
		alert("Test internet connection!");
	}
});