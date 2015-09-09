<?php
// register_ajax.php
	require 'caldb.php';
	require_once '/home/swiftmailer/lib/swift_required.php';
 	header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json
 	if(($_POST['username'] == "") or ($_POST['password'] == "")){
 			echo json_encode(array(
			"success" => false,
			"message" => "Username or Password required"
		));
		exit;
 	}
	$newuser = $_POST['username'];
	$newpass = crypt($_POST['password']);
	$email = $_POST['email'];
 	$msg = 'Hi new user! Thank you for registering for our calendar. To add events, use the form at the top of the page, and then click on the event titles on the calendar for more information, or to manage or delete events. Enjoy! Sean and Michelle'; 
 	
 	if(!preg_match('/^[\w_\.\-]+$/', $newuser) ){
			echo json_encode(array(
			"success" => false,
			"message" => "Invalid username"
		));
		exit;
	}
	$stmt = $mysqli->prepare("INSERT INTO users (username, cryptpass) VALUES (?, ?)");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	$stmt->bind_param('ss', $newuser, $newpass);
	if($stmt->execute()){
			$transport = Swift_SmtpTransport::newInstance('ssl://smtp.gmail.com', 465)
				->setUsername('smcmanus14@gmail.com') // Your Gmail Username
				->setPassword('pickles1995'); // Your Gmail Password

			$mailer = Swift_Mailer::newInstance($transport);

			$message = Swift_Message::newInstance('Welcome!')
				->setFrom(array('smcmanus14@gmail.com' => '330 Calendar')) // can be $_POST['email'] etc...
				->setTo(array($email => 'Receiver Name')) // your email / multiple supported.
				->setBody($msg, 'text/html');

			$mailer->send($message);
		echo json_encode(array(
			"success" => true
		));
		exit;	
	}
	else{
		echo json_encode(array(
			"success" => false,
			"message" => "Duplicate username or invalid pasword"
		));
		exit;
	}
?>