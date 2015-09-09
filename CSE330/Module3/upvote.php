<?php 
	session_start();
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
	$id = $_POST['id'];
	$vote = $_POST['vote'];
	$vote = ((int)$vote) + 1;
	$stmt = $mysqli->prepare("update stories set vote = ? where story_ID = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);			
		exit;
	}
	$stmt->bind_param('ii', $vote, $id);
	$stmt->execute();
	$stmt->close();
	header("location: userhome.php");
?>