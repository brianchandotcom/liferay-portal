/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Request, Response} from 'express';

import Cache from '../lib/Cache';
import logger from '../lib/Logger';
import Jira from '../services/jira/Jira';
import JiraEngine from '../services/jira/JiraEngine';
import Testray from '../services/liferay/Testray';
import {getPusherClient} from '../services/pusher';
import {runAsyncAction} from '../utils';

const jiraBaseURL = 'https://liferay.atlassian.net';
const cacheInstance = Cache.getInstance();

const jira = new Jira();
const jiraEngine = new JiraEngine();
const testray = new Testray();

const actions = {
	authorize: (userId: string) => jira.authorize(userId),
	authorizeCallback: ({code, state}: {code: string; state: string}) => {
		runAsyncAction(async () => {
			const response = await jira.exchangeAuthorizationCode({
				code: code as string,
				state: state as string,
			});

			await testray.setTestrayOAuthJiraCode(response, state as string);
		});
	},
	importRequirementFromIssues: async (
		request: Request,
		response: Response
	) => {
		const {
			objectEntry: {
				statusByUserId: userId,
				values: {
					issues: _issues,
					r_projectJiraImportRequirements_c_projectId: projectId,
				},
			},
		} = request.body;

		const httpContext = {
			authorization: request.headers['authorization'] as string,
			userId,
		};

		runAsyncAction(async () => {
			const jiraIssues = ((_issues ?? '') as string)
				.split(',')
				.map((issue) => issue.trim());

			const issues = await jira.getIssues(jiraIssues, httpContext);

			for (const issue in issues) {
				if (issues[issue]) {
					const jiraIssue = issues[issue];

					await testray.createRequirement(
						{
							components: jiraIssue.jiraComponents.join(', '),
							description: jiraIssue.description,
							descriptionType: 'markdown',
							key: `R-${Math.ceil(Math.random() * 1000)}`,
							linkTitle: jiraIssue.key,
							linkURL: `${jiraBaseURL}/${jiraIssue.key}`,
							r_projectToRequirements_c_projectId: projectId,
							summary: jiraIssue.summary,
						},
						httpContext
					);
				}
			}

			const pusherClient = getPusherClient();

			if (pusherClient) {
				pusherClient.trigger(`${userId}-requirements`, 'processed', {
					message: `${Object.keys(issues)
						.filter((issue) => issue)
						.join(', ')} was imported`,
				});
			}
		}, response);
	},
	preauthorize: (userId: string, authorization: string) => {
		cacheInstance.set(`preauthorize-${userId}`, authorization);
	},
	resync: (request: Request, response: Response) => {
		const payload =
			typeof request.body === 'string'
				? JSON.parse(request.body)
				: request.body;

		const {
			objectEntry: {
				id: requirementId,
				statusByUserId: userId,
				values: {linkTitle: ticket},
			},
		} = payload as any;

		const httpContext = {
			authorization: request.headers['authorization'] as string,
			userId,
		};

		runAsyncAction(async () => {
			const issue = await jira.getIssue(ticket, httpContext);

			await testray.resyncWithJira(issue, {
				...httpContext,
				requirementId,
			});

			logger.info(
				`Success to Sync ${ticket} in Testray Requirement ${requirementId}`
			);

			const pusherClient = getPusherClient();

			if (pusherClient) {
				pusherClient.trigger(`${userId}-requirements`, 'processed', {
					message: `Resync completed ${requirementId}`,
				});
			}
		}, response);
	},
	updateTickets: (request: Request, response: Response) => {
		const {
			objectEntry: {
				id: caseResultId,
				statusByUserId: userId,
				values: {issues},
			},
			originalObjectEntry: {
				values: {issues: oldIssues},
			},
		} = request.body;

		if (!issues?.length || oldIssues?.trim() === issues?.trim()) {
			logger.info(`No issues to update on ${caseResultId} caseResultId`);
		}

		const httpContext = {
			authorization: request.headers['authorization'] as string,
			userId,
		};

		runAsyncAction(async () => {
			const _issues = issues
				.split(',')
				.map((issue: string) => issue.trim()) as string[];

			await jiraEngine.updateIssues(_issues, httpContext);

			logger.info(
				`Success to update ${_issues.join(
					', '
				)} on Case Result ${caseResultId}`
			);
		}, response);
	},
};

export default actions;
