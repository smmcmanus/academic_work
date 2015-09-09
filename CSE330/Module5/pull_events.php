<?php
// pull_events.php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 	if(isset($_SESSION['username'])){
		$id = $_SESSION['id'];
		$day = $_POST['day'];
		$month = $_POST['month'];
		$year = $_POST['year'];
 
 
		$stmt = $mysqli->prepare("SELECT title, id, time FROM events WHERE month=? and year=? and day=? and userid =?");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}
		$stmt->bind_param('iiii', $month, $year, $day, $id);
		$stmt->execute();
		$stmt->bind_result($title, $eid, $time);
		$return = array(array("login" => true));
		while($stmt->fetch()){
			array_push($return, array("title" => htmlentities($title),"id" => $eid, "time" => $time));
		}
		echo json_encode(
				$return
			);
		
	} else {
		$return = array(array("login" => false));
		echo json_encode($return);
	}
	
?>