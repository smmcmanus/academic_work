<?php
	session_start();
	if(!isset($_SESSION['login_user'])){
		header("location: index.php");
	}
	set_error_handler("warning_handler", E_WARNING);
	function warning_handler($errno, $errstr) { 
		echo "File or User not found<br>";
	}
	$username = $_SESSION['login_user'];
	if(!preg_match('/^[\w_\-]+$/', $username) ){
		echo "Invalid username<br>";
		echo "<a href='home.php'>Home</a>";
		exit;
	}
	$filename = $_POST['filenameShare'];
	if(!preg_match('/^[\w_\.\-]+$/', $filename) ){
		echo "Invalid filename";
		echo "<a href='home.php'>Home</a>";
		exit;
	}
	$share_to = $_POST['usershare'];
	if(!preg_match('/^[\w_\.\-]+$/', $share_to) ){
		echo "Invalid other user<br>";
		echo "<a href='home.php'>Home</a>";
		exit;
	}
	$full_path = sprintf("/srv/uploads/%s/%s", $username, $filename);
	$share_path = sprintf("/srv/uploads/%s/%s", $share_to, $filename);
	copy($full_path, $share_path);
	if(copy($full_path, $share_path)){
		header("location: shareSuccess.html");
	} else {
		header("location: shareFailure.html");
	}
?>