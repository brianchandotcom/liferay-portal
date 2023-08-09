/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cors} from '@elysiajs/cors';
import {Elysia} from 'elysia';

import actions from './actions';
import Cache from './lib/Cache';
import logger from './lib/Logger';

const {
	authorize,
	authorizeCallback,
	importRequirementFromIssues,
	preauthorize,
	resync,
	updateTickets,
} = actions;

const {APP_DEBUG_CACHE_ROUTER = '/cache', PORT} = Bun.env;

const cacheInstance = Cache.getInstance();

new Elysia()
	.get('/', () => 'Testray LXC Jira Integration')
	.get(APP_DEBUG_CACHE_ROUTER, () => {
		logger.debug(cacheInstance);

		return JSON.stringify([...cacheInstance.cache]);
	})
	.get('/jira/authorize/:liferay-user-id', ({params, set}) =>
		authorize(params['liferay-user-id'], set)
	)
	.get('/jira/preauthorize/:liferay-user-id', ({params, request}) =>
		preauthorize(
			params['liferay-user-id'],
			request.headers.get('authorization') as string
		)
	)
	.get('/jira/authorize/callback', ({query: {code, state}, set}) =>
		authorizeCallback({code: code as string, state: state as string}, set)
	)
	.post('/jira/update-tickets', ({body, request}) =>
		updateTickets({body, request})
	)
	.post('/jira/import-requirement-tickets', ({body, request}) =>
		importRequirementFromIssues({
			body,
			request,
		})
	)
	.post('/jira/resync', async ({body, request}) => resync({body, request}))
	.use(cors())
	.listen(Number(PORT), ({hostname, port}) =>
		logger.info(
			`🦊 Testray LXC Integration with Elysia is running at ${hostname}:${port}`
		)
	);
