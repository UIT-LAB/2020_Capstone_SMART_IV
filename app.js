var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
// var session = require("express-session");
// var passport = require("passport");
// var passportConfig = require('./middleware/passport');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var loginRouter = require('./routes/login');
var socketRouter = require('./routes/socket');
var hosRouter = require('./routes/hos');
var mngRouter = require('./routes/mng');
var adminRouter = require('./routes/admin');
var androidRouter = require('./routes/android');

var app = express();
// app.use(
//   session({
//     resave: false,
//     saveUninitialized: false,
//     secret: "conative",
//     cookie: {
//       httpOnly: true,
//       secure: false
//     }
//   })
// );
// app.use(express.urlencoded({ extended: false }));  // 클라이언트의 form값을 req.body에 넣음
// app.use(passport.initialize());                    // passport 동작
// app.use(passport.session());                       // passport.deserializeUser 실행

// passportConfig(passport);

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'node_modules')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/login', loginRouter);
app.use('/socket', socketRouter);
app.use('/hos', hosRouter);
app.use('/mng', mngRouter);
app.use('/admin', adminRouter);
app.use('/android', androidRouter);

// app.use(session({
//   secret:"#JDKLF439jsdlfsjl",
//   resave:false,
//   saveUninitialized:true,
//   store: sessionStore
// }))

// var passport = require('passport')
//   , LocalStrategy = require('passport-local').Strategy;

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});


module.exports = app;
