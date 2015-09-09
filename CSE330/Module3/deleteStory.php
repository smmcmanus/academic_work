<?php 
	session_start();
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
	if($_SESSION['token'] !== $_POST['token']){
		die("Request forgery detected");
	}
	$story = $_POST['storyid'];
	$stmt0 = $mysqli->prepare("delete from comments where story_id = ?");
	if(!$stmt0){
		printf("Query Prep Failed: %s\n", $mysqli->error);			
		exit;
	}
	$stmt0->bind_param('i', $story);
	$stmt0->execute();
	$stmt0->close();
	$stmt = $mysqli->prepare("delete from stories where story_ID = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);			
		exit;
	}
	$stmt->bind_param('i', $story);
	$stmt->execute();
	$stmt->close();
	header("location: userhome.php");
?>