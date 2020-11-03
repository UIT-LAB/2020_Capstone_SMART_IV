var express = require('express');
var router = express.Router();

var menubar = {
    "소개 페이지": "/hos/",
    "층별 안내": "/hos/floor",
    "사진": "/hos/pic",
    "찾아오시는 길": "/hos/loc"
}


/* GET home page. */
router.get(['/','/main'], function(req, res, next) {
  res.render('hos/hosMain', {menubar});
});

router.get('/floor', function(req, res, next) {
    res.render('hos/floor', {menubar});
});

router.get('/pic', function(req, res, next) {
    res.render('hos/pic', {menubar});
});

router.get('/loc', function(req, res, next) {
    res.render('hos/loc', {menubar});
});

module.exports = router;