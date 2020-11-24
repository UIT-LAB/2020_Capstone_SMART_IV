var express = require('express');
var db = require("../config/KYJ_db");
var router = express.Router();
/* GET home page. */
router.post('/getuserdata', function(req, res, next) {
    let bt_value = req.body.mac;
    //let bt_value = "74:9E:F5:81:8F:49";
    db.query(`SELECT * FROM user_data AS ud JOIN user_disease AS udi where ud.bt_mac = ? and ud.uid = udi.uid;`, bt_value, function (error, db_data) {
        if (error) {
            throw error;
        }
        res.json(db_data);
    });
});
module.exports = router;