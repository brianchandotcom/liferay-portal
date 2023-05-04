'use strict';

import bodyParser from 'body-parser';
import config from './util/configTreePath.js';
import express from 'express';
import fetch from 'node-fetch';
import {
	corsWithReady,
	liferayJWT,
} from './util/liferay-oauth2-resource-server.js';
import log from './util/log.js';

log.info(`config: ${JSON.stringify(config, null, '\t')}`);

const app = express();

app.use(bodyParser.json());
app.use(corsWithReady);
app.use(liferayJWT);

app.get(config.readyPath, (req, res) => {
	res.send('READY');
});

app.get('/comic', async (req, res) => {
	log.info('User %s is authorized', req.jwt.username);
	log.info('User scopes: ' + req.jwt.scope);

	const comicResponse = await fetch('https://xkcd.com/info.0.json');

	if (comicResponse.status !== 200) {
		res.status(500).send('Error fetching comic ');
		return;
	}

	const comic = await comicResponse.json();

	log.info('Comic fetched\n%s', JSON.stringify(comic, null, 2));

	res.status(200).json(comic);
});

const serverPort = config['server.port'];

app.post('/sample/object/action/1', async (req, res) => {
	log.info('User %s is authorized', req.jwt.username);
	log.info('User scopes: ' + req.jwt.scope);

	const json = req.body;
	log.info(`json: ${JSON.stringify(json, null, '\t')}`);
	res.status(200).send(json);
});

app.post('/sample/object/action/2', async (req, res) => {
	log.info('User %s is authorized', req.jwt.username);
	log.info('User scopes: ' + req.jwt.scope);

	const json = req.body;
	log.info(`json: ${JSON.stringify(json, null, '\t')}`);
	res.status(200).send(json);
});

app.listen(serverPort, () => {
	log.info('App listening on %s', serverPort);
});

export default app;
