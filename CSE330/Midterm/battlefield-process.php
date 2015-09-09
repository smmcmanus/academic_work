<?php
	if (!isset($_POST['ammo'])) {
		echo "Ammo not entered";
		exit;
	}
	if (!isset($_POST['soldiers'])) {
		echo "Soldiers not entered";
		exit;
	}
	if (!isset($_POST['duration'])) {
		echo "Duration not entered";
		exit;
	}
	if (!isset($_POST['critique'])) {
		echo "Critique not entered";
		exit;
	}
	$ammo = (int) $_POST['ammo'];
	$soldiers = (int) $_POST['soldiers'];
	$duration = (double) $_POST['duration'];
	$critique = $_POST['critique'];
	
	$mysqli = new MYSQLI('localhost', 'root', '#WashUBears#', 'battlefield');
 
	if($mysqli->connect_errno) {
		printf("Connection Failed: %s\n", $mysqli->connect_error);
		exit;
	}
	
	$stmt = $mysqli->prepare("INSERT INTO reports (ammunition, soldiers, duration, critique) VALUES (?, ?, ?, ?)");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('iids', $ammo, $soldiers, $duration, $critique);
	if($stmt->execute()){
		header("location: battlefield-submit.html");	
	}
	else{
		echo "SQL Query Failed";
		exit;
	}
	$stmt->close();
 
?>