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
	
	$('#repassword').bind('focusout', function() {
		testRePassword();
	});
	
	$('#name').bind('focusout', function() {
		testName(this);
	});
	$('#surname').bind('focusout', function() {
		testName(this);
	});

	$('#register_button').click(function(e) {
		tryRegister();
		e.stopPropagation();
	});
    $('#login_form').keypress(function(e) {
        if(e.which==13)
             tryRegister();
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
	var b =false;
	var tEmail = $('#email').val();
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (!re.test(tEmail)) {
		$('#email').css('border-color', 'red');
		return b;
	}
	else{
		$.ajax({
			url : 'register',
			type : 'POST',
			async : false,
			data : {
				'action' : 'testEmail',
				'email' : $('#email').val()
			},
			dataType : 'json',
			success : function(data) {
				if (data == "free") {
					$('#email').css('border-color', 'green');
					b = true;
				} else {
					$('#email').css('border-color', 'red');
				}
			},
			error : function(data) {

			}
		});
	}
	return b;
}

function testName(element){
	var name = $(element);
	if(name.val()==''){
		name.css('border-color', 'red');
		return false;
	}
	else{
		name.css('border-color', 'grey');
		return true;
	}
}

function testPassword() {
	if ($('#password').val() == "") {
		$('#password').css('border-color', 'red');
		$('#repassword').css('border-color', 'red');
		return false;
	} else {
		$('#password').css('border-color', 'grey');
		$('#repassword').css('border-color', 'grey');
		return true;
	}
}

function testRePassword() {
	if (($('#password').val() !=$('#repassword').val())||($('#repassword').val() == "")){
		$('#repassword').css('border-color', 'red');
		return false;
	}else{
		$('#repassword').css('border-color', 'grey');
		return true;
	}
}

function tryRegister() {
    $('#password').css('border-color', 'grey');
    $('#email').css('border-color', 'grey');
    $('#email').css('border-color', 'grey');
    $('#password').css('border-color', 'grey');
    $('#repassword').css('border-color', 'grey');
    var name = $('#name');
    name.css('border-color', 'grey');
    var surname = $('#surname');
    surname.css('border-color', 'grey');

	if (testEmail()&testPassword()&testRePassword()&testName(name)&testName(surname)){
		
		var tEmail = $('#email').val();
		var tPassword = $('#password').val();
		alert('Check confirm');
		$.ajax({
			url : 'register',
			type : 'POST',
			data : {
				'action' : 'register',
				'email' : tEmail,
				'password' : tPassword,
				'name' : name.val(),
				'surname': surname.val()
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
				alert('Register error');
			}
		});
	}
}