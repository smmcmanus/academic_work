<?php 
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
	if (isset($_POST['submit'])) {
		if($_SESSION['token'] !== $_POST['token']){
			die("Request forgery detected");
		}
		$title = $_POST['title'];
		$link = $_POST['link'];
		$id = $_SESSION['id'];
		$content = $_POST['content'];
		$stmt = $mysqli->prepare("INSERT INTO stories (title, link, user_id, article_text) VALUES (?, ?, ?, ?)");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}
		$stmt->bind_param('ssis', $title, $link, $id, $content);
 
		if($stmt->execute()){
			header("location: userhome.php");	
		}
		else{
			printf("Query Prep Failed: %s\n", $mysqli->error);
			header("location: storyAdd.php");
		}
 
		$stmt->close();
	}
?>