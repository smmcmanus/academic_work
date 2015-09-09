<?php
	session_start();
	if(!isset($_SESSION['login_user'])){
		header("location: index.php");
	}
	$upload_status = "";
	if(isset($_POST['upload'])){	//code for uploading files
		$filename = basename($_FILES['uploadedfile']['name']);
		if(!preg_match('/^[\w_\.\-]+$/', $filename) ){
			echo "Invalid filename";
			echo "<br><a href='home.php'>Home</a>";
			exit;
		}

		$username = $_SESSION['login_user'];
		if(!preg_match('/^[\w_\-]+$/', $username) ){
			echo "Invalid username";
			echo "<br><a href='home.php'>Home</a>";
			exit;
		}
		$full_path = sprintf("/srv/uploads/%s/%s", $username, $filename);
		$_SESSION['path'] = $full_path;
		if( move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $full_path) ){
			$upload_status = "File successfully uploaded";
		}else{
			$upload_status = "File upload unsuccessful";
		}
	}
	
?>
<!DOCTYPE html>
<html>
	<head>
		<title>Your Home Page</title>
		<link rel="stylesheet" type="text/css" href="homeStyle.css">
	</head>
	<body>
		<div id="profile">
			<b id="welcome">Welcome: <?php echo htmlentities($_SESSION['login_user']) ?></b>
			<h4>Your Files:</h4>
				<!-- iterates directory  --!>
		<div id="main">
			<p>
				<?php
					$username=$_SESSION['login_user'];
					if ($handle = opendir("/srv/uploads/$username")) {
						while (false !== ($entry = readdir($handle))) {
							if ($entry != "." && $entry != "..") {
								echo "$entry<br>";
							}
						 }
						closedir($handle);
					}
				?>
			</p>
		</div>
		<form action = "view.php" method = "POST">
			<h4>View File</h4>
			File: <input type='text' id ='file' name='filename'>
			<input name="action" type="submit" value="View">
		</form>
		<form action = "delete.php" method = "POST">
			<h4>Delete File</h4>
			File: <input type='text' id ='_file' name='_filename'>
			<input name="action" type="submit" value="Delete">
 		</form>
		<form action = "share.php" method = "POST">
		<h4>Share a File:</h4>
			User to share with: <input type='text' id ='user' name='usershare' autocomplete="off">  File: <input type='text' id ='fileShare' name='filenameShare'> <input name="submit" type="submit" value="Share">
		</form>
		<h4>Upload a File:</h4>
		<!-- references code in header --!>
		<form enctype="multipart/form-data" action="home.php" method="POST"> 
			<p>
				<input type="hidden" name="upload" value="false">
				<input type="hidden" name="MAX_FILE_SIZE" value="20000000" />
				<label for="uploadfile_input">Choose a file to upload:</label> <input name="uploadedfile" type="file" id="uploadfile_input" />
			</p>
			<p>
				<input type="submit" value="Upload_File" />
			</p>
			<span><?php echo $upload_status ?></span>
		</form>
		<b id="logout"><a href="logout.php">Log Out</a></b>
		</div>
	</body>
</html>