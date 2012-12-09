<%@ page import="com.appspot.i_can_do.service.utils.ServletUtils" %>
<%@ page import="com.appspot.i_can_do.master.model.Address" %>
<%@ page import="com.appspot.i_can_do.master.model.Phone" %>
<%@ page import="com.appspot.i_can_do.master.model.Email" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final Address address = (Address) request
            .getAttribute("address");
    final Email email = (Email) request
            .getAttribute("email");
    final Phone phone = (Phone) request
            .getAttribute("phone");
%>
<%
    if (address != null) {
%>
<div class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
     style="z-index:101; display: block;">
    <div
            class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
        <span class="headerText">Edit address</span>

        <div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
                      onClick="hidePopupDialog();">close</span>
        </div>
    </div>
    <div class="addForm">
        <select class="ui-corner-all editSelect"
                style="float:left; width:30%;margin: 4px;"><%=ServletUtils.addressEmailOptions(address.getType()) %>
        </select>
        <input type="text" name="saveItemText" class="saveItemText"
               style="width: 65%;" placeholder="Write address" value="<%=address.getAddress()%>"/>
        <button class="save">Save</button>
    </div>
    <div class="key"><%=KeyFactory.keyToString(address.getKey())%></div>
    <div class="type">address</div>
</div>
<%
} else if (email != null) {
%>
<div class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
     style="z-index:101; display: block;">
    <div
            class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
        <span class="headerText">Edit email</span>

        <div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
                      onClick="hidePopupDialog();">close</span>
        </div>
    </div>
    <div class="addForm">
        <select class="ui-corner-all editSelect"
                style="float:left; width:30%;margin: 4px;"><%=ServletUtils.addressEmailOptions(email.getType()) %>
        </select>
        <input name="saveItemText" class="saveItemText"
               style="width: 65%;" placeholder="Write email" value="<%=email.getEmail()%>"/>
        <button class="save">Save</button>
    </div>
    <div class="key"><%=KeyFactory.keyToString(email.getKey())%></div>
    <div class="type">email</div>
</div>

<%
} else if (phone != null) {
%>

<div class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
     style="z-index:101; display: block;">
    <div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
        <span class="headerText">Edit phone number</span>

        <div class="form_title ui-dialog-titlebar-close">
				<span class="ui-icon ui-icon-closethick ui-corner-all"
                      onClick="hidePopupDialog();">close</span>
        </div>
    </div>
    <div class="addForm">
        <select class="ui-corner-all editSelect"
                style="float:left; width:30%;margin: 4px;"><%=ServletUtils.phoneOptions(phone.getType()) %>
        </select>
        <input name="saveItemText" class="saveItemText"
               style="width: 65%;" placeholder="Write phone number" value="<%=phone.getPhoneNumber()%>"/>
        <button class="save">Save</button>
    </div>
    <div class="key"><%=KeyFactory.keyToString(phone.getKey())%></div>
    <div class="type">phone</div>
</div>

<% } %>
