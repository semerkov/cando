$(document).ready(function() {
	$('button').button();
	$(".ui-draggable").draggable();
	
	$('#groupsArea ul li').hover(
			  function () {
				  $(this).children('.removeGroupType').css('display','block');
			  }, 
			  function () {
				  $(this).children('.removeGroupType').css('display','none');
			  }
			);
	$('.removeGroupType').click(function(e) {
		var key = $(this).next('.groupKey').text();
		$.ajax({
			url : 'contactList',
			type : 'POST',
			data : {
				'action' : 'removeGroup',
				'groupKey' : key
			},
			success : function(data) {
				location.reload();
			},
			error : function(data) {
				alert("Error remove group")
			}
		});
		e.stopPropagation();
	});
	
	$('#createGroup').click(function(e) {
		showPopupDialog('addNewGroupForm');
		center('addNewGroupForm');
		e.stopPropagation();
	});

	$('#contactListAddGroup').click(function(e) {
		var name = $('#newGroupText').val();
		if (name == "") {
			$('#newGroupText').css('border-color', 'red');
		} else {
			$('#newGroupText').css('border-color', 'green');
			$.ajax({
				url : 'contactList',
				type : 'POST',
				data : {
					'action' : 'addGroup',
					'name' : name
				},
				success : function(data) {
					$('#newGroupText').css('border-color', 'white');
					location.reload();
				},
				error : function(data) {
					alert("Error add new group")
				}
			});
		}
		e.stopPropagation();
	});
	
	$('.addContact').click(function(e) {
		var key = $(this).next('.contactKey').text();
		$.ajax({
			url : 'contactList',
			type : 'POST',
			data : {
				'action' : 'addContact',
				'contactKey' : key
			},
			success : function(data) {
				location.reload();
			},
			error : function(data) {
				alert("Error add new contact")
			}
		});
		e.stopPropagation();
	});
	
	$('.removeContact').click(function(e) {
		var key = $(this).next('.contactKey').text();
		$.ajax({
			url : 'contactList',
			type : 'POST',
			data : {
				'action' : 'removeContact',
				'contactKey' : key
			},
			success : function(data) {
				location.reload();
			},
			error : function(data) {
				alert("Error remove contact")
			}
		});
		e.stopPropagation();
	});
	
	var currentContact = "";
	
	$('.addoToGroup').click(function(e) {
		showPopupDialog('addGroupToContact');
		center('addGroupToContact');
		currentContact = $(this).prev('.contactKey').text();
		e.stopPropagation();
	});
	
	$('#contactListAddGroupToContact').click(function(e) {	
		var groupKey = $("select option:selected").val();
		$.ajax({
			url : 'contactList',
			type : 'POST',
			data : {
				'action' : 'addToGroup',
				'contactKey' : currentContact,
				"groupKey" : groupKey
			},
			success : function(data) {
				location.reload();
			},
			error : function(data) {
				alert("Error remove contact")
			}
		});
		e.stopPropagation();
	});
	
	var fullContactListContainerText = $('#otherContactsSpan').text();
	$('#searchContact').keyup(function(e){
		var searchContactText = $('#searchContact').val();
		
		$('.contact .name').each(function(index,object){
			var o = $(object);
			var expr = new RegExp(searchContactText, 'ig');
			if(expr.test(o.text())){
				o.closest('div.contact').css('display','block');
			}else{
				o.closest('div.contact').css('display','none');
			}
		});
		
		var foundCount  = 0;
		$('#myContactListContainer .contact').each(function(index,object){
			if($(object).css('display') == 'block'){
				foundCount++;
			}
		});
		
		if(foundCount != 0){
			$('#contactCount').text('You have '+foundCount+' contacts');
		}else{
			$('#contactCount').text('Contacts not found!');
		}
		
		
		var text = "";
		$('#fullContactListContainer .contact').each(function(index,object){
			if($(object).css('display') == 'block'){
				text = fullContactListContainerText; 
				return;
			}
		});
		$('#otherContactsSpan').text(text);

		
		e.stopPropagation();
	});

});

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
function center(id) {
	var form = $('#' + id);
	form.css({
		position : 'absolute',
		left : ($(window).outerWidth() - form.outerWidth()) / 2,
		top : ($(window).outerHeight() - form.outerHeight()) / 2
	});
}

function seeMyProfile() {
	document.location.href = "/profile";
}
function seeMyCalendar() {
	document.location.href = "/calendar";
}