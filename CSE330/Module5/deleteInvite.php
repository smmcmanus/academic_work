<?php
// newEvent_ajax.php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 
	$id = $_POST['eventid'];
	$stmt = $mysqli->prepare("DELETE from notifications where id = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('i', $id);
	if($stmt->execute()){
		echo json_encode(array(
			"success" => true,
			"id"=>$id
		));
		exit;	
	}else{
		echo json_encode(array(
			"success" => false,
			"message" => "Nope."
		));
		exit;
	}
	$stmt->close();
?>