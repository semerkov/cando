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
                       <li>Friends</li>
                       <li>Work</li>
                   </ul>
                </div>
                <div style="text-align: center; border-top: 1px solid #d3d3d3;">
                    <span id="createGroup">Create group</span>
                </div>
            </div>
        </div>


        <div style="margin-top: 5px;" id="contactArea" class="ui-corner-all">
            <span id="contactCount">You have 4 contacts</span>

            <div id="myContactListContainer">

                <div class="contact">
                    <div class="contactControl">
                        <span>Remove</span>
                        <span>Add to group</span>
                    </div>
                    <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                        <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
                             onclick="seeMyProfile();" style="cursor: pointer;"/>
                    </div>
                    <table>
                        <tr><td onclick="seeMyProfile();" style="cursor: pointer;"><%=user.getName() +
                                " " + user.getSername()%></td></tr>
                        <tr><td style="font-size: 80%; cursor: pointer; color: darkSlateGray;"
                                onclick="seeMyProfile();">Friends<td></tr>
                    </table>

                </div>

                <div class="contact">
                    <div class="contactControl">
                        <span>Remove</span>
                        <span>Add to group</span>
                    </div>
                    <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                        <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
                             onclick="seeMyProfile();" style="cursor: pointer;"/>
                    </div>
                    <table>
                        <tr><td onclick="seeMyProfile();" style="cursor: pointer;"><%=user.getName() +
                                " " + user.getSername()%></td></tr>
                        <tr><td style="font-size: 80%; cursor: pointer; color: darkSlateGray;"
                                onclick="seeMyProfile();">Friends<td></tr>
                    </table>
                </div>


                <div class="contact">
                    <div class="contactControl">
                        <span>Remove</span>
                        <span>Add to group</span>
                    </div>
                    <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                        <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
                             onclick="seeMyProfile();" style="cursor: pointer;"/>
                    </div>
                    <table>
                        <tr><td onclick="seeMyProfile();" style="cursor: pointer;"><%=user.getName() +
                                " " + user.getSername()%></td></tr>
                        <tr><td style="font-size: 80%; cursor: pointer; color: darkSlateGray;"
                                onclick="seeMyProfile();">Work<td></tr>
                    </table>
                </div>
                <div class="contact">
                    <div class="contactControl">
                        <span>Remove</span>
                        <span>Add to group</span>
                    </div>
                    <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                        <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
                             onclick="seeMyProfile();" style="cursor: pointer;"/>
                    </div>
                    <table>
                        <tr><td onclick="seeMyProfile();" style="cursor: pointer;"><%=user.getName() +
                                " " + user.getSername()%></td></tr>
                        <tr><td style="font-size: 80%; cursor: pointer; color: darkSlateGray;"
                                onclick="seeMyProfile();"><td></tr>
                    </table>
                </div>


            </div>

            <span id="otherContactsSpan">Others contacts</span>

            <div id="fullContactListContainer">
                <div class="contact">
                    <div style="display: table-cell; vertical-align: middle;text-align: center; float: left; width: 60px;height: 60px; margin: 5px;">
                        <img src="/image?action=showAvatar" width="60" height="60" alt="your photo"
                             onclick="seeMyProfile();" style="cursor: pointer;"/>
                    </div>
                <span class="name"
                      onclick="seeMyProfile();"><%=user.getName() + " " + user.getSername()%></span>

                    <div class="contactControl">
                        <span>Add</span>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>