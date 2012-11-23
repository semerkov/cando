<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="CSS\mycss.css" />

<!-- JQuery Framework -->
	  	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>

<title>CanDO - Log in</title>
</head>
<body>
<div class="container">
  <div class="header"><jsp:include page="header.jsp" flush="true" /></div>
  <div class="content">
  	<div style="margin-left:80px;" >
      <form id="login_form" name="login_form" method="post" action="">
      <label> Login:</label>
      <br/>
      <input type="text" name="login_login" style="width:640px; height:50px; background-color: #FAFFBD;font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <label> Password:</label>
      <br/>
      <input type="password" name="login_password" style="width:640px; height:50px;background-color: #FAFFBD; font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <input name="login_submit" type="button" tabIndex="4"; style="font-size: 3em; border-radius:10px; margin:15px;" value="Enter" onclick="alert('Data won`t validate. Its a gag'); self.location='home.jsp';"/>
      <br/>
      <span>You do not have an account? <a href="register.jsp" style="font-style:oblique">Sign up here!</a></span>
    </form>
    </div>
  </div>
</div>
</body>
</html>