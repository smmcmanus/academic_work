<?php
// login_ajax.php
	require 'caldb.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 
	$username = $_POST['username'];
	$password = $_POST['password'];
 
 
	$stmt = $mysqli->prepare("SELECT COUNT(*), cryptpass, id FROM users WHERE username=?");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('s', $username);
	$stmt->execute();
	$stmt->bind_result($cnt, $pwd_hash, $id);
	$stmt->fetch(); 
	if( $cnt == 1 && crypt($password, $pwd_hash)==$pwd_hash){
		ini_set("session.cookie_httponly", 1);
		session_start();
		$_SESSION['username'] = $username;
		$_SESSION['id'] = $id;
		$_SESSION['token'] = substr(md5(rand()), 0, 10);
 
		echo json_encode(array(
			"success" => true,
			"name" => $username,
			"token" => $_SESSION['token']
		));
		exit;
	}else{
		echo json_encode(array(
			"success" => false,
			"message" => "Incorrect Username or Password"
		));
		exit;
	}
?>