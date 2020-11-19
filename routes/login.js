var express = require('express');
var router = express.Router();
var db = require("../config/KYJ_db");

/* GET home page. */
router.get(['/','/login'], function(req, res, next) {
  res.render('login');
});

// router.post(['/','/login'], function(req, res, next) {
//     db.query(`SELECT * FROM login_userdata where id="${req.body.id}" && pw="${req.body.pw}"`, function (error, db_data) {
//         if (error) {
//             throw error;
//         }
//         res.json(db_data);
//     });
// });


// router.post('/login',
//   passport.authenticate('local'),
//   function(req, res) {
//     // If this function gets called, authentication was successful.
//     // `req.user` contains the authenticated user.
//     res.redirect('/admin');
//   });

// router.post("/login", (req, res, next) => {
//     passport.authenticate("local", (authError, user, info) => { // passport/localStrategy.js를 실행시킵니다.  (1)
//       return req.login(user, loginError => {
//         if (loginError) {
//           console.error(loginError);
//         }
//       });
//     })(req, res, next);
  
//     res.redirect("/success");
//   });
module.exports = router;