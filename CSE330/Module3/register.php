<?php
	require 'database.php';
	include('addUser.php'); // Includes Login Script
	if(isset($_SESSION['login_user'])){
		header("location: userhome.php");
	}
?>
<!DOCTYPE html>
<html>
	<head>
	<link rel="stylesheet" type="text/css" href="news.css">
	</head>
	<body>
		<h1>Register here</h1>
		<form enctype="multipart/form-data" action="#" method="POST" id="add">
			<label>UserName :</label>
			<input id="name" name="username" placeholder="Username" type="text" autocomplete="off" required>
			<label>Password :</label>
			<input id="password" name="password" placeholder="Password" type="text" autocomplete="off" required>
			<label>Tell us a little bit about you: </label>
			<textarea id="about" name="about" type="text" form="add"></textarea>
			<p>
				<input type="hidden" name="picture" value="false">
				<input type="hidden" name="MAX_FILE_SIZE" value="20000000" />
				<label for="uploadfile_input">Choose a picture of yourself for your profile:</label> <input name="uploadedfile" type="file" id="picture" />
			</p>
			<input name="submit" type="submit" value=" Register ">
			<span><?php echo $addStatus; ?></span>
		</form>
	</body>
</html>