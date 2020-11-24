var express = require('express');
var router = express.Router();
var db = require("../config/KYJ_db");

/* GET home page. */
router.get(['/','/login'], function(req, res, next) {
  res.render('login');
});

router.post('/', function (req, res, next) {
  //HASHING
  var bkfd2Password = require("pbkdf2-password");
  var hasher = bkfd2Password();

  db.query(`select * from login_userdata where id="${req.body.id}"`, function (error, db_value) {
      if (error) {
          throw error;
      }
      try{
        hasher({ password: req.body.pw, salt: db_value[0].salt }, (err, pass, salt, hash) => {
        if (hash === db_value[0].pw) {
          sess = req.session;
          sess.user_id = db_value[0].id;
          res.redirect('/admin');
        }else{
            res.send('<script> alert("비밀번호를 확인하세요!"); location.href="/login" </script>');
        }});
      }catch(e){
        res.send('<script> alert("존재하지 않는 아이디입니다."); location.href="/login" </script>');
      }
      
  });
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