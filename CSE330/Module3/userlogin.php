<?php
	// This is a *good* example of how you can implement password-based user authentication in your web application.
 	session_start();
 	$error = '';
 	require 'database.php';
 	if (isset($_POST['submit'])) {
		// Use a prepared statement
		$stmt = $mysqli->prepare("SELECT COUNT(*), username, crypt_pass, user_id FROM users WHERE username=?");
 
		// Bind the parameter
		$user = $_POST['username'];
		$stmt->bind_param('s', $user);
		$stmt->execute();
 
		// Bind the results
		$stmt->bind_result($cnt, $user_id, $pwd_hash, $id);
		$stmt->fetch();
 
		$pwd_guess = $_POST['password'];
		//Compare the submitted password to the actual password hash
		if( $cnt == 1 && crypt($pwd_guess, $pwd_hash)==$pwd_hash){
		// Login succeeded!
			$_SESSION['login_user'] = $user_id;
			$_SESSION['token'] = substr(md5(rand()), 0, 10);
			$_SESSION['id'] = $id;
			header("location: userhome.php");
		}else{
			$error = 'login failed';
			header("location: homepage.php");
		}
	}
?>