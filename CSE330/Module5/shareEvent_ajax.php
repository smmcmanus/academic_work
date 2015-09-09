<?php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 
	$name = $_POST['share'];
	$id = $_POST['id'];

	$stmt = $mysqli->prepare("SELECT title, day, month, year, time, userid FROM events WHERE id = ?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('i', $id);
	$stmt->execute();
	$stmt->store_result();
	$stmt->bind_result($title, $day, $month, $year, $time, $org);
	$count = 0;
	while($stmt->fetch()){	
		if($count==0){
			$title1 = $title; 
			$day1 = $day;
			$month1 = $month;
			$year1 = $year;
			$time1 = $time;
			$org1 = $org;
			$count++;
		}
	}
	$stmt->close();

	$stmt1 = $mysqli->prepare("SELECT id FROM users WHERE username = ?");
	if(!$stmt1){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt1->bind_param('s', $name);
	$stmt1->execute();
	$stmt1->store_result();
	$stmt1->bind_result($userid);
	$count1 = 0;
	while($stmt1->fetch()){	
		if($count1==0){
			$userid1 = $userid;
			$count1++;
		}
	}
	$stmt1->close();
	
	$stmt2 = $mysqli->prepare("INSERT INTO notifications (title, day, month, year, userid, time, org) VALUES (?, ?, ?, ?, ?, ?, ?)");
	if(!$stmt2){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt2->bind_param('siiiisi', $title1, $day1, $month1, $year1, $userid1, $time1, $org1);
	if($stmt2->execute()){
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