<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CanDO - Log in</title>

<link href="CSS/jquery-ui-1.9.1.custom.min.css" rel="stylesheet"
	type="text/css" />
<link href="CSS/profile.css" rel="stylesheet" type="text/css" />

<!-- JQuery Framework -->
<script src="js/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery-ui-1.9.1.custom.min.js"></script>

<script type="text/javascript" src="js/profile.js"></script>

<%@ page
	import="com.appspot.i_can_do.master.security.User, com.appspot.i_can_do.master.model.Profile,java.util.Calendar,java.util.List,java.util.ArrayList, com.appspot.i_can_do.master.model.Phone, com.appspot.i_can_do.master.model.Email, com.appspot.i_can_do.master.model.Address,
	com.appspot.i_can_do.master.security.AddressEmailType,com.appspot.i_can_do.master.security.PhoneType, com.google.appengine.api.datastore.KeyFactory, com.appspot.i_can_do.service.utils.ServletUtils"%>
<%
	User user = (User) request.getAttribute("user");
	Profile profile = user.getProfile();
	List<Address> addresses = profile.getAddresses();
	List<Email> emails = profile.getEmails();
	List<Phone> phones = profile.getPhones();
%>

</head>
<body>
	<div id="user_key" style="display: none;"><%=KeyFactory.keyToString(user.getKey())%></div>
	<div id="address_count" style="display: none;"><%=addresses.size()%></div>
	<div id="address_curr" style="display: none;"><%=addresses.size() > 0 ? 1 : -1%></div>
	<div id="email_count" style="display: none;"><%=emails.size()%></div>
	<div id="email_curr" style="display: none;"><%=emails.size() > 0 ? 1 : -1%></div>
	<div id="phone_count" style="display: none;"><%=phones.size()%></div>
	<div id="phone_curr" style="display: none;"><%=phones.size() > 0 ? 1 : -1%></div>
	<div id="profile_form"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div
			class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Your profile
			<div class="form_title"
				style="float: right; position: relative; top: 4px;">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
					style="float: right;" onclick="seeMyCalendar();">close</span> <span
					class="ui-icon ui-icon-check ui-corner-all"
					style="float: right; margin-right: 8px;"
					onClick="saveProfile();">save</span>
			</div>
		</div>
		<div id="container">

			<div class="side">
				<div id="image_container">
					Picture
					<div id="image" class="ui-corner-all">
                        <img src="/image?action=showAvatar" width="300" height="300"/>
					</div>
                    <form id="imageUploadForm" method="post" enctype="multipart/form-data" action="profile">
                        <input type="file" name="picture" id="picture"
                               accept="image/gif, image/jpeg, image/png" required="required"/>
                    </form>
				</div>
				<div id="about">
					<p>About:</p>
					<textarea id="aboutText" class="ui-widget-input ui-corner-all"
						style="margin: 6px 0; height: 90px;"><%=profile.getAbout()%></textarea>
				</div>
			</div>

			<div class="side">
				<div id="account_data">
					<table>
						<tr>
							<td>
								<p>First Name:</p> <input type="text" id="firstName"
								class="ui-widget-input ui-corner-all"
								value="<%=profile.getName()%>"/>
							</td>
							<td>
								<p>Last Name:</p> <input type="text" id="lastName"
								class="ui-widget-input ui-corner-all"
								value="<%=profile.getSername()%>">
							</td>
						</tr>
					</table>

					<table>
						<tr>
							<td><span class="ui-icon ui-icon-plus" style="float: right"
								onclick="addNewAddress();"></span>
								<p>Address list:</p>
								<div id="addressList" class="ui-widget-input ui-corner-all">
									<%
										for (int i = 0; i < addresses.size(); i++) {
											Address a = addresses.get(i);
									%>
									<div class="address <%if (i == 0) {%>active<%}%>">
										<div style="height: 30px; width: 95px; float: left;">
											<div class="type">
												<%=a.getType()%>
											</div>
										</div>
                                        <button class="edit">
                                            <span class="ui-icon ui-icon-pencil"></span>
                                        </button>
										<div class="text"><%=a.getAddress()%></div>
										<div class="key"><%=KeyFactory.keyToString(a.getKey())%></div>
									</div>
									<%
										}
									%>
									<div id="nextSidebar">
										<div id="nextAddress" class="ui-icon ui-icon-triangle-1-e"></div>
									</div>
									<div id="prevSidebar">
										<div id="prevAddress" class="ui-icon ui-icon-triangle-1-w"></div>
									</div>
								</div></td>
						</tr>

						<tr>
							<td><span class="ui-icon ui-icon-plus" style="float: right"
								onclick="addNewEmail();"></span>
								<p>Email list:</p>
								<div id="emailList" class="ui-widget-input ui-corner-all">

									<div id="prevEmail" class="ui-icon ui-icon-triangle-1-n"
										style="margin-left: 50%;"></div>

									<%
										for (int i = 0; i < emails.size(); i++) {
											Email e = emails.get(i);
									%>
									<div class="email <%if (i < 3) {%>active<%}%>">
										<div style="height: 25px; width: 80px; float: left;">
											<div class="type">
												<%=e.getType()%>
											</div>
										</div>
                                        <button class="edit">
                                            <span class="ui-icon ui-icon-pencil"></span>
                                        </button>
										<span class="text"><%=e.getEmail()%></span>
										<div class="key"><%=KeyFactory.keyToString(e.getKey())%></div>
									</div>
									<%
										}
									%>

									<div id="nextEmail" class="ui-icon ui-icon-triangle-1-s"
										style="margin-left: 50%;"></div>
								</div></td>
						</tr>

						<tr>
							<td><span class="ui-icon ui-icon-plus" style="float: right"
								onclick="addNewPhone();"></span>
								<p>Phone Number list:</p>
								<div id="phoneList" class="ui-widget-input ui-corner-all">
									<div id="prevPhone" class="ui-icon ui-icon-triangle-1-n"
										style="margin-left: 50%;"></div>

									<%
										for (int i = 0; i < phones.size(); i++) {
											Phone e = phones.get(i);
									%>
									<div class="phone <%if (i < 3) {%>active<%}%>">
										<div style="height: 25px; width: 80px; float: left;">
											<div class="type">
												<%=e.getType() %>
											</div>
										</div>
                                        <button class="edit">
                                            <span class="ui-icon ui-icon-pencil"></span>
                                        </button>
										<span class="text"><%=e.getPhoneNumber()%></span>
										<div class="key"><%=KeyFactory.keyToString(e.getKey())%></div>
									</div>
									<%
										}
									%>

									<div id="nextPhone" class="ui-icon ui-icon-triangle-1-s"
										style="margin-left: 50%;"></div>
								</div></td>
						</tr>
					</table>

				</div>
			</div>

		</div>

	</div>
	<div id="popupMask" class="popupMask" onclick="hidePopupDialog();"></div>

	<!-- add new address -->
	<div id="profileAddAddress"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div
			class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			<span class="headerText">Add new address</span>
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
					onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addForm">
		<select id="newAddressType" class="ui-corner-all" style="float:left; width:30%;margin: 4px;"><%=ServletUtils.addressEmailOptions(null) %></select>
			<input type="text" name="newAddressText" id="newAddressText"
				style="width: 65%;" placeholder="Write address" />
			<button name="profileAddAddressButton" id="profileAddAddressButton">Create</button>
		</div>
	</div>
	<!-- add new email -->
	<div id="profileAddEmail"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div
			class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			<span class="headerText">Add new email</span>
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
					onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addForm">
			<select id="newEmailType" class="ui-corner-all" style="float:left; width:30%;margin: 4px;"><%=ServletUtils.addressEmailOptions(null) %></select>
			<input type="text" name="newEmailText" id="newEmailText"
				style="width: 65%;" placeholder="Write email address" />
			<button name="profileAddEmailButton" id="profileAddEmailButton">Create</button>
		</div>
	</div>
	<!-- add new phone -->
	<div id="profileAddPhone"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div
			class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			<span class="headerText">Add new phone number</span>
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
					onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addForm">
			<select id="newPhoneType" class="ui-corner-all" style="float:left; width:30%;margin: 4px;"><%=ServletUtils.phoneOptions(null) %></select>
			<input type="text" name="newPhoneText" id="newPhoneText"
				style="width: 65%;" placeholder="Write phone number" />
			<button name="profileAddPhoneButton" id="profileAddPhoneButton">Create</button>
		</div>
	</div>
	<!-- Edit pop-up-->
	<div id="editPopup" class="ui-corner-all">
		<button onclick="edit()">Edit</button>
		<img src="IMG/close_icon.png"
			style="float: right; background-color: #FFF; cursor: pointer; top: -12px; right: 1px; position: absolute;"
			onclick="hidePopupDialog();" alt="Remove edit form"/>
		<button onclick="remove();">Remove</button>
	</div>

    <!-- #editContainer -->
    <div id="editContainer"></div>
    <!-- /#editContainer -->
</body>
</html>