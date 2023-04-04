'use strict';

const axios = require('axios');
const bodyParser = require('body-parser');
const express = require('express');

const app = express();
const HOST = '0.0.0.0';
const PORT = 3000;

app.get('/', (req, res) => {
	res.send(req.body);
});

app.listen(PORT, HOST, () => {
	console.log(`Running on http://${HOST}:${PORT}`);
});

app.post('/', (req, res) => {
	res.send(req.body);
});

app.use(bodyParser.json());
