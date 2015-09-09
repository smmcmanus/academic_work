var bcrypt = require('bcryptjs');
var express = require('express');


var app = require('./app.js');
var router = express.Router();

router.post('/register', function(req, res) {
  var salt = bcrypt.genSaltSync(10);
  var hash = bcrypt.hashSync(req.body.password, salt);	//Password protection
  var newUser = new user({
    username:  req.body.username,
    password:   hash
  });
  newUser.save(function(err) {
    if (err) {
      var error = 'No.';
      if (err.code === 11000) {
        error = 'Someone already has that username. Be more original.';
      }
      res.render('landing.html', { error: error });
    } else {
	     var exists = {
            username: newUser.username,
          };
          req.session.user = exists;
          req.user = exists;
          res.locals.user = exists;
      res.redirect('/index');
    }
  });
});

router.post('/login', function(req, res) {
  user.findOne({ username: req.body.username }, function(err, user) {	//check unique
    if (!user) {
      res.render('landing.html');
    } else {
      if (bcrypt.compareSync(req.body.password, user.password)) {	//Password is correct
        var exists = {
            username: user.username,
          };
          req.session.user = exists;
          req.user = exists;
          res.locals.user = exists;
          req.session.valid = true;
        res.redirect('/index');
      } else {
        res.render('landing.html');
      }
    }
  });
});

router.get('/logout', function(req, res) {
  if (req.session) {
    req.session.reset();
  }
  res.redirect('/');
});

router.get('/', function(req, res) { 
  res.render('landing.html');
});


router.get('/index', app.requireLogin, function(req, res) {
	var classes = [];
  classy.find({classyOwner: req.session.user.username},function(err, obj) {
      classes = obj;
      console.log("Classes " + classes);
      res.render('index.html', {user:req.session.user, classes:classes});
    });
	//put a db query here to get the users events and pass it with the render
});

router.post('/add', function(req, res) {
  var addClassy = new classy({
    classyOwner:  req.body.user,
    name:         req.body.name,
    time:         req.body.time,
    building:     req.body.building
  });
  console.log(addClassy);
  addClassy.save(function(err) {
    if (err) {
      var error = 'No.';
      console.log(err);
    } else {
      res.redirect('/index');
    }
  });
});

router.post('/delete', function(req, res) {
  classy.find({ _id:req.body.dclass }).remove(function(err) {
    if (err) {
      console.log(err);
    } else {
      res.redirect('/index');
    }
  });
});
router.post('/updateName', function(req, res) {
  classy.find({ _id:req.body.dclass }).update({$set:{name:req.body.name}}, function(err) {
    if (err) {

      console.log(err);
    } else {
      res.redirect('/index');
    }
  });
});
router.post('/updateTime', function(req, res) {
  classy.find({ _id:req.body.dclass }).update({$set:{time:req.body.time}}, function(err) {
    if (err) {
      console.log(err);
    } else {
      res.redirect('/index');
    }
  });
});
router.post('/updateBuilding', function(req, res) {
  classy.find({ _id:req.body.dclass }).update({$set:{building:req.body.building}},function(err) {
    if (err) {
      console.log(err);
    } else {
      res.redirect('/index');
    }
  });
});

module.exports = router;
