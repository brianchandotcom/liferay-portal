/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import logger from '../lib/Logger';
import Jira from '../services/jira/Jira';
import { RequestSet } from '../services/jira/JiraAuth';
import JiraEngine from '../services/jira/JiraEngine';
import Testray from '../services/liferay/Testray';
import { getPusherClient } from '../services/pusher';

const {
    LIFERAY_BASE_URL,
    LIFERAY_TESTRAY_REDIRECT_AUTHORIZATION = '/web/testray?p_l_mode=preview',
} = Bun.env;

const jira = new Jira();
const jiraEngine = new JiraEngine();
const testray = new Testray();

const actions = {
    authorize: (userId: string, set: RequestSet) => jira.authorize(userId, set),
    authorizeCallback: (
        { code, state }: { code: string; state: string },
        set: RequestSet
    ) => {
        setTimeout(async () => {
            const response = await jira.exchangeAuthorizationCode({
                code: code as string,
                state: state as string,
            });

            await testray.setTestrayOAuthJiraCode(response, state as string);
        }, 1000);

        return (set.redirect = `${LIFERAY_BASE_URL}${LIFERAY_TESTRAY_REDIRECT_AUTHORIZATION}`);
    },
    preauthorize: (userId: string, set: RequestSet) =>
        jira.authorize(userId, set),
    updateTickets: ({ body, request }: { body: unknown; request: Request }) => {
        const {
            objectEntry: {
                id: caseResultId,
                statusByUserId: userId,
                values: { issues },
            },
            originalObjectEntry: {
                values: { issues: oldIssues },
            },
        } = body as any;

        if (!issues?.length || oldIssues?.trim() === issues?.trim()) {
            logger.info(`No issues to update on ${caseResultId} caseResultId`);
        }

        const httpContext = {
            authorization: request.headers.get('authorization'),
            userId,
        };

        setTimeout(async () => {
            const _issues = issues
                .split(',')
                .map((issue: string) => issue.trim()) as string[];

            await jiraEngine.updateIssues(_issues, httpContext);

            logger.info(
                `Success to update ${_issues.join(
                    ', '
                )} on Case Result ${caseResultId}`
            );
        }, 1000);

        return 'ok';
    },
    resync: ({ body, request }: { body: unknown; request: Request }) => {
        const payload = typeof body === 'string' ? JSON.parse(body) : body;

        const {
            objectEntry: {
                id: requirementId,
                statusByUserId: userId,
                values: { linkTitle: ticket },
            },
        } = payload as any;

        const httpContext = {
            authorization: request.headers.get('authorization'),
            userId,
        };

        setTimeout(async () => {
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
                    requirementId,
                });
            }
        }, 2000);

        return 'ok';
    },
};

export default actions;
