/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import cors from 'cors';
import Express from 'express';
import morgan from 'morgan';

import actions from './actions';
import Cache from './lib/Cache';
import logger from './lib/Logger';
import {APP_DEBUG_CACHE_ROUTER, PORT} from './utils/env';

const app = Express();

const {
	authorize,
	authorizeCallback,
	importRequirementFromIssues,
	preauthorize,
	resync,
	updateTickets,
} = actions;

const cacheInstance = Cache.getInstance();

app.use(Express.json());
app.use(Express.text());
app.use(morgan('dev'));
app.use(cors());

app.get('/', (request, response) =>
	response.send('Testray LXC Jira Integration')
);

app.get(APP_DEBUG_CACHE_ROUTER, (request, response) => {
	logger.debug(cacheInstance);

	response.send(JSON.stringify([...cacheInstance.cache]));
});

app.get('/jira/preauthorize/:userId', (request, response) => {
	preauthorize(
		request.params.userId,
		request.headers['authorization'] as string
	);

	response.send('ok');
});

app.get('/jira/authorize/callback', (request, response) => {
	const {code, state} = request.query;

	authorizeCallback({code: code as string, state: state as string});

	response.send('ok');
});

app.get('/jira/authorize/:userId', (request, response) => {
	const authorizeURL = authorize(request.params.userId);

	response.redirect(authorizeURL);
});

app.post('/jira/update-tickets', (request, response) =>
	updateTickets(request, response)
);

app.post('/jira/import-requirement-tickets', (request, response) =>
	importRequirementFromIssues(request, response)
);

app.post('/jira/resync', (request, response) => resync(request, response));

app.listen(PORT, () => {
	logger.info(`Testray LXC Integration running at ${PORT}`);
});
