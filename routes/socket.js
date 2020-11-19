var express = require('express');
var router = express.Router();
var db = require("../config/KYJ_db");
var dayjs = require('dayjs');

var io = require('socket.io').listen(3100);

// 앱에서 심장박동 이상 발견 시 작동
io.on('connection', function (socket) {
    console.log('connect1');
    router.post('/', function(req, res, next) {
      var mac = req.body.mac;
      db.query(`SELECT * FROM user_data AS ud JOIN user_disease AS udi where ud.bt_mac = ? and ud.uid = udi.uid;`, mac, function (error, db_data) {
          if (error) {
              throw error;
          }
          db_data[0].birth = dayjs(db_data[0].birth).format('YYYY-MM-DD');
          //console.log();
          socket.broadcast.emit('emgc', '1');
          socket.broadcast.emit('recMsg', {db_data});
          res.json(db_data);
      });
    });
    router.get('/test', function(req, res, next) {
        socket.broadcast.emit('emgc', '2');
        res.json('asdf');
      });
});

module.exports = router;
