<?php
	require 'database.php';
	$addStatus = '';
	if (isset($_POST['submit'])) {
	
		$bio = $_POST['about'];
		$user = $_POST['username'];
		$cpass = crypt($_POST['password']);
		$pic = basename($_FILES['uploadedfile']['name']);
		if(!preg_match('/^[\w_\.\-]+$/', $user) ){
			echo "Invalid username";
			echo "<br><a href='homepage.php'>Home</a>";
			exit;
		}
		if(!preg_match('/^[\w_\.\-]+$/', $pic) ){
			echo "Invalid picture";
			echo "<br><a href='homepage.php'>Home</a>";
			exit;
		}
		$full_path = sprintf("user_pictures/%s", $pic);
		$_SESSION['path'] = $full_path;
		if( move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $full_path) ){
			$upload_status = "File successfully uploaded";
		}else{
			$upload_status = "File upload unsuccessful";
		}
		$stmt = $mysqli->prepare("INSERT INTO users (username, crypt_pass, about, userpicture) VALUES (?, ?, ?, ?)");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}
		$full_path = sprintf("http://ec2-54-191-137-146.us-west-2.compute.amazonaws.com/~smcmanus/user_pictures/%s", $pic);
		$stmt->bind_param('ssss', $user, $cpass, $bio, $full_path);
 
		if($stmt->execute()){
			$addStatus = 'User added';
			header("location: homepage.php");	
		}
		else{
			$addStatus = 'Add failed';
		}
 
		$stmt->close();
	}
?>