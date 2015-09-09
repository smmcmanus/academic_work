<?php
	session_start();
	if(!isset($_SESSION['login_user'])){
		header("location: index.php");
	}
	set_error_handler("warning_handler", E_WARNING);
	function warning_handler($errno, $errstr) { //Catches File not found warning
		echo "File not found<br>";
		echo '<br><a href="home.php">Home</a>';
		exit;
	}
	$username = $_SESSION['login_user'];
	if(!preg_match('/^[\w_\-]+$/', $username) ){ //filter input for special characters
		echo "Invalid username";
		echo '<br><a href="home.php">Home</a>';
		exit;
	}

	$filename = $_POST['_filename'];
	if(!preg_match('/^[\w_\.\-]+$/', $filename) ){
		echo "Invalid filename";
		echo '<br><a href="home.php">Home</a>';
		exit;
	}
        $full_path = sprintf("/srv/uploads/%s/%s", $username, $filename);
        if($_POST['action'] == "Delete"){
		unlink($full_path);
		echo "File Deleted<br>";
		echo '<br><a href="home.php">Home</a>';
		exit;
        }
?>