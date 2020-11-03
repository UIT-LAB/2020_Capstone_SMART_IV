const mysql = require("mysql");

require('dotenv').config({ path : ".env" });
const db = mysql.createConnection({
  host: process.env.DBhost,
  port: process.env.DBport,
  user: process.env.DBuserid,
  password: process.env.DBuserpw,
  database: process.env.DBname,
})

handleDisconnect(db);

function handleDisconnect(client){
    client.on('error', function (error) {
       if(!error.fatal) return;
       if (error.code !== 'PROTOCOL_CONNECTION_LOST') throw err;

        console.error('> Re-connecting lost MySQL connection: ' + error.stack);

        db = mysql.createConnection(client.config);
        handleDisconnect(db);
        db.connect();
    });
};
module.exports =  db;