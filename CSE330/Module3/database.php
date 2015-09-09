<?php
	// Content of database.php
 
	$mysqli = new MYSQLI('localhost', 'unews', '#login2news', 'newsdb');
 
	if($mysqli->connect_errno) {
		printf("Connection Failed: %s\n", $mysqli->connect_error);
		exit;
	}
?>