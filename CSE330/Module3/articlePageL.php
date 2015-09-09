<?php 
	session_start();
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
	if(isset($_POST['commenting'])){
		$stmt = $mysqli->prepare("insert into comments (content, user_id,story_id) values (?, ?, ?)");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);			
			exit;
		}
		$content = $_POST['comment'];

		$user_id = $_SESSION['id'];

		$story_id = $_POST['storyid'];

		$stmt->bind_param('sii', $content, $user_id, $story_id);
		$stmt->execute();
		$stmt->close();	
	}
?>
<!DOCTYPE html>
<html>
	<head>
	<title>Article Page</title>
	<link rel="stylesheet" type="text/css" href="news.css">
	</head>
	<body>
		<h1><a href='userhome.php'>Home</a></h1>
		<h2 id="Title"><?php echo htmlspecialchars($_POST['title']) ?></h2>
		<p>Submitter: <?php echo htmlentities($_POST['submitter']) ?></p>
		<p>Submitted: <?php echo htmlentities($_POST['timestamp']) ?></p>
		<?php
			$time = $_POST['timestamp'];
			$storyid = $_POST['storyid'];
			$token = $_SESSION['token'];
			$title = $_POST['title'];
			$submitter = $_POST['submitter'];
			//This selects the story information requested from the database
			$stmt = $mysqli->prepare("select link, user_id, article_text, vote from stories where story_ID = ?");
			if(!$stmt){
				printf("Query Prep Failed: %s\n", $mysqli->error);
				exit;
			}
			$stmt->bind_param('i', $storyid);
			$stmt->execute();
  			$stmt->store_result();
			$stmt->bind_result($link, $u_id, $content, $vote);
			while($stmt->fetch()){
				printf("\t%s %s<br>%s\n",
					"<br>Vote:<b>".htmlspecialchars($vote)."</b>",
					"<p><a href='".$link."'>Link</a><br></p>",
					"$content"
				);
				//if this is the logged in users post they can edit it or delete it
				if($_POST['submitter'] == $_SESSION['login_user']){
					printf("\t%s %s\n",
						"<form action = 'deleteStory.php' method = 'POST'>
							<input type = 'hidden' name='storyid' value='".$storyid."'>
							<input type='hidden' name='token' value='".$token."'>
							<input name='submit' type='submit' value=' Delete Story '>
						</form>",
						"<form action = 'edit.php' method = 'POST'>
							<input type = 'hidden' name='storyid' value='".$storyid."'>
							<input type='hidden' name='token' value='".$token."'>
							<input type = 'text' name = 'newtitle'> 
							<input name='submit' type='submit' value='Edit Title'><br>
							<input type = 'text' name = 'newlink'> 
							<input name='submit' type='submit' value='Edit Link'><br>
							<input type = 'text' name = 'newdesc'>
							<input name='submit' type='submit' value='Edit Description'>
						</form>"
					);	
				}
			}
			//this finds the comments for this story
			$stmt1 = $mysqli->prepare("SELECT comments.content, users.username, comments.comment_id FROM users INNER JOIN comments ON comments.user_id=users.user_id where story_id = ? order by comment_ID ASC");
			if(!$stmt1){
				printf("Query Prep Failed: %s\n", $mysqli->error);
				exit;
			}
			$stmt1->bind_param('i', $storyid);
			$stmt1->execute();
			$stmt1->bind_result($content, $user, $c_id);
			echo "<h3>Comments:</h3><br>";
			while($stmt1->fetch()){	
				printf("\t%s %s\n",
				htmlspecialchars($content),
				"  - ".$user."<br>");
				//if logged in users owns the comment, they can edit or delete
				if($user == $_SESSION['login_user']){
					printf("\t%s %s\n",
					"<form action = 'deleteComment.php' method = 'POST'>
						<input type = 'hidden' name='commentid' value='".$c_id."'>
						<input type='hidden' name='token' value='".$token."'>
						<input name='submit' type='submit' value=' Delete '>
					</form>",
					"<form action = 'editcomment.php' method = 'POST'>
						<input type = 'hidden' name='commentid' value='".$c_id."'>
						<input type='hidden' name='token' value='".$token."'>
						<input type='text' name='newcomment'>
						<input name='submit' type='submit' value=' Edit '>
					</form><br>"
					);
				}
			}
			printf("\t%s\n",
				"<form enctype='multipart/form-data' action='articlePageL.php' method='POST'>
				<input type='hidden' name='commenting' value='false'>
				Comment: <input type='text' id ='user' name='comment' autocomplete='off'>
				<input type = 'hidden' name='storyid' value='".$storyid."'>
				<input type='hidden' name='token' value='".$token."'>
				<input type='hidden' name='timestamp' value='".$time."'>
				<input type = 'hidden' name='submitter' value='".$submitter."'>
				<input type = 'hidden' name='title' value='".$title."'>
				<input name='submit' type='submit' value=' Comment '>
				</form>");	
 
			$stmt->close();
		?>
		<b id="logout"><a href="userlogout.php">Log Out</a></b>
	</body>
</html>