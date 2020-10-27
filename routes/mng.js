var express = require('express');
var router = express.Router();

/* GET home page. */
router.get(['/', '/faq'], function(req, res, next) {
    res.render('mng/faq');
});

router.get('/oq', function(req, res, next) {
    res.render('mng/oq');
});

router.get('/rsrv', function(req, res, next) {
    res.render('mng/rsrv');
});

module.exports = router;