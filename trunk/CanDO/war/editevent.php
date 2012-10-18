<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CanDO - Edit event</title>
</head>
<body>
<div class="container">
  <div class="header"><?php include('header.php'); ?></div>
  <div class="content">
  	<div style="margin-left:80px;" >
      <form id="edit_form" name="edit_form" method="post" action="">
      <label> Title:</label>
      <br/>
      <input type="text" id="edit_form_title" style="width:640px; height:50px; background-color: #FAFFBD;font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <label> Description:</label>
      <br/>
      <input type="text" id="edit_form_desc" style="width:640px; height:50px;background-color: #FAFFBD; font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <label> Date:</label>
      <br/>
       <input type="text" id="edit_form_date" style="width:640px; height:50px;background-color: #FAFFBD; font-family:'MS Serif', 'New York', serif; font-size: 3em;" />
      <br/>
      <input name="edit_form_submit" type="button"  tabIndex="4"; style="font-size: 3em;  border-radius:10px; margin:15px;" value="OK!"/>
    </form>
    </div>
  </div>
  <!--<div class="footer"><?php include('footer.php'); ?>--></div>
</div>
</body>
</html>