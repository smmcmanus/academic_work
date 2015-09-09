<?php
	include('login.php');
	if(isset($_SESSION['login_user'])){
		header("location: home.php");
	}
	$_SESSION['add_status'] = "";
?>
<!DOCTYPE html>
<html>
	<head>
		<title>File Sharer Login</title>
		<link rel="stylesheet" type="text/css" href="indexStyle.css">
	</head>
	<body>
		<div id="main">
			<h1>File Sharer</h1>
			<div id="login">
				<h2>Login</h2>
				<form action="#" method="post">
					<label>UserName :</label>
					<input id="name" name="username" placeholder="username" type="text" autocomplete="off">
					<input name="submit" type="submit" value=" Login ">
					<span><?php echo $error; ?></span>
				</form>
			</div>
			<h2>Add New User</h2>
			<form action="userAdd.php" method="POST">
				<input type="text" placeholder="New Username" name="addition" autocomplete="off">
				<input type="submit">
				<span><?php echo $_SESSION['add_status']; ?></span>
			</form>
		</div>
	</body>
</html>