/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HttpContext} from '../../lib/Types';
import {
	JIRA_APP_FIELD_ID_QA_TEST_NAME,
	JIRA_APP_FIELD_ID_QA_TEST_SCORE,
} from '../../utils/env';
import Testray from '../liferay/Testray';
import Jira from './Jira';

const jira = new Jira();
const testray = new Testray();

class JiraEngine {
	public async updateIssues(issues: string[], httpContext: HttpContext) {
		const testrayIssues = await testray.getIssues(issues, httpContext);

		for (const testrayIssue of testrayIssues) {
			let totalPriority = 0;

			const testrayCaseNames = new Set<string>();

			for (const {
				r_caseResultToCaseResultsIssues_c_caseResult: caseResult,
			} of testrayIssue.issueToCaseResultsIssues) {
				const {r_caseToCaseResult_c_case: testrayCase} = caseResult;

				const caseName = testrayCase.name.replace(' ', '_');

				if (testrayCaseNames.has(caseName)) {
					continue;
				}

				totalPriority += testrayCase.priority;

				testrayCaseNames.add(caseName);
			}

			const payload = {
				fields: {
					[JIRA_APP_FIELD_ID_QA_TEST_NAME as string]: [
						...testrayCaseNames,
					],
					[JIRA_APP_FIELD_ID_QA_TEST_SCORE as string]: totalPriority,
				},
			};

			await jira.updateIssue(testrayIssue.name, payload, httpContext);
		}
	}
}

export default JiraEngine;
