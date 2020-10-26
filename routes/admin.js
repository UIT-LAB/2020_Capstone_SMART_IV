var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('admin');
});

//AJAX GET METHOD
router.get('/get',function(req,res) {
 
  var data = req.query.data;

  console.log('GET Parameter = ' + data);


  var result = data + ' Succese';

  console.log(result);


  res.send({result:result});

});


//AJAX POST METHOD
router.post('/post', function(req, res){

var data = req.body.data;

  console.log('POST Parameter = ' + data);


  var result = data + ' Succese';

  console.log(result);

  res.send({result:result});

});

module.exports = router;
