var bodyParser = require('body-parser');
var csrf = require('csurf');
var express = require('express');
var cookieParser = require('cookie-parser');
var mongoose = require('mongoose');
var session = require('client-sessions');

var Schema = mongoose.Schema;
var ObjectId = Schema.ObjectId;

module.exports.createApp = function() {
  mongoose.connect('mongodb://ec2-54-191-137-146.us-west-2.compute.amazonaws.com:27017/scheduler'); //connect to mongodb
  var db = mongoose.connection;
  db.on('error', console.error.bind(console, 'connection error:'));
  db.once('open', function (callback) {
  // yay! 
  });

   user = mongoose.model('user', new Schema({ //mongo user model
    id:           ObjectId,
    username:     { type: String, required: true, unique: true },
    password:     { type: String, required: true },
  }));

  classy = mongoose.model('classy', new Schema({ //mongo class model
    id:           ObjectId, 
    classyOwner:  { type: String, required: true },
    name:         { type: String, required: true },
    time:         { type: String, required: true }, 
    building:     { type: String, required: true }
  }))

  var app = express();
  app.engine('html', require('ejs').renderFile);	//using EJS
  app.use(bodyParser.urlencoded({ extended: true }));
  app.use(cookieParser());
  app.use(session({		//User sessions
    cookieName: 'session',
    secret: 'sugarspiceneverythingnice',
    duration: 30 * 60 * 1000,
    activeDuration: 5 * 60 * 1000,
  }));
  //app.use(csrf());
  app.use(function(req, res, next) {
    if (req.session && req.session.user) {
      user.findOne({ username: req.session.user.username }, function(err, user) { //login validation
        if (user) {
          var exists = {
            username:user.username,
          };
          req.session.user = exists;
          req.user = exists;
          res.locals.user = exists;
        }
        next();
      });
    } else {
      next();
    }
  } );

  app.use(require('./routers.js'));

  return app;
};

module.exports.requireLogin = function(req, res, next) {
  if (!req.user) {
    res.redirect('/login');		//If not logged in, redirect
  } else {
    next();
  }
};