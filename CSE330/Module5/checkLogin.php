<?php
	ini_set("session.cookie_httponly", 1);
	session_start();
	if(isset($_SESSION['username'])){
		echo 'true';
	}else{
		echo 'false';
	}
?>