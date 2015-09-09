<?php 
	session_start();
	require 'database.php';
	include('storySubmit.php');
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
?>
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
		<h1>Add story</h1>
		<form action="#" method="POST" id='add'>
			<label>Title :</label>
			<input id="title" name="title" placeholder="" type="text" required>
			<label>Full Link (ie: http://www.google.com):</label>
			<input id="link" name="link" placeholder="" type="url" required>
			<input type='hidden' name='token' value='<?php echo $_SESSION['token'];?>'>
			<br>
			<label>Article Text</label>
			<textarea id="content" name="content" form="add"></textarea>
			<input name="submit" type="submit" value=" Submit ">
		</form>
	</body>
</html>