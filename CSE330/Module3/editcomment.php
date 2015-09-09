<?php 
	session_start();
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
	 if($_SESSION['token'] !== $_POST['token']){
 		die("Request forgery detected");
 	}
	$comment = $_POST['commentid'];
    $newcomment = $_POST['newcomment'];
	$stmt = $mysqli->prepare("update comments set content = ? where comment_id = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);			
		exit;
	}
	$stmt->bind_param('si', $newcomment, $comment);
	$stmt->execute();
	$stmt->close();
	header("location: userhome.php");
?>