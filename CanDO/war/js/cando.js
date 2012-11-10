function testEmail(){
	var tEmail=$('#register_email').val();
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if(!re.test(tEmail)){
	  //alert('Please type valid email address');
	  $('#register_email').css('border-color','red');
	  return;
	 }else{
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
					//email free;
					$('#register_email').css('border-color','green');
				}else{
					alert("Email occuped");
				}
			},
			error : function(data) {
				alert("Test internet connection!");
			}
		});
	}
	
	
};

$().ready(function() {
	
	$('#register_email').bind('input', function() { 
		testEmail();
	});
});
