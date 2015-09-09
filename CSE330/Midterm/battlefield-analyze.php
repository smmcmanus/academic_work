<!DOCTYPE html>
<head>
<meta charset="utf-8"/>
<title>Battlefield Analysis</title>
<style type="text/css">
body{
	width: 760px; /* how wide to make your web page */
	background-color: teal; /* what color to make the background */
	margin: 0 auto;
	padding: 0;
	font:12px/16px Verdana, sans-serif; /* default font */
}
div#main{
	background-color: #FFF;
	margin: 0;
	padding: 10px;
}
</style>
</head>
<body><div id="main">
 	<h1>Battlefield Analysis</h1>
 	<h2>Latest Critiques</h2>
 	<?php 
 		$mysqli = new MYSQLI('localhost', 'root', '#WashUBears#', 'battlefield');
 
		if($mysqli->connect_errno) {
			printf("Connection Failed: %s\n", $mysqli->connect_error);
			exit;
		}
 		$stmt = $mysqli->prepare("select critique from reports order by posted limit 5");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}
		$stmt->execute();
		$stmt->bind_result($critique);
		echo "<ul>";
		while($stmt->fetch()){
			printf("\t<li>%s</li>\n",
				htmlspecialchars($critique)
			);
		echo "</ul>\n";
		}
		$stmt->close();
  ?>
  <h2>Battle Statistics</h2>
  <?php 
 		$stmt = $mysqli->prepare("select soldiers, avg(ammunition / duration) as pounds_per_sec from reports group by soldiers order by soldiers desc");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}
		$stmt->execute();
		$stmt->bind_result($soldiers, $pounds_per_sec);
		echo "<table> <tr><td>Soldiers</td>   <td>Pounds of Ammunition per Second</td></tr>";
		while($stmt->fetch()){
			printf("<tr> <td>%s</td>  | <td>%s</td></tr><br>",
				htmlspecialchars($soldiers),
				htmlspecialchars($pounds_per_sec)
			);
		echo "</table>";
		}
		$stmt->close();
  ?>
  <br><a href="battlefield-submit.html">Submit a New Battle Report</a>
</div></body>
</html>