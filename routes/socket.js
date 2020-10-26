var express = require('express');
var router = express.Router();


var io = require('socket.io').listen(3100);

io.on('connection', function (socket) {
    console.log('connect');
    router.get('/', function(req, res, next) {
      res.send('respond with a resource');
      socket.broadcast.emit('recMsg', {comment: "asdf"});
    });
});


module.exports = router;
