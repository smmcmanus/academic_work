// Require the packages we will use:
var http = require("http"),
	socketio = require("socket.io"),
	fs = require("fs");
 
// Listen for HTTP connections.  This is essentially a miniature static file server that only serves our one file, client.html:
var app = http.createServer(function(req, resp){
	// This callback runs when a new connection is made to our HTTP server.
 
	fs.readFile("client.html", function(err, data){
		// This callback runs when the client.html file has been read from the filesystem.
 
		if(err) return resp.writeHead(500);
		resp.writeHead(200);
		resp.end(data);
	});
});
app.listen(3456);
 
// Do the Socket.IO magic:
var io = socketio.listen(app);
var clients = {};
var rooms = {
	"general":{"occupants":[],
			"admin":null,
			"password":null,
			"banned":[]
			}
	};
io.sockets.on("connection", function(socket){	
	// This callback runs when a new Socket.IO connection is established.
 	socket.on("user_login", function(data) {
 		var user = data['username'];
 		clients[user] = {"id":socket.id,"current_room":"general"};
 		rooms.general.occupants.push(user);
 		io.sockets.emit("update_user_list", {users:rooms.general.occupants});
 		io.sockets.emit("update_room_list", {rooms:Object.keys(rooms)});
		io.sockets.connected[socket.id].emit("logged_in", {success:true, user:user});
	});
	socket.on('private_message', function(data) {
		var socket_to = clients[data.to].id;
		var socket_from = clients[data.from].id;
		var message = data.from + " says: " + data.message;
		if(rooms[data.room].occupants.indexOf(data.to) == -1){
			if(io.sockets.connected[socket_from] != null){
				io.sockets.connected[socket_from].emit("not_here");
			}
		} else {
			if(io.sockets.connected[socket_from] != null && io.sockets.connected[socket_to] != null){
				io.sockets.connected[socket_to].emit("incoming_mail", {message:message});
				io.sockets.connected[socket_from].emit("sent", {success:true, message:"Your mail was sent"});
			}
		}
	});
	socket.on('new_public_room', function(data) {
		var name = data.name;
		var creator = data.creator;
		var socket_from = clients[creator].id;
		rooms[name] = {"occupants":[],
			"admin":creator,
			"password":null,
			banned:[]
		};	
		io.sockets.emit("update_room_list", {rooms:Object.keys(rooms)});
		if(io.sockets.connected[socket_from] != null){
			io.sockets.connected[socket_from].emit("room_created", {success:true, message:"Your new public room was created"});	
		}
	});
	socket.on('new_private_room', function(data) {
		var name = data.name;
		var creator = data.creator;
		var pass = data.password;
		var socket_from = clients[creator].id;
		rooms[name] = {"occupants":[],
			"admin":creator,
			"password":pass,
			"banned":[]
		};
		io.sockets.emit("update_room_list", {rooms:Object.keys(rooms)});
		if(io.sockets.connected[socket_from] != null){
			io.sockets.connected[socket_from].emit("room_created", {success:true, message:"Your new private room was created"});
		}
	});
	
	socket.on('message_to_server', function(data) {
		console.log("message: "+data["message"]); // log it to the Node.JS output
		var c_room = clients[data.username].current_room;
		var in_room = rooms[c_room].occupants;
		for (var key in in_room) {
			var s_id = clients[in_room[key]].id;
			var date = Date();
			if(io.sockets.connected[s_id] != null){
				io.sockets.connected[s_id].emit("message_to_client",{message:data["message"], username:data.username, date:date}); // broadcast the message to other users
			}
		}
	});
	socket.on('image_to_server', function(data) {
		var c_room = clients[data.username].current_room;
		var in_room = rooms[c_room].occupants;
		for (var key in in_room) {
			var date = Date();
			var s_id = clients[in_room[key]].id;
			if(io.sockets.connected[s_id] != null){
				io.sockets.connected[s_id].emit("image_to_client",{url:data["image"], username:data.username, date:date}); // broadcast the message to other users
			}
		}
	});
	socket.on('video_to_server', function(data) {
		var c_room = clients[data.username].current_room;
		var in_room = rooms[c_room].occupants;
		for (var key in in_room) {
			var date = Date();
			var s_id = clients[in_room[key]].id;
			if(io.sockets.connected[s_id] != null){
				io.sockets.connected[s_id].emit("video_to_client",{url:data["video"], username:data.username, date:date}); // broadcast the message to other users
			}
		}
	});
	socket.on('public_switch', function(data) {
		var room_list = Object.keys(rooms);
		var switcher = data.user;
		var socket_from = clients[switcher].id;
		var new_room = data.new_room;
		var old_room = data.old_room;
 		if(room_list.indexOf(new_room) == -1){
 			if(io.sockets.connected[socket_from] != null){
 				io.sockets.connected[socket_from].emit("404_room_not_found");
 			}
 		} else if(rooms[new_room].banned.indexOf(switcher) != -1){
 			if(io.sockets.connected[socket_from] != null){
 				io.sockets.connected[socket_from].emit("access_denied");
 			}
 		} else if(rooms[new_room].password != null){
 			if(io.sockets.connected[socket_from] != null){
 				io.sockets.connected[socket_from].emit("switch_is_private", {user:switcher, new_room:data.new_room});
 			}
 		} else {
 			var i = rooms[data.old_room].occupants.indexOf(switcher);
 			rooms[data.old_room].occupants.splice(i, 1);
 			rooms[new_room].occupants.push(switcher);
 			clients[switcher].current_room = data.new_room;
 			if(io.sockets.connected[socket_from] != null){
 				io.sockets.connected[socket_from].emit("switch_success", {new_room:data.new_room});
 			}
 			for(var k in Object.keys(clients)){
 				var key = Object.keys(clients)[k];
 				var user_current_room = clients[key].current_room;
 				if(io.sockets.connected[clients[key].id] != null){
 					io.sockets.connected[clients[key].id].emit("update_user_list", {users:rooms[user_current_room].occupants});
				} 			
 			}
 		}
 		
	});
	socket.on('initiate_ban', function(data) {
		var banner = data.banner;
		var bannee = data.bannee;
		var room = data.room;
		var socket_from = clients[banner].id;
		if(rooms[room].admin != banner){
			if(io.sockets.connected[socket_from] != null){
				io.sockets.connected[socket_from].emit("no_authority");
			}
		}else if(rooms[room].occupants.indexOf(bannee) == -1){
			if(io.sockets.connected[socket_from] != null){
				io.sockets.connected[socket_from].emit("not_here");
			}
		} else {
			var i = rooms[room].occupants.indexOf(bannee);
			rooms[room].occupants.splice(i, 1);
 			rooms["general"].occupants.push(bannee);
 			rooms[room].banned.push(bannee);
			clients[bannee].current_room = "general";
			if(io.sockets.connected[clients[banner].id] != null && io.sockets.connected[clients[bannee].id] != null){
				io.sockets.connected[clients[banner].id].emit("ban_success");
				io.sockets.connected[clients[bannee].id].emit("banned", {room:room});
			}
			for(var k in Object.keys(clients)){
 				var key = Object.keys(clients)[k];
 				var user_current_room = clients[key].current_room;
 				if(io.sockets.connected[clients[key].id] != null){
 					io.sockets.connected[clients[key].id].emit("update_user_list", {users:rooms[user_current_room].occupants});
 				}
 			}
		}
	});
	socket.on('initiate_kick', function(data) {
		var banner = data.banner;
		var bannee = data.bannee;
		var room = data.room;
		var socket_from = clients[banner].id;
		if(rooms[room].admin != banner){
			if(io.sockets.connected[socket_from] != null){
				io.sockets.connected[socket_from].emit("no_authority");
			}
		}else if(rooms[room].occupants.indexOf(bannee) == -1){
			if(io.sockets.connected[socket_from] != null){
				io.sockets.connected[socket_from].emit("not_here");
			}
		} else {
			var i = rooms[room].occupants.indexOf(bannee);
			rooms[room].occupants.splice(i, 1);
 			rooms["general"].occupants.push(bannee);
			clients[bannee].current_room = "general";
			if(io.sockets.connected[clients[banner].id] != null && io.sockets.connected[clients[bannee].id] != null){
				io.sockets.connected[clients[banner].id].emit("kick_success");
				io.sockets.connected[clients[bannee].id].emit("kicked", {room:room});
			}
			for(var k in Object.keys(clients)){
 				var key = Object.keys(clients)[k];
 				var user_current_room = clients[key].current_room;
 				if(io.sockets.connected[clients[key].id] != null){
 					io.sockets.connected[clients[key].id].emit("update_user_list", {users:rooms[user_current_room].occupants});
 				}
 			}
		}
	});
	socket.on('private_switch', function(data) {
		var pass = data.password;
		var switcher = data.user;
		var socket_from = clients[data.user].id;
		if(pass == rooms[data.new_room].password){
			var i = rooms[data.old_room].occupants.indexOf(switcher);
 			rooms[data.old_room].occupants.splice(i, 1);
 			rooms[data.new_room].occupants.push(switcher);
 			clients[switcher].current_room = data.new_room;
 			if(io.sockets.connected[socket_from] != null){
 				io.sockets.connected[socket_from].emit("switch_success", {new_room:data.new_room});
 			}
 			for(var k in Object.keys(clients)){
 				var key = Object.keys(clients)[k];
 				var user_current_room = clients[key].current_room;
 				if(io.sockets.connected[clients[key].id] != null){
 					io.sockets.connected[clients[key].id].emit("update_user_list", {users:rooms[user_current_room].occupants});
 				}
 			}
 		} else {
 			if(io.sockets.connected[socket_from] != null){
 				io.sockets.connected[socket_from].emit("bad_password");
 			}
 		}
	});
});