<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page
        import="com.appspot.i_can_do.master.security.User" %>

<%
    User user = (User) request.getAttribute("user");
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
    <div id="contactListContainer" class=" ui-widget-content">
        <div id="personalInformation">
            <div id="avatarArea">
                <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                    <%
                        if (user.getProfile().getImageFile() != null) {
                    %>
                    <img src="/calendar?type=showAvatar" width="60" height="60" alt="your photo"
                         onclick="seeMyProfile();" style="cursor: pointer;"/>
                    <%
                    } else {
                    %>
                    <img src="IMG/avatar.jpg" width="60" height="60"
                         alt="your photo" onclick="seeMyProfile();" style="cursor: pointer;"/>
                    <%
                        }
                    %>
                </div>
                <span style="font-size: 120%; cursor: pointer;"
                      onclick="seeMyProfile();"><%=user.getName() + " " + user.getSername()%></span>
            </div>
        </div>
    </div>
</div>
</body>
</html>