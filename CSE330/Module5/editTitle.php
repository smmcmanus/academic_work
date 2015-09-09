<?php
// newEvent_ajax.php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 
 
	$id = $_SESSION['id'];
	$title = $_POST['title'];
	$event_id = $_POST['id'];
	
	if($_SESSION['token'] !== $_POST['token']){
		die("Request forgery detected");
	}
	$stmt = $mysqli->prepare("UPDATE events set title = ? where id = ? and userid = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('sii', $title, $event_id, $id);
	if($stmt->execute()){
		echo json_encode(array(
			"success" => true
		));
		exit;	
	}else{
		echo json_encode(array(
			"success" => false,
			"message" => "You do not own this event."
		));
		exit;
	}
?>