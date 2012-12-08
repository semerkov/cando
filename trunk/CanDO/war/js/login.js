var context = "/";

$(document).ready(function(e) {
	$( "button" ).button();
	$( ".ui-draggable" ).draggable();
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
    $('#login_form').keypress(function(e) {
        if(e.which==13)
             tryLogin();
        e.stopPropagation();
    });
	
	$(window).resize(function(){
			$('#login_form').css({
				position:'absolute',
				left: ($(window).width() - $('#login_form').outerWidth())/2,
				top: ($(window).height() - $('#login_form').outerHeight())/2
			});
	});
	$(window).resize();
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
}

function testPassword() {
	if ($('#password').val() == "") {
		$('#password').css('border-color', 'red');
	} else {
		$('#password').css('border-color', 'grey');
	}
}

function tryLogin() {
    $('#password').css('border-color', 'grey');
    $('#email').css('border-color', 'grey');

	if (!testEmail()) {
		//$('#email').val("");
		$('#password').val("");
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
					document.location.href = context;
				} else {
					$('#email').val("");
					$('#password').val("");
					testEmail();
					testPassword();
				}
			},
			error : function(data) {
				alert("Test internet connection!");
			}
		});
	}
}