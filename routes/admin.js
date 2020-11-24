var express = require('express');
var db = require("../config/KYJ_db");
var router = express.Router();
var dayjs = require('dayjs');

/* GET home page. */
router.get('/', function(req, res, next) {
  db.query(`SELECT * FROM user_data`, function (error, db_data) {
      if (error) {
          throw error;
      }
      res.render('admin', {db_data, dayjs});
  });
});

module.exports = router;
