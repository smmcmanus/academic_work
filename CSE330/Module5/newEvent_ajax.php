<?php
// newEvent_ajax.php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 
 
	$id = $_SESSION['id'];
	$title = $_POST['title'];
	$day = $_POST['day'];
	$month = $_POST['month'];
	$year = $_POST['year'];
	$time = $_POST['time'];

	if($_SESSION['token'] !== $_POST['token']){
		die("Request forgery detected");
	}
	$stmt = $mysqli->prepare("INSERT INTO events (title, day, month, year, userid, time) VALUES (?, ?, ?, ?, ?, ?)");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('siiiis', $title, $day, $month, $year, $id, $time);
	if($stmt->execute()){
		echo json_encode(array(
		"success" => true
		));
		exit;	
	}else{
		echo json_encode(array(
			"success" => false,
			"message" => "somthing went wrong."
		));
		exit;
	}
?>