<?php 
	session_start();
	require 'database.php';
?>
<!DOCTYPE html>
<html>
	<head>
	<title>Article Page</title>
	<link rel="stylesheet" type="text/css" href="news.css">
	</head>
	<body>
		<h1><a href='homepage.php'>Home</a></h1>
		<h2 id="Title"><?php echo htmlspecialchars($_POST['title']) ?></h2>
		<p>Submitter: <?php echo htmlentities($_POST['submitter']) ?></p>
		<?php
			$storyid = $_POST['storyid'];
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
				printf("\t %s %s<br>%s\n",
					"<br>Vote:<b>".htmlspecialchars($vote)."</b>",
					"<p><a href='".$link."'>Link</a><br></p>",
					"$content"
				);
			}
			$stmt1 = $mysqli->prepare("SELECT comments.content, users.username FROM users INNER JOIN comments ON comments.user_id=users.user_id where story_id = ? order by comment_ID ASC");
			if(!$stmt1){
				printf("Query Prep Failed: %s\n", $mysqli->error);
				exit;
			}
			$stmt1->bind_param('i', $storyid);
			$stmt1->execute();
			$stmt1->bind_result($content, $user);
			echo "<h3>Comments:</h3><br>";
			while($stmt1->fetch()){	
				printf("\t%s %s\n",
				htmlspecialchars($content),
				"  - ".$user."<br>");
			}
 
			$stmt->close();
		?>
	</body>
</html>