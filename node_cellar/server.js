var http = require('http');
var express = require('express');
var app = express();

var players = require('./routes/players.js')


var port = 3056;

app.get('/players', players.findAll);
app.get('/player', players.findByName);

app.listen(port);
console.log('Listening on port ' + port + '...');