var express = require('express');
var router = express.Router();
var db = require("../config/KYJ_db");

var menubar = {
    "공지사항": "/mng/notice",
    "자주 묻는 질문": "/mng/faq",
    "1:1 문의": "/mng/oq",
    "예약": "/mng/rsrv"
}

/* GET home page. */
router.get(['/', '/faq'], function(req, res, next) {
    db.query(`SELECT * FROM faq`, function (error, db_data) {
        if (error) {
            throw error;
        }
        console.log(db_data)
        res.render('mng/faq', {menubar, db_data});
    });
});

router.get('/oq', function(req, res, next) {
    res.render('mng/oq', {menubar});
});

router.post('/oq', function(req, res, next) {
    let data = {
        email : req.body.email1 + "@" + req.body.email2,
        writer : req.body.writer,
        content : req.body.content
    };

    db.query(`INSERT INTO oq set ?`, data, function (error, field) {
        if(error){
            throw error;
        }
        else{
            res.render('mng/oq', {menubar});
        }
    });
});

router.get('/rsrv', function(req, res, next) {
    res.render('mng/rsrv', {menubar});
});

router.get('/notice', function(req, res, next) {
    db.query(`SELECT * FROM notice`, function (error, db_data) {
        if (error) {
            throw error;
        }
        console.log(db_data)
        res.render('mng/notice', {menubar, db_data});
    });
});

module.exports = router;