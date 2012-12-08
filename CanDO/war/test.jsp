<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 09.12.12
  Time: 0:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="CSS/jquery-ui-1.9.1.custom.min.css" rel="stylesheet"
          type="text/css" />

    <!-- JQuery Framework -->
    <script src="js/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
    <style>
        #feedback { font-size: 1.4em; }
        #selectable .ui-selecting { background: #FECA40; }
        #selectable .ui-selected { background: #F39814; color: white; }
        #selectable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
        #selectable div { margin: 3px; padding: 0.4em; font-size: 1.4em; height: 18px; }
    </style>
    <script type="text/javascript">
        $(document).ready(function(e){
            $('#selectable').selectable();
        });
    </script>
</head>
<body>
  <div id="selectable">
      <div class="ui-widget-content">cmnsdf</div>
      <div class="ui-widget-content">df</div>
      <div class="ui-widget-content">s</div>
      <div class="ui-widget-content">dssfd</div>
      <div class="ui-widget-content">sfd</div>
      <div class="ui-widget-content">df</div>
      <div class="ui-widget-content">sd</div>
      <div class="ui-widget-content">d</div>
      <div class="ui-widget-content">fds</div>
      <div class="ui-widget-content">d</div>
  </div>
</body>
</html>
