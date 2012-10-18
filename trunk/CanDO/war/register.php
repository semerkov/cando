<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CanDO - Register</title>
</head>
<body>
<div class="container">
  <div class="header"><?php include('header.php'); ?></div>
  <div class="content">
  	<div style="margin-left:80px;" >
      <form id="register_form" name="login_form" method="post" action="">
      <p style="font-size:2em">All fields must be filled!</p>
      <label> Login:</label>
      <br/>
      <input name="register_login" type="text" style="width:640px; height:50px; background-color: #FAFFBD;font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <label> E-mail:</label>
      <br/>
      <input type="text" name="register_email" style="width:640px; height:50px; background-color: #FAFFBD;font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <label> Password:</label>
      <br/>
      <input type="password" name="register_password" style="width:640px; height:50px;background-color: #FAFFBD; font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <label> Repeat password:</label>
      <br/>
      <input type="password" name="register_repeat_password" style="width:640px; height:50px;background-color: #FAFFBD; font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <input type="checkbox" name="register_terms" value="" />
      <span>I have read <a href="terms.php" style="font-style:oblique">terms of use</a>!</span>
      <br/>
      <input name="register_submit" type="button" tabIndex="4"; style="font-size: 3em; border-radius:10px; margin:15px;" value="Register"/>
    </form>
    </div>
  </div>
  <!--<div class="footer"><?php include('footer.php'); ?>--></div>
</div>
</body>
</html>