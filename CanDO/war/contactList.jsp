<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page
        import="com.appspot.i_can_do.master.security.User,com.appspot.i_can_do.master.model.Group,com.appspot.i_can_do.master.model.GroupType,java.util.List,java.util.List,java.util.List,java.util.HashMap,java.util.List,java.util.ArrayList, com.google.appengine.api.datastore.KeyFactory" %>

<%
    User user = (User) request.getAttribute("user");
	List<User> newContacts = (List<User>) request.getAttribute("newContacts");
	HashMap<User, String> contacts = (HashMap<User, String>) request.getAttribute("contacts");
	List<GroupType> groupTypes = (List<GroupType>) request.getAttribute("groupTypes");	
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>CanDO - Contact List</title>
    <link href="CSS/jquery-ui-1.9.1.custom.min.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="CSS/contactList.css"/>

    <!-- JQuery Framework -->
    <script src="js/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery-ui-1.9.1.custom.min.js"></script>

    <script type="text/javascript" src="js/contactList.js"></script>


</head>
<body>
<div id="container">
    <div id="contactListForm" class=" ui-widget-content">
        <div id="personalInformation">
            <div id="controlArea">
                <img src="IMG/calendar.jpg" height="25" width="25" style="margin: 5px 10px; cursor: pointer;"
                     onclick="seeMyCalendar();"
                     alt="Go to calendar"/>
            </div>
            <div id="avatarArea">
                <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                    <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
                         onclick="seeMyProfile();" style="cursor: pointer;"/>
                </div>
                <table>
                    <tr><td onclick="seeMyProfile();" style="cursor: pointer;"><%=user.getName() +
                            " " + user.getSername()%></td></tr>
                    <tr><td style="font-size: 80%; cursor: pointer;" onclick="seeMyProfile();"><%=user.getEmail()%><td></tr>
                </table>
            </div>
        </div>

        <input type="text" name="searchContact" id="searchContact" class="ui-corner-all"
               placeholder="Start write contact name"/>

        <div id="groupsArea">
            <span id="groupsSpan">Groups</span>
            <div>
                <div>
                   <ul>
                       <li class="active">All</li>
                       <% for(GroupType g : groupTypes){%>
                       <li><%= g.getGroupName() %> 
                       <span class="removeGroupType ui-icon ui-icon-closethick ui-corner-all" style="float:right;">close</span>
                       <div class="groupKey"><%=KeyFactory.keyToString(g.getKey())%></div>
                       </li>
                       <%} %>
                   </ul>
                </div>
                <div style="text-align: center; border-top: 1px solid #d3d3d3;">
                    <span id="createGroup">Create group</span>
                </div>
            </div>
        </div>


        <div style="margin-top: 5px;" id="contactArea" class="ui-corner-all">
            <span id="contactCount">You have <%=contacts.size() %> contacts</span>

            <div id="myContactListContainer">

				<% for(User u : contacts.keySet()){
					String group = contacts.get(u);
					if(group == null) group = "";
					%>
		            <div class="contact">
	                    <div class="contactControl">
	                        <span class="removeContact">Remove</span>
	                        <div class="contactKey"><%=KeyFactory.keyToString(u.getKey())%></div>
	                        <span class="addoToGroup">Add to group</span>   
	                    </div>
	                    <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
	                        <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
	                             onclick="seeMyProfile();" style="cursor: pointer;"/>
	                    </div>
	                    <table>
	                        <tr><td onclick="seeMyProfile();" style="cursor: pointer;" class="name"><%=u.getName() +
	                                " " + u.getSername()%></td></tr>
	                        <tr><td style="font-size: 80%; cursor: pointer; color: darkSlateGray;"
	                                onclick="seeMyProfile();"><%= group %><td></tr>
	                    </table>
	
	                </div>
	            <%}%>

            </div>
			<% if(newContacts.size() > 0){ %>
	           <span id="otherContactsSpan">Contacts that You might know...</span>
			<% for(User u : newContacts){ %>
	            <div id="fullContactListContainer">
	                <div class="contact">
	                    <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
	                        <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
	                             onclick="seeMyProfile();" style="cursor: pointer;"/>
	                    </div>
	                <span class="name"
	                      onclick="seeMyProfile();"><%=u.getName() + " " + u.getSername()%></span>
	
	                    <div class="contactControl">
	                        <span class="addContact">Add</span>
	                        <div class="contactKey"><%=KeyFactory.keyToString(u.getKey())%></div>
	                    </div>
	                </div>
	            </div>
	           <%}%>
            <%}%>
        </div>

    </div>
</div>

<!-- Add new group form -->
<div id="addNewGroupForm" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
	<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			<span class="headerText">Add new group</span>
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
					onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addForm">
			<input type="text" name="newGroupText" id="newGroupText"
				style="width: 98%;" placeholder="Write group name" />
			<button name="contactListAddGroup" id="contactListAddGroup">Create</button>
		</div>
</div>

<!-- add some group to contact -->
<div id="addGroupToContact" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
	<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			<span class="headerText">Add group to contact</span>
			<div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
					onClick="hidePopupDialog();">close</span>
			</div>
		</div>
		<div class="addForm">
			<select id="contactListGroups"
				style="width: 98%;" <%=groupTypes.size() != 0 ? "" : "disabled"%> >
				<% for(GroupType g : groupTypes){%>
                       <option value="<%=KeyFactory.keyToString(g.getKey())%>"><%= g.getGroupName() %></option>
                <%} %>
				</select>
			<button name="contactListAddGroupToContact" id="contactListAddGroupToContact">Add</button>
		</div>
</div>



<div id="popupMask" class="popupMask" onClick="hidePopupDialog()"></div>

</body>
</html>