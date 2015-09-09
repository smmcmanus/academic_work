<?php 
	session_start();
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
?>
<!DOCTYPE html>
<html>
	<head>
	<title>News Home</title>
	<link rel="stylesheet" type="text/css" href="news.css">
	</head>
	<body>
		<b id="welcome">Welcome: <?php echo htmlentities($_SESSION['login_user']) ?></b>
		<?php
 
			$stmt = $mysqli->prepare("select stories.title, stories.story_ID, stories.link, users.username, stories.vote, stories.submit_time from stories INNER JOIN users ON stories.user_id = users.user_id order by story_ID DESC");
			if(!$stmt){
				printf("Query Prep Failed: %s\n", $mysqli->error);
				exit;
			}
 
			$stmt->execute();
  			$stmt->store_result();
			$stmt->bind_result($name, $id, $link, $user, $vote, $timestamp);
			while($stmt->fetch()){
				printf("\t<h2>%s</h2> %s %s %s %s %s\n",
					htmlentities($name),
					"<br>Vote:<b>".htmlentities($vote)."</b>
					<form action = 'upvote.php' method = 'POST'>
					<input type='hidden' name = 'id' value = '".$id."'>
					<input type='hidden' name = 'vote' value = '".$vote."'>
					<input name='submit' type='submit' value=' Upvote '>
					</form><form action = 'downvote.php' method = 'POST'>
					<input type='hidden' name = 'id' value = '".$id."'>
					<input type='hidden' name = 'vote' value = '".$vote."'>
					<input name='submit' type='submit' value=' Downvote '>
					</form>",
					" Submitter: ".$user."",
					"Submitted: ".$timestamp."",
					"<form action = 'userProfile.php' method = 'POST'>
					<input type = 'hidden' name='user' value='".$user."'>
					<input name='submit' type='submit' value=' User Profile '>
					</form>",
					"<form action = 'articlePageL.php' method = 'POST'>
					<input type = 'hidden' name='storyid' value='".$id."'>
					<input type = 'hidden' name='title' value='".$name."'>
					<input type = 'hidden' name='submitter' value ='".$user."'>
					<input type = 'hidden' name='timestamp' value ='".$timestamp."'>
					<input name='submit' type='submit' value=' Article Page '>
					</form>",
					"<a href='".$link."'>External Link</a><br>"
				);
			}
			$stmt->close();
		?>
		<br>
		<b id='add'><a href="storyAdd.php">Add your own Article</a></b><br>
		<b id="logout"><a href="userlogout.php">Log Out</a></b>
	</body>
</html>