<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CanDO - Log in</title>

<link href="CSS/jquery-ui-1.9.1.custom.min.css" rel="stylesheet"
	type="text/css" />
<link href="CSS/login.css" rel="stylesheet"
	type="text/css" />

<!-- JQuery Framework -->
<script src="js/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery-ui-1.9.1.custom.min.js"></script>

<script type="text/javascript" src="js/login.js"></script>

</head>
<body>
	<div id="login_form" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
		<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-all">
			Login
			<div class="form_title ui-dialog-titlebar-close">
			<span class="ui-icon ui-icon-closethick ui-corner-all" onClick="hidePopupDialog();">close</span>
			</div>
		</div>   
		<label> Email:</label>
	    <br/>
	    <input type="text" name="email" id="email"/>
	    <br/>
	    <label> Password:</label>
	    <br/>
	    <input type="password" name="password" id="password" class="ui-corner-all"></input>
	    <br/>
	    <input type="checkbox" name="rememberMe" id="rememberMe" class="ui-corner-all" value="1"/>
	    <label> Keep me log in</label>
	    <br/>
	    <button id="login_button">Login</button>
	    <br/>
	    <span>You do not have an account? <a href="register.jsp" style="font-style:oblique">Sign up here!</a></span>
 </div>
</body>
</html>