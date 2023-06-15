/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import { Elysia, t } from 'elysia';

import Cache from './lib/Cache';
import logger from './lib/Logger';
import Jira from './services/jira/Jira';
import JiraEngine from './services/jira/JiraEngine';
import Testray from './services/liferay/Testray';

const { APP_DEBUG_CACHE_ROUTER = '/cache', PORT } = Bun.env;

const jira = new Jira();
const jiraEngine = new JiraEngine();
const testray = new Testray();

const getUserId = (request: Request) =>
    request.headers.get('liferay-user-id') ?? '20123';

const cacheInstance = Cache.getInstance();

new Elysia()
    .get('/', () => 'Testray LXC Jira Integration')
    .get(APP_DEBUG_CACHE_ROUTER, () => {
        logger.debug(cacheInstance);

        return cacheInstance;
    })
    .get('/jira/authorize', async ({ request, set }) =>
        jira.authorize(getUserId(request), set)
    )
    .get('/jira/authorize/callback', async ({ query: { code, state } }) => {
        const response = await jira.exchangeAuthorizationCode({
            code: code as string,
            state: state as string,
        });

        await testray.setTestrayOAuthJiraCode(response, state as string);

	.post(
		"/jira/getissues",
		({ body, request }) => jira.getIssues(body.issues, getUserId(request)),
		{
			schema: {
				body: t.Object({
					issues: t.Array(t.String()),
				}),
			},
		},
	)
