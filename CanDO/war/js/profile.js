$(document).ready(function(e) {
	$( "button" ).button();
	$( ".ui-draggable" ).draggable();
	
	var address_count = parseInt($('#address_count').text());
	var address_curr = parseInt($('#address_curr').text());
	showAddressNav(address_curr);
	
	function showAddressNav(current){
		if(address_count == 1 || current > address_count || current < 1) return;
		if(current == 1){
			$('#nextAddress').css('display', 'block');
			$('#prevAddress').css('display', 'none');
		} else if(current == address_count){
			$('#nextAddress').css('display', 'none');
			$('#prevAddress').css('display', 'block');
		}else{
			$('#nextAddress').css('display', 'block');
			$('#prevAddress').css('display', 'block');
		}
	}
	
	$('#nextAddress').click(function(e){
		var currAddress = $('.address.active');
		currAddress.removeClass('active');
		currAddress.next('.address').addClass('active');
		address_curr++;
		showAddressNav(address_curr);
		e.stopPropagation();
	});
	$('#prevAddress').click(function(e){
		var currAddress = $('.address.active');
		currAddress.removeClass('active');
		currAddress.prev('.address').addClass('active');
		address_curr--;
		showAddressNav(address_curr);
		e.stopPropagation();
	});
	
	var email_count = parseInt($('#email_count').text());
	var email_curr = parseInt($('#email_curr').text());
	var emailVisible = 3;
	showEmailNav(email_curr);
	
	function showEmailNav(current){
		if(email_count <=emailVisible || current > email_count || current < 1) return;
		if(current == 1 ){
			$('#nextEmail').css('display', 'block');
			$('#prevEmail').css('display', 'none');
		} else if(current + emailVisible > email_count){
			$('#nextEmail').css('display', 'none');
			$('#prevEmail').css('display', 'block');
		}else{
			$('#nextEmail').css('display', 'block');
			$('#prevEmail').css('display', 'block');
		}
	}
	
	$('#nextEmail').click(function(e){
		var firstEmail = $('.email.active').first();
		firstEmail.removeClass('active')
		var lastEmail = $('.email.active').last();
		lastEmail.addClass('active');
		lastEmail.next('.email').addClass('active');
		email_curr++;
		showEmailNav(email_curr);
		e.stopPropagation();
	});
	$('#prevEmail').click(function(e){
		var firstEmail = $('.email.active').first();
		firstEmail.prev('.email').addClass('active')
		var lastEmail = $('.email.active').last();
		lastEmail.removeClass('active');
		email_curr--;
		showEmailNav(email_curr);
		e.stopPropagation();
	});
	
	var phone_count = parseInt($('#phone_count').text());
	var phone_curr = parseInt($('#phone_curr').text());
	var phoneVisible = 3;
	showPhoneNav(email_curr);
	
	function showPhoneNav(current){
		if(phone_count <= phoneVisible || current > email_count || current < 1) return;
		if(current == 1 ){
			$('#nextPhone').css('display', 'block');
			$('#prevPhone').css('display', 'none');
		} else if(current + phoneVisible > phone_count){
			$('#nextPhone').css('display', 'none');
			$('#prevPhone').css('display', 'block');
		}else{
			$('#nextPhone').css('display', 'block');
			$('#prevPhone').css('display', 'block');
		}
	}
	
	$('#nextPhone').click(function(e){
		var firstPhone = $('.phone.active').first();
		firstPhone.removeClass('active')
		var lastPhone = $('.phone.active').last();
		lastPhone.addClass('active');
		lastPhone.next('.phone').addClass('active');
		phone_curr++;
		showPhoneNav(phone_curr);
		e.stopPropagation();
	});
	$('#prevPhone').click(function(e){
		var firstPhone = $('.phone.active').first();
		firstPhone.prev('.phone').addClass('active')
		var lastPhone = $('.phone.active').last();
		lastPhone.removeClass('active');
		phone_curr--;
		showPhoneNav(phone_curr);
		e.stopPropagation();
	});
	
	$(window).resize(function(){
		center('profile_form');
	});
	$('.edit').parent('div').mouseenter(function(){
		$(this).find('.edit').css('display', 'block');
	}).mouseleave(function(){
		$(this).find('.edit').css('display', 'none');
	});
	
	$('.edit').click(
			function(e) {
				var t = $(this);
                setClickedItem(t.parent());
				showPopupDialog('editPopup',
						t.offset().top + t.height(), t.offset().left);
                e.stopPropagation();
			});
	
	$(window).resize();

	$('#profileAddAddressButton').click(function(e){
		var name = $('#newAddressText').val();
		if (name == "") {
			$('#newAddressText').css('border-color', 'red');
		} else {
			$('#newAddressText').css('border-color', 'green');
			$.ajax({
				url : 'profile',
				type : 'POST',
				data : {
					'action' : 'addAddress',
					'address' : name,
					'addressTypeCode' : $('#newAddressType :selected').val()
				},
				success : function(data) {
					location.reload();
				},
				error : function(data) {
					alert("Error add new address")
				}
			});
		}
		e.stopPropagation();
	});
	
	$('#profileAddEmailButton').click(function(e){
		var name = $('#newEmailText').val();
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if (!re.test(name)) {
			$('#newEmailText').css('border-color', 'red');
		} else {
			$('#newEmailText').css('border-color', 'green');
			$.ajax({
				url : 'profile',
				type : 'POST',
				data : {
					'action' : 'addEmail',
					'email' : name,
					'emailTypeCode' : $('#newEmailType :selected').val()
				},
				success : function(data) {
					location.reload();
				},
				error : function(data) {
					alert("Error add new email")
				}
			});
		}
		e.stopPropagation();
	});
	
	$('#profileAddPhoneButton').click(function(e){
		var name = $('#newPhoneText').val();
		if (name == "") {
			$('#newPhoneText').css('border-color', 'red');
		} else {
			$('#newPhoneText').css('border-color', 'green');
			$.ajax({
				url : 'profile',
				type : 'POST',
				data : {
					'action' : 'addPhone',
					'phone' : name,
					'phoneTypeCode' : $('#newPhoneType :selected').val()
				},
				success : function(data) {
					location.reload();
				},
				error : function(data) {
					alert("Error add new phone")
				}
			});
		}
		e.stopPropagation();
	});

    $('#picture').on("change", function(event){
       $('#imageUploadForm').submit();
    });
});


function addNewAddress(){
	showPopupDialog('profileAddAddress');
	center('profileAddAddress');
}
function addNewEmail(){
	showPopupDialog('profileAddEmail');
	center('profileAddEmail');
}
function addNewPhone(){
	showPopupDialog('profileAddPhone');
	center('profileAddPhone');
}
function remove(){
    $.ajax({
        url : 'profile',
        type : 'POST',
        data : {
            'action' : 'remove',
            'type' : getClickedItemType(),
            'key' : getClickedItemKey()
        },
        success : function() {
            location.reload();
        },
        error : function() {
            alert("Error while removing!")
        }
    });
}

function edit(){
    $.ajax({
        url : 'profile',
        type : 'POST',
        data : {
            'action' : 'edit',
            'type' : getClickedItemType(),
            'key' : getClickedItemKey()
        },
        success : function(data) {
            var container = "#editContainer";
            $(container).html(data);

            hidePopupDialog();
            showPopupDialog('editContainer');
            center('editContainer');

            $( container + " button" ).button();
            $( container + ".ui-draggable" ).draggable();

            $('button.save').click(function(e){
                var form = $(this).parent('div').parent('div');
                var text = form.find('.saveItemText').val();
                if(checkForEmpty(form.find('.saveItemText'))){
                    saveItem(form.find('.type').text(),form.find('.key').text(),form.find('.editSelect :selected').val(),text);
                }
                e.stopPropagation();
            });
        },
        error : function() {
            alert("Error while editing!")
        }
    });
}

function saveItem(type, key, typeCode, text ){
    $.ajax({
        url : 'profile',
        type : 'POST',
        data : {
            'action' : 'save',
            'type' : type,
            'typeCode' : typeCode,
            'text' : text,
            'key' : key
        },
        success : function(data) {
            location.reload();
        },
        error : function(data) {
            alert("Error save" + type);
        }
    });
}
function saveProfile(){
    if(checkForEmpty($('#aboutText')) & checkForEmpty($('#firstName')) & checkForEmpty($('#lastName'))){
        $.ajax({
            url : 'profile',
            type : 'POST',
            data : {
                'action' : 'saveProfile',
                'name' : $('#firstName').val(),
                'sername' : $('#lastName').val(),
                'about' : $('#aboutText').val()
            },
            success : function(data) {
                seeMyCalendar();
            },
            error : function(data) {
                alert("Error save profile");
            }
        });
    }
}
var clickedItem = null;
function setClickedItem(item){
        if(clickedItem != null){
            clickedItem.removeClass('clicked');
        }
        clickedItem = $(item).addClass('clicked');
}
function getClickedItemType(){
    var type = "none";
    if(clickedItem.hasClass('address')){
        type = 'address';
    }else if(clickedItem.hasClass('email')){
        type =  'email';
    }else if(clickedItem.hasClass('phone')){
        type = 'phone';
    }
    return type;
}
function getClickedItemKey(){
    return $(clickedItem).find('.key').text();
}
function checkForEmpty(input){
    if (input.val() == "") {
        input.css('border-color', 'red');
        return false;
    } else {
        input.css('border-color', 'green');
        return true;
    }
}

var showPopupDialog = function(dialogId, topPosition, leftPosition) {
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
    var mask = $('#popupMask');
	mask.css({
		'width' : maskWidth,
		'height' : maskHeight
	});
	mask.css('display', 'block');

    var dialog = $("#" + dialogId);
    dialog.css('top', topPosition);
    dialog.css('left', leftPosition);
    dialog.toggleClass('popupDialog', true);

    dialog.fadeIn(200);
};

var hidePopupDialog = function() {
	$('#popupMask').css('display', 'none');
	var pD = $(".popupDialog");
	if (pD != null) {
		pD.fadeOut(200);
		pD.toggleClass('popupDialog', false);
	}
};
function center(id){
    var form = $('#'+id);
    form.css({
		position:'absolute',
		left: ($(window).outerWidth() - form.outerWidth())/2,
		top: ($(window).outerHeight() -form.outerHeight())/2
	});
}

function seeMyCalendar(){
    document.location.href = "/";
}