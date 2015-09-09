<?php
	require 'database.php';
	include('userlogin.php'); // Includes Login Script
	if(isset($_SESSION['login_user'])){
		header("location: userhome.php");
	}
	//This is the homepage a not logged in user sees
?>
<!DOCTYPE html>
<html>
	<head>
	<title> News Site</title>
	<link rel="stylesheet" type="text/css" href="news.css">
	</head>
	<body>
		<form action="#" method="POST">
			<label>UserName :</label>
			<input id="name" name="username" placeholder="username" autocomplete="off" type="text">
			<label>Password :</label>
			<input id="password" name="password" autocomplete="off" placeholder="**********" type="password">
			<input name="submit" type="submit" value=" Login ">
			<span><?php echo $error; ?></span>
		</form> 
		<form action="register.php">
			<input type="submit" value="Register">	
		</form>
		<?php		
			$stmt = $mysqli->prepare("select stories.title, stories.story_ID, stories.link, users.username, stories.vote from stories INNER JOIN users ON stories.user_id = users.user_id order by story_ID DESC");
			if(!$stmt){
				printf("Query Prep Failed: %s\n", $mysqli->error);
				exit;
			}
 
			$stmt->execute();
  			$stmt->store_result();
			$stmt->bind_result($name, $id, $link, $user, $vote);
			while($stmt->fetch()){
				printf("\t<h2>%s</h2>%s %s %s \n",
					htmlspecialchars($name),
					" Submitter: ".$user."",
					"<br>Vote:<b>".htmlspecialchars($vote)."</b>",
					"<form action = 'articlePage.php' method = 'POST'>
					<input type = 'hidden' name='storyid' value='".$id."'>
					<input type = 'hidden' name='title' value='".$name."'>
					<input type = 'hidden' name='submitter' value ='".$user."'>
					<input name='submit' type='submit' value=' Article Page '>
					</form>"
				);
			}
			$stmt->close();
		?>
		<br>
	</body>
</html>	