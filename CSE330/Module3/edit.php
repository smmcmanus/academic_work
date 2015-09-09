<?php 
	//Editing for stories
	session_start();
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
	 if($_SESSION['token'] !== $_POST['token']){
 		die("Request forgery detected");
 	}
	$story = $_POST['storyid'];
	$newTitle = $_POST['newtitle'];
	$newLink = $_POST['newlink'];
	$newDescription = $_POST['newdesc'];
        $something = "hi";
	if($newTitle != ''){
		$something = "title";
		$whatever = $newTitle;
	}
	if($newLink != ''){
		$something = "link";
		$whatever = $newLink;
	}
	if($newDescription != ''){
		$something = "article_text";
		$whatever = $newDescription;
	}
        $stmt = $mysqli->prepare("update stories set $something = ? where story_id = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);			
		exit;
	}
	$stmt->bind_param('si', $whatever, $story);
	$stmt->execute();
	$stmt->close();
	header("location: userhome.php");
?>