<?php
// pull_events.php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 	if(isset($_SESSION['username'])){
		$id = $_SESSION['id'];
 
		$stmt = $mysqli->prepare("SELECT users.username, notifications.title, notifications.day, notifications.month, notifications.year, notifications.time, notifications.id FROM users INNER JOIN notifications on users.id=notifications.org WHERE userid =?");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}
		$stmt->bind_param('i', $id);
		$stmt->execute();
		$stmt->bind_result($org, $title, $day, $month, $year, $time, $id);
		$return = array(array("login" => true));
		while($stmt->fetch()){
			array_push($return, array("org"=>$org, "title" => htmlentities($title), "day"=>htmlentities($day), "month"=>$month, "year"=>$year, "time" => htmlentities($time), "eventid"=>$id));
		}
		echo json_encode(
				$return
			);
		
	} else {
		$return = array(array("login" => false));
		echo json_encode($return);
	}
	
?>