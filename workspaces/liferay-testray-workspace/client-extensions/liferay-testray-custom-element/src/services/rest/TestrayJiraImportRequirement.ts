/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '~/core/Rest';

import yupSchema from '../../schema/yup';
import {TestrayJiraImportRequirement} from './types';

type JiraImportRequirement = typeof yupSchema.jiraImportRequirement.__outputType;

class TestrayJiraImportRequirementsImpl extends Rest<
	JiraImportRequirement,
	TestrayJiraImportRequirement
> {
	constructor() {
		super({
			adapter: ({
				issues,
				projectId: r_projectJiraImportRequirements_c_projectId,
			}) => ({
				issues,
				r_projectJiraImportRequirements_c_projectId,
			}),

			uri: 'jiraimportrequirements',
		});
	}
}

const testrayJiraImportRequirementImpl = new TestrayJiraImportRequirementsImpl();

export {testrayJiraImportRequirementImpl};
