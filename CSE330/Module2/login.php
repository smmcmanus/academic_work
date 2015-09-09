<?php
	session_start();
	$error='';
	if (isset($_POST['submit'])) {
		if (empty($_POST['username'])) {
			$error = "Username is invalid";
		}
		else{
			$username=$_POST['username'];
			$file = '../users.txt';
			$handle = fopen($file, "r");
			//searches users.txt 
			if ($handle) {
    			while (($line = fgets($handle)) !== false) {
        			if(strcmp($line, $username) == 1){
        				$_SESSION['login_user'] = $username;
						header("location: home.php");
        			}
        		}
    			fclose($handle);
				$error = "Username is invalid";	
			} 
		
		}
	}
	
?>