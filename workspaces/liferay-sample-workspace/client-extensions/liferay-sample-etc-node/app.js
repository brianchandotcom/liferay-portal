/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import express from 'express';
import fetch from 'node-fetch';

import config from './util/configTreePath.js';
import {
	corsWithReady,
	liferayJWT,
} from './util/liferay-oauth2-resource-server.js';
import {logger} from './util/logger.js';

const serverPort = config['server.port'];
const app = express();

logger.log(`config: ${JSON.stringify(config, null, '\t')}`);

app.use(express.json());
app.use(corsWithReady);
app.use(liferayJWT);

app.get(config.readyPath, (req, res) => {
	res.send('READY');
});

app.get('/comic', async (req, res) => {
	logger.log(`User ${req.jwt.username} is authorized`);
	logger.log(`User scopes: ${req.jwt.scope}`);

	const comicResponse = await fetch('https://xkcd.com/info.0.json');

	if (comicResponse.status !== 200) {
		res.status(500).send('Error fetching comic ');

		return;
	}

	const comic = await comicResponse.json();

	logger.log('Comic fetched\n%s', JSON.stringify(comic, null, 2));

	res.status(200).json(comic);
});

app.post('/sample/object/action/1', async (req, res) => {
	const json = req.body;

	logger.log(`User ${req.jwt.username} is authorized`);
	logger.log(`User scopes: ${req.jwt.scope}`);
	logger.log(`json: ${JSON.stringify(json, null, '\t')}`);

	res.status(200).send(json);
});

app.post('/sample/object/action/2', async (req, res) => {
	const json = req.body;

	logger.log(`User ${req.jwt.username} is authorized`);
	logger.log(`User scopes: ${req.jwt.scope}`);
	logger.log(`json: ${JSON.stringify(json, null, '\t')}`);

	res.status(200).send(json);
});

app.listen(serverPort, () => {
	logger.log(`App listening on ${serverPort}`);
});

export default app;
