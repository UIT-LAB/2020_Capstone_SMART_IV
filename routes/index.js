var express = require('express');
var router = express.Router();
var db = require("../config/KYJ_db");

/* GET home page. */
router.get('/', function(req, res, next) {
  db.query(`SELECT * FROM notice`, function (error, db_data) {
      if (error) {
          throw error;
      }
      console.log(db_data)
      res.render('index', { db_data });
  });
});

module.exports = router;
