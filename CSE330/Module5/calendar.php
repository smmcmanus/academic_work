<!DOCTYPE html>
<html>
	<head>
		<title>Calendar</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> 
		<script src="http://classes.engineering.wustl.edu/cse330/content/calendar.min.js"></script>
		<link rel="stylesheet" type="text/css" href="calendar.css">	
	</head>
	<body>
		<div id = "labels">
		<div id = "nl" class = "box">
			<label>Username:</label><input type="text" id="username" value="">
			<br>
			<label>Password:</label><input type="password" id="password" value="">
			<br>
			<button id = "login_btn">Login</button>
			<br><br>
			<label>New Username:</label><input type="text" id="newusername" value="">
			<br>
			<label>New Password:</label><input type="text" id="newpassword" value="">
			<br>
			<label>Email:</label><input type="email" id="email" value="">
			<br>
			<button id = "register_btn">Register</button>
		</div>
		<div id = "l" class = "box">
			<p id="loggedIn" class="out"></p>
			<button id = "logout">Logout</button>
			<p id = "l1" class="out">New Event:</p>
			<label id = "l2">Title <input type="text" id="title"></label><br>
			<label id = "l3">Day <input type="number" id="day"></label><br>
			<label id = "l4">Time <input type="time" id = "time"></label><br><br>
			<button id = "createEvent">Create New Event</button>
			<input type='hidden' id ='token'>
			<p class="out">Notifications:</p>
			<div id="notifications"></div>
		</div>
		</div>
		<br><br>
		<div>
			<div id="calendeer">
			<button id = "prev_month_btn">Previous Month Button</button>
			<button id = "next_month_btn">Next Month Button</button>
			<p id='out' class='out'></p>
			<p class="weekday">Sunday</p><p class="weekday">Monday</p><p class="weekday">Tuesday</p><p class="weekday">Wednesday</p><p class="weekday">Thursday</p><p class="weekday">Friday</p><p class="weekday">Saturday</p>
			<div id ='body'></div>
			</div>
		</div>
	<script>
		document.getElementById("nl").style.display = 'block';
		document.getElementById("l").style.display = 'none';
		document.getElementById("loggedIn").innerHTML = '';

		var currentMonth = new Month(2015, 2); // October 2012
 		updateCalendar();
		notifications();
		document.getElementById("register_btn").addEventListener("click", registerAjax, false);
		document.getElementById("login_btn").addEventListener("click", loginAjax, false);
		document.getElementById("logout").addEventListener("click", logoutAjax, false);
		document.getElementById("createEvent").addEventListener("click", createEvent, false);
		document.getElementById("next_month_btn").addEventListener("click", function(event){
			currentMonth = currentMonth.nextMonth(); // Previous month would be currentMonth.prevMonth()
			updateCalendar(); // Whenever the month is updated, we'll need to re-render the calendar in HTML
		}, false);
		document.getElementById("prev_month_btn").addEventListener("click", function(event){
			currentMonth = currentMonth.prevMonth(); // Previous month would be currentMonth.prevMonth()
			updateCalendar(); // Whenever the month is updated, we'll need to re-render the calendar in HTML
		}, false);
 
		function updateCalendar(){
			var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
			document.getElementById("out").innerHTML = monthNames[currentMonth.getDateObject(1).getMonth()] + " " +currentMonth.getDateObject(1).getFullYear();
			var weeks = currentMonth.getWeeks();
 			document.getElementById("body").innerHTML = "";
 			var body = document.getElementById('body');
 			if(true){
				for(var w in weeks){
					var days = weeks[w].getDates();
					if(true){
						for(var d in days){
							var day = document.createElement("div");
							var numDay = days[d].getDate();
							if(days[d].getMonth() == currentMonth.getDateObject(1).getMonth()){
								day.setAttribute("id", numDay);
								var num = document.createTextNode(numDay);
								var numHold = document.createElement('p');
								numHold.appendChild(num);
								day.appendChild(numHold);
								getEvents(numDay, days[d].getMonth(), days[d].getFullYear());	
							}
							body.appendChild(day);
						}
					}
				}
			}
		}
		
		function notifications(){
			document.getElementById("notifications").innerHTML = "";
			var xmlHttp = new XMLHttpRequest(); // Initialize our XMLHttpRequest instance
			xmlHttp.open("POST", "pullEventNot.php", true); // Starting a POST request (NEVER send passwords as GET variables!!!)
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(!jsonData[0].login){
					return;
				}
				if(event.target.responseText != "[{'login':true}]"){
					for(var i=1; i < jsonData.length; i++){
						var realMonth = Number(jsonData[i].month) + 1;
						var invite = jsonData[i].org + " wants to add this event to your calendar: " + jsonData[i].title + " on " + realMonth + "/" + jsonData[i].day + "/" + jsonData[i].year + " at " + jsonData[i].time;
						var addButton = "<br><button id='add" + i + "'>Add To Calendar</button><button id ='delete" + i + "'>Reject Request</button><br><br>";
						document.getElementById("notifications").innerHTML += invite + addButton;						
						// document.getElementById("add" + i).addEventListener("click", addInvite.bind(this, jsonData[i].eventid), false);
						// document.getElementById("delete" + i).addEventListener("click", deleteInvite.bind(this, jsonData[i].eventid), false);
					}
					for(var j=1; j < jsonData.length; j++){
						document.getElementById("add" + j).addEventListener("click", addInvite.bind(this, jsonData[j].eventid), false);
						document.getElementById("delete" + j).addEventListener("click", deleteInvite.bind(this, jsonData[j].eventid), false);
					}
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(null); // Send the data
		}

		function registerAjax(event){
			var newusername = document.getElementById("newusername").value; // Get the username from the form
			var newpassword = document.getElementById("newpassword").value; // Get the password from the form
			var email = document.getElementById("email").value; 
			// Make a URL-encoded string for passing POST data:
			var dataString = "username=" + encodeURIComponent(newusername) + "&password=" + encodeURIComponent(newpassword) + "&email=" + encodeURIComponent(email);
			var xmlHttp = new XMLHttpRequest(); // Initialize our XMLHttpRequest instance
			xmlHttp.open("POST", "register_ajax.php", true); // Starting a POST request (NEVER send passwords as GET variables!!!)
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); // It's easy to forget this line for POST requests
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText); // parse the JSON into a JavaScript object
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("You've been Registered!");
					document.getElementById("newusername").value= "";
					document.getElementById("newpassword").value= "";
					document.getElementById("email").value= "";
				}else{
					alert("You were not registered.  "+jsonData.message);
					document.getElementById("newusername").value= "";
					document.getElementById("newpassword").value= "";
					document.getElementById("email").value= "";
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString); // Send the data
		}
		function loginAjax(event){
			var username = document.getElementById("username").value; // Get the username from the form
			var password = document.getElementById("password").value; // Get the password from the form
			// Make a URL-encoded string for passing POST data:
			var dataString = "username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password);
			var xmlHttp = new XMLHttpRequest(); // Initialize our XMLHttpRequest instance
			xmlHttp.open("POST", "login_ajax.php", true);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText); 
				if(jsonData.success){ 
					document.getElementById("nl").style.display = 'none';
					document.getElementById("l").style.display = 'block';
					document.getElementById("loggedIn").innerHTML = "Welcome " + jsonData.name; 
					document.getElementById("username").value= "";
					document.getElementById("password").value= "";
					document.getElementById('token').value = jsonData.token;
					alert("You've been Logged In!");
				}else{
					document.getElementById("username").value= "";
					document.getElementById("password").value= "";
					alert("You were not logged in.  "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString); // Send the data
			updateCalendar();
			notifications();
		}
		function logoutAjax(event){
			var xmlHttp = new XMLHttpRequest(); // Initialize our XMLHttpRequest instance
			xmlHttp.open("POST", "cal_logout.php", true);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText); 
				if(jsonData.success){  
					alert("You've been logged out.");
					document.getElementById("nl").style.display = 'block';
					document.getElementById("l").style.display = 'none';
					document.getElementById("loggedIn").innerHTML = '';
					document.getElementById('token').value = ""; 
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(null); // Send the data
			updateCalendar();
		}
		function getEvents(day, month, year){
			var dataString = "day=" + encodeURIComponent(day) + "&month=" + encodeURIComponent(month) + "&year=" + encodeURIComponent(year);
			var xmlHttp = new XMLHttpRequest(); // Initialize our XMLHttpRequest instance
			xmlHttp.open("POST", "pull_events.php", true); // Starting a POST request (NEVER send passwords as GET variables!!!)
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(!jsonData[0].login){
					return;
				}
				if(event.target.responseText != "[{'login':true}]"){
					for(var i=1; i < jsonData.length; i++){
						var node = document.getElementById(day);
						var p = document.createElement("p");
						var text = document.createTextNode(jsonData[i].title);
						p.appendChild(text);
						node.appendChild(p);
						p.setAttribute("id", (jsonData[i].id + 100));
						p.setAttribute("class", jsonData[i].time);
						document.getElementById((jsonData[i].id + 100)).addEventListener("click", edit_delete_display, false);
					}
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString); 
		}

		function addInvite(eventid){
			var dataString = "eventid=" + encodeURIComponent(eventid);
			var xmlHttp = new XMLHttpRequest(); 
			xmlHttp.open("POST", "addToCalendar.php", true); 
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Request added to calendar!");
					updateCalendar();
					notifications();
					deleteInvite(eventid);
				}else{
					alert("Request not added to calendar, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);
		}

		function deleteInvite(eventid){
			var dataString = "eventid=" + encodeURIComponent(eventid);
			var xmlHttp = new XMLHttpRequest(); 
			xmlHttp.open("POST", "deleteInvite.php", true); 
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Request deleted!");
					notifications();
				}else{
					alert("Request not deleted, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);			
		}

		function edit_delete_display(event){ //Opens a new window to display info
			var rid = event.target.id - 100;
			var newWindow = window.open("", null, "height=200,width=400,status=yes,toolbar=no,menubar=no,location=no");
			var a = new Date("January 1, 2015 " + event.target.className + ":00");
 			var time = getTime(a);
			newWindow.document.write("Title: " + event.target.innerHTML + "<br>");
			newWindow.document.write("Time: " + time + "<br>");
 			newWindow.document.write("<br><button id='delete'>Delete</button><br>");
 			newWindow.document.write("<label> New Title <input type=text' id='newtitle'></input></label>");
 			newWindow.document.write("<br><button id='editT'>Edit Title</button><br>");
 			newWindow.document.write("<label>New Day <input type='number' id='newday' min='1' max='31'></input></label>");
 			newWindow.document.write("<br><button id='editD'>Edit Day</button><br>");
 			newWindow.document.write("<label>New Time <input type='time' id = 'newtime'></input></label>");
 			newWindow.document.write("<br><button id='editTi'>Edit Time</button><br>");
 			newWindow.document.write("<label> User <input type=text' id='share'></input></label>");
 			newWindow.document.write("<br><button id='shareb'>Share</button><br>");
			newWindow.document.getElementById('editT').addEventListener("click", function(event){
							var title = newWindow.document.getElementById("newtitle").value;
							editTitle(title, rid);
							newWindow.close();
							}, false);
			newWindow.document.getElementById('editD').addEventListener("click", function(event){
 							var day = newWindow.document.getElementById("newday").value;
 							editDay(day, rid);
							newWindow.close();
							}, false);
			newWindow.document.getElementById('editTi').addEventListener("click", function(event){
 							var time = newWindow.document.getElementById("newtime").value;
 							editTime(time, rid);
							newWindow.close();
							}, false);				
 			newWindow.document.getElementById('delete').addEventListener("click", function(event){
 							deleteEvent(rid);
							newWindow.close();
							}, false);
 			newWindow.document.getElementById('shareb').addEventListener("click", function(event){
							var share = newWindow.document.getElementById("share").value;
							shareEvent(share, rid);
							newWindow.close();
							}, false);
		}
		function getTime(date) { //Formats Date from 24 hour clock
            var hours = date.getHours();
            var minutes = date.getMinutes();

            if (minutes < 10)
                minutes = "0" + minutes;

            var suffix = "AM";
            if (hours >= 12) {
                suffix = "PM";
                hours = hours - 12;
            }
            if (hours === 0) {
                hours = 12;
            }
            var current_time = hours + ":" + minutes + " " + suffix;
            return current_time;
        }
		function deleteEvent(id){
			var token = document.getElementById('token').value;
			var dataString = "id=" + encodeURIComponent(id) + "&token=" + encodeURIComponent(token);
			var xmlHttp = new XMLHttpRequest(); 
			xmlHttp.open("POST", "deleteEvent.php", true); 
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Event Deleted!");
					updateCalendar();
				}else{
					alert("Event not deleted, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);
		}
		function editTitle(title, id){
			var token = document.getElementById('token').value;
			var dataString = "id=" + encodeURIComponent(id) + "&title=" + encodeURIComponent(title) + "&token=" + encodeURIComponent(token);
			var xmlHttp = new XMLHttpRequest(); 
			xmlHttp.open("POST", "editTitle.php", true); 
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Event edited!");
					updateCalendar();
				}else{
					alert("Event not edited, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);	
		}
		function editDay(day, id){
			var token = document.getElementById('token').value;
			var dataString = "id=" + encodeURIComponent(id) + "&day=" + encodeURIComponent(day) + "&token=" + encodeURIComponent(token);
			var xmlHttp = new XMLHttpRequest(); 
			xmlHttp.open("POST", "editDay.php", true); 
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Event edited!");
					updateCalendar();
				}else{
					alert("Event not edited, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);	
		}
		function editTime(time, id){
			var token = document.getElementById('token').value;
			var dataString = "id=" + encodeURIComponent(id) + "&time=" + encodeURIComponent(time) + "&token=" + encodeURIComponent(token);
			var xmlHttp = new XMLHttpRequest(); 
			xmlHttp.open("POST", "editTime.php", true); 
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Event edited!");
					updateCalendar();
				}else{
					alert("Event not edited, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);	
		}
		function createEvent(){
			var token = document.getElementById('token').value;
			var title = document.getElementById("title").value;
			var day = document.getElementById("day").value;
			var month = currentMonth.month;
			var year = currentMonth.year;
			var time = document.getElementById("time").value;
			var dataString = "title=" + encodeURIComponent(title) + "&day=" + encodeURIComponent(day) + "&month=" + encodeURIComponent(month) + "&year=" + encodeURIComponent(year) + "&time=" + encodeURIComponent(time) + "&token=" + encodeURIComponent(token);
			var xmlHttp = new XMLHttpRequest(); // Initialize our XMLHttpRequest instance
			xmlHttp.open("POST", "newEvent_ajax.php", true); // Starting a POST request (NEVER send passwords as GET variables!!!)
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Event Added!");
				}else{
					alert("Event not added, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);
			updateCalendar();
			document.getElementById("title").value = "";
			document.getElementById("day").value = "";
			document.getElementById("time").value = "";
		}
		function shareEvent(share, id){
			var dataString = "share=" + encodeURIComponent(share) + "&id=" + encodeURIComponent(id);
			var xmlHttp = new XMLHttpRequest(); 
			xmlHttp.open("POST", "shareEvent_ajax.php", true); 
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.addEventListener("load", function(event){
				var jsonData = JSON.parse(event.target.responseText);
				if(jsonData.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
					alert("Event shared");
					updateCalendar();
				}else{
					alert("Event not shared, "+jsonData.message);
				}
			}, false); // Bind the callback to the load event
			xmlHttp.send(dataString);			
		}
	
	</script>
	</body>
</html>