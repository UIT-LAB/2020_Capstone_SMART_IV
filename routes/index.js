var express = require('express');
var router = express.Router();
var db = require("../config/KYJ_db");

/* GET home page. */
router.get('/', function(req, res, next) {
  db.query(`SELECT * FROM notice`, function (error, notice_db_data) {
      if (error) {
          throw error;
      }
      db.query(`SELECT * FROM faq`, function (error, faq_db_data) {
        if (error) {
            throw error;
        }
        res.render('index', { notice_db_data, faq_db_data});
    });
  });
});

module.exports = router;
