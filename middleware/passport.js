// const passport = require('passport');
// const LocalStrategy = require('passport-local').Strategy;
// const Users = require('../routes/login');

// module.exports = () => {
//   passport.serializeUser((user, done) => { // Strategy 성공 시 호출됨
//     done(null, user); // 여기의 user가 deserializeUser의 첫 번째 매개변수로 이동
//   });

//   passport.deserializeUser((user, done) => { // 매개변수 user는 serializeUser의 done의 인자 user를 받은 것
//     done(null, user); // 여기의 user가 req.user가 됨
//   });

//   passport.use(new LocalStrategy({ // local 전략을 세움
//     usernameField: 'id',
//     passwordField: 'pw',
//     session: true, // 세션에 저장 여부
//     passReqToCallback: false, //TRUE로 변경 시 뒤 콜백이 (req, id, password, done) 으로 변경됨.
//   }, (id, password, done) => {
//     Users.findOne({ id: id }, (findError, user) => {
//       if (findError) return done(findError); // 서버 에러 처리
//       if (!user) return done(null, false, { message: '존재하지 않는 아이디입니다' }); // 임의 에러 처리
//       return user.comparePassword(password, (passError, isMatch) => {
//         if (isMatch) {
//           return done(null, user); // 검증 성공
//         }
//         return done(null, false, { message: '비밀번호가 틀렸습니다' }); // 임의 에러 처리
//       });
//     });
//   }));
// };

// var passport = require('passport')
//   , LocalStrategy = require('passport-local').Strategy;

// passport.use(new LocalStrategy(
//   function(username, password, done) {
//     User.findOne({ username: username }, function(err, user) {
//       if (err) { return done(err); }
//       if (!user) {
//         return done(null, false, { message: 'Incorrect username.' });
//       }
//       if (!user.validPassword(password)) {
//         return done(null, false, { message: 'Incorrect password.' });
//       }
//       return done(null, user);
//     });
//   }
// ));

// const localStrategy = require("passport-local").Strategy;

// module.exports = passport => {
//   passport.use(
//     new localStrategy(
//       {
//         usernameField: "id",   // 여기서 id,pw의 값은 index.html의 form에서 해당하는 name값이여야 합니다.  
//         passwordField: "pw" 
//       },
//       (id, pw, done) => {    // id, pw는 위에서 받은 값 입니다.

//         const user = {        // 테스트 사용자정보 객체
//           id: "whwlsvy12", 
//           pw: "1234"
//         };

//         if (id === user.id && pw === user.pw) {         // id,pw를 사용하여 db에서 사용자를 조회하는 로직이 들어가야 합니다.
//           console.log("localStrategy에서 id,pw 조회 성공");
//           done(null, user);   // 콜백부분은 아래에서 자세히 설명하겠습니다.
//         }
//       }
//     )
//   );
// };