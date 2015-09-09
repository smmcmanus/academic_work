<?php
	// Content of database.php
 
	$mysqli = new MYSQLI('localhost', 'ucal', 'calpass', 'caldb');
 
	if($mysqli->connect_errno) {
		printf("Connection Failed: %s\n", $mysqli->connect_error);
		exit;
	}
?>