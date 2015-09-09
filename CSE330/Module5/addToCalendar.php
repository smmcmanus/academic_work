<?php
	session_start();
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 
	$id = $_POST['eventid'];


	$stmt = $mysqli->prepare("SELECT title, day, month, year, time, userid FROM notifications WHERE id = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('i', $id);
	$stmt->execute();
	$stmt->store_result();
	$stmt->bind_result($title, $day, $month, $year, $time, $userid);
	$count = 0;
	while($stmt->fetch()){	
		if($count==0){
			$title1 = $title; 
			$day1 = $day;
			$month1 = $month;
			$year1 = $year;
			$time1 = $time;
			$userid1 = $userid;
			$count++;
		}
	}
	$stmt->close();
	
	$stmt2 = $mysqli->prepare("INSERT INTO events (title, day, month, year, userid, time) VALUES (?, ?, ?, ?, ?, ?)");
	if(!$stmt2){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt2->bind_param('siiiis', $title1, $day1, $month1, $year1, $userid1, $time1);
	if($stmt2->execute()){
		echo json_encode(array(
		"success" => true
		));
		exit;	
	}else{
		echo json_encode(array(
			"success" => false,
			"message" => "something went wrong."
		));
		exit;
	}
	$stmt2->close();

?>