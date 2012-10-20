function testEmail(tEmail) {
	$.ajax({
		url : 'register',
		type : 'POST',
		data : {
			'action' : 'testEmail',
			'email' : tEmail
		},
		dataType: 'json',
		success : function(data) {
			if(data=="free"){
				alert("11");
			}else{
				alert("22");
			}
		},
		error : function(data) {
			alert("Test internet connection!");
		}
	});
	
};

$().ready(function() {

});
function f (){
	$.post("register", {
		"action" : "testEmail",
		"email" : tEmail
	}, function(data) {
		alert(data);
	}, "application/json");
}