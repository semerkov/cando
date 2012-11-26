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
<div class="container">
  <div class="header"><jsp:include page="header.jsp" flush="true" /></div>
  <div class="content">
  	<div style="margin-left:80px;" >
      <form id="login_form">
      <label> Login:</label>
      <br/>
      <input type="text" name="email" id="email"/>
      <br/>
      <label> Password:</label>
      <br/>
      <input type="password" name="password" id="password" class=""/>
      <br/>
      <label> Remember ME:</label>
      <input type="checkbox" name="rememberMe" id="rememberMe" value="0"/>
      <br/>
      <input type="button" id="login_button" tabIndex="4"; value="Enter"/>
      <br/>
      <span>You do not have an account? <a href="register.jsp" style="font-style:oblique">Sign up here!</a></span>
    </form>
    </div>
  </div>
</div>
</body>
</html>