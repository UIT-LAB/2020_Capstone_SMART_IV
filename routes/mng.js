var express = require('express');
var router = express.Router();

var menubar = {
    "자주 묻는 질문": "/mng/faq",
    "1:1 문의": "/mng/oq",
    "예약": "/mng/rsrv"
}

/* GET home page. */
router.get(['/', '/faq'], function(req, res, next) {
    res.render('mng/faq', {menubar});
});

router.get('/oq', function(req, res, next) {
    res.render('mng/oq', {menubar});
});

router.get('/rsrv', function(req, res, next) {
    res.render('mng/rsrv', {menubar});
});

module.exports = router;