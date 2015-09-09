<?php
	session_start();
	if(!isset($_SESSION['login_user'])){
		header("location: index.php");
	}
	set_error_handler("warning_handler", E_WARNING);
	function warning_handler($errno, $errstr) { 
		echo "File not found<br>";
		echo '<br><a href="home.php">Home</a>';
		exit;
	}
	$username = $_SESSION['login_user'];
	if(!preg_match('/^[\w_\-]+$/', $username) ){
		echo "Invalid username";
		echo '<br><a href="home.php">Home</a>';
		exit;
	}
	$filename = $_POST['filename'];
	if(!preg_match('/^[\w_\.\-]+$/', $filename) ){
		echo "Invalid filename";
		echo '<br><a href="home.php">Home</a>';
		exit;
	}
	
        $full_path = sprintf("/srv/uploads/%s/%s", $username, $filename);
		$finfo = new finfo(FILEINFO_MIME_TYPE);
		$mime = $finfo->file($full_path);
		if($mime == "application/pdf" || $mime == "text/plain" || $mime == "image/jpeg" || $mime == "image/png" || $mime == "text/html" || $mime == "image/gif"){
			header("Content-Type: ". $mime);
			readfile($full_path);
			exit;
        }
        else{
			header("Content-Type: ". $mime);
			header('Content-Description: File Transfer');
			header('Content-Disposition: attachment; filename='.basename($filename));
			header('Expires: 0');
			header('Cache-Control: must-revalidate');
			header('Pragma: public');
			readfile($full_path);
			exit;
		}
?>