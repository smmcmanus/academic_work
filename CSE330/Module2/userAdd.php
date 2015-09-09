<?php
$fn = '../users.txt';
$file = fopen($fn, "a+");
$new_user = $_POST['addition'];
if(!preg_match('/^[\w_\.\-]+$/', $new_user) ){
	echo "Invalid username";
	header("location: upload_failure.html");
	exit;
}
if($new_user){ 
	fwrite($file, $new_user . "\n");
	fclose($file);
	if (!file_exists(sprintf("/srv/uploads/%s", $new_user))) {
    	mkdir(sprintf("/srv/uploads/%s", $new_user), 0777, true);
	}
	header("location: upload_success.html");
} else {
	fclose($file);
	header("location: upload_failure.html");
}
?>