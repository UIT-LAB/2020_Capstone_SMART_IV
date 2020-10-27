var express = require('express');
var router = express.Router();

/* GET home page. */
router.get(['/','/main'], function(req, res, next) {
  res.render('hos/hosMain');
});

router.get('/floor', function(req, res, next) {
    res.render('hos/floor');
});

router.get('/pic', function(req, res, next) {
    res.render('hos/pic');
});

router.get('/loc', function(req, res, next) {
    res.render('hos/loc');
});
module.exports = router;