<?php 
	session_start();
	$userprof = $_POST['user'];
	require 'database.php';
	if(!isset($_SESSION['login_user'])){
		header("location: homepage.php");
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<title>User Profile</title>
	<link rel="stylesheet" type="text/css" href="news.css">
        </head>
	<body>
		<h1><a href='userhome.php'>Home</a></h1>
                <b id="welcome">User Page: <?php echo htmlentities($userprof) ?></b>
				<br>
				<h2>About:</h2>
				<?php    
				$stmt0 = $mysqli->prepare("select about, user_id, userpicture from users where username = ?");
				if(!$stmt0){
					printf("Query Prep Failed: %s\n", $mysqli->error);
					exit;
				}
				$stmt0->bind_param('s', $userprof);
				$stmt0->execute();
  				$stmt0->store_result();
				$stmt0->bind_result($about, $uid, $pic);
				while($stmt0->fetch()){ 
                                        printf("%s <br> %s",
                                        htmlspecialchars($about),
                                        "<img src='".$pic."' alt='fluffy kitty' width='128' height='128'><br>"
                                        );
				}
				//Individual users stories
                $stmt = $mysqli->prepare("select stories.title, stories.story_ID, stories.link, users.username, stories.vote, stories.submit_time from stories INNER JOIN users ON stories.user_id = users.user_id where users.username= ? order by story_ID DESC");
				if(!$stmt){
					printf("Query Prep Failed: %s\n", $mysqli->error);
					exit;
				}
 				$stmt->bind_param('s', $userprof);
				$stmt->execute();
  				$stmt->store_result();
				$stmt->bind_result($name, $id, $link, $user, $vote, $timestamp);
				echo "<h1>Stories</h1>";
				while($stmt->fetch()){      
                            printf("\t<h3>%s</h3>%s %s %s\n",
                            htmlspecialchars($name),
                            "<br>Vote:<b>".htmlspecialchars($vote)."</b>",
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
				//Prints titles of articles user has commented on and the comment below
				echo "<h1>Comments</h1>";
				$stmt1 = $mysqli->prepare("SELECT content, story_id FROM comments where comments.user_id= ?");
				$stmt1->bind_param('i', $uid);
				$stmt1->execute();
  				$stmt1->store_result();
				$stmt1->bind_result($content,$sid);
				while($stmt1->fetch()){ 
					$stmt2 = $mysqli->prepare("SELECT title FROM stories where stories.story_ID= ?");
					$stmt2->bind_param('i', $sid);
					$stmt2->execute();
  					$stmt2->store_result();
					$stmt2->bind_result($title);
					while($stmt2->fetch()){
						printf("\t<h3>%s</h3>",
						"Title: '".htmlspecialchars($title)."'");
					}
					$stmt2->close();
					printf("%s",
					htmlspecialchars($content));
				}
				$stmt1->close();
		?>
		<br>
		<b id="logout"><a href="userlogout.php">Log Out</a></b>
	</body>
</html>