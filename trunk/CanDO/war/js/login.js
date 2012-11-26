$(document).ready(function(e) {
	$('#email').bind('focusout', function() {
		testEmail();
	});
	$('#password').bind('focusout', function() {
		testPassword();
	});

	$('#login_button').click(function(e) {
		tryLogin();
		e.stopPropagation();
	});
});

function testEmail() {
	var tEmail = $('#email').val();
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (!re.test(tEmail)) {
		$('#email').css('border-color', 'red');
		return false;
	}

	$('#email').css('border-color', 'grey');
	return true;
};

function testPassword() {
	if ($('#password').val() == "") {
		$('#password').css('border-color', 'red');
	} else {
		$('#password').css('border-color', 'grey');
	}
};

function tryLogin() {
	if (!testEmail()) {
		$('#email').val() = "";
		$('#password').val() = "";
	} else {
		var tEmail = $('#email').val();
		var tPassword = $('#password').val();
		var tRememberMe = $('#rememberMe').val();
		$.ajax({
			url : 'login',
			type : 'POST',
			data : {
				'action' : 'login',
				'email' : tEmail,
				'password' : tPassword,
				'rememberMe' : tRememberMe
			},
			dataType : 'json',
			success : function(data) {
				if (data == "ready") {
					window.location.replace("http://localhost:8888/");
				} else {
					$('#email').val("");
					$('#password').val("");
					alert(data);
				}
			},
			error : function(data) {
				alert("Test internet connection!");
			}
		});
	}
};