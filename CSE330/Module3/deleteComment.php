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
	$stmt = $mysqli->prepare("delete from comments where comment_id = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);			
		exit;
	}
	$stmt->bind_param('i', $comment);
	$stmt->execute();
	$stmt->close();

	header("location: userhome.php");
?>