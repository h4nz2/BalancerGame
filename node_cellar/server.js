var http = require('http');
var express = require('express');
var app = express();

var players = require('./routes/players.js')


var port = 5000;

app.get('/players', players.findAll);
app.get('/players/:name', players.findByName);

app.listen(port);
console.log('Listening on port ' + port + '...');