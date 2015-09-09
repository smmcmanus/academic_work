<!DOCTYPE html>
<html>
	<head>
		<title>Calculator</title>
		<link rel="stylesheet" type="text/css" href="calcStyle.css">
	</head>
	<body>
		<form action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>" method="GET">
			First Number:<br>
			<input type = "text" name = "firstNum" autocomplete="off">
			<br>Second Number:<br>
			<input type = "text" name = "secondNum" autocomplete="off">
			<br><br>
			<input type = "radio" name = "ops" id = "add" value = "add" checked>Addition
			<input type = "radio" name = "ops" id = "subtract" value = "subtract">Subtraction
			<br>
			<input type = "radio" name = "ops" id = "multiply" value = "multiply">Multiplication
			<input type = "radio" name = "ops" id = "divide" value = "divide">Division
			<br>
			<input type="hidden" name="submitted" value="true" />
			<input type="submit" value="Submit">	
		</form>
		<br><br>
		<div>
			<?php
			if(isset($_REQUEST['submitted'])) {
			$first = $_GET['firstNum'];
			$second= $_GET['secondNum'];
			if(is_numeric($first) && is_numeric($second)) {
				if($_GET['ops'] == 'add') {
					echo $first + $second;
				}else if($_GET['ops'] == 'subtract') {
					echo $first - $second;
				}else if($_GET['ops'] == 'multiply') {
					echo $first * $second;
				}else if($_GET['ops'] == 'divide') {
					if($second == 0){
						echo "Cannot Divide By 0";
					}else{
						echo $first / $second;
					}
				}
			}else{ 
				echo "Inputs must be Numeric";
			}
			}
			?>
		</div>		
	</body>
</html>