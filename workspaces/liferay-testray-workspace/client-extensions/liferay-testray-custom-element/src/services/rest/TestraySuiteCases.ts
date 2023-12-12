/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '~/core/Rest';
import yupSchema from '~/schema/yup';

import {TestraySuiteCase} from './types';

type SuiteCase = typeof yupSchema.suiteCase.__outputType;

class TestraySuiteImpl extends Rest<SuiteCase, TestraySuiteCase> {
	constructor() {
		super({
			adapter: ({
				caseId: r_caseToSuitesCases_c_caseId,
				name,
				suiteId: r_suiteToSuitesCases_c_suiteId,
			}) => ({
				name,
				r_caseToSuitesCases_c_caseId,
				r_suiteToSuitesCases_c_suiteId,
			}),
			nestedFields: 'case.component,suite',
			uri: 'suitescaseses',
		});
	}

	public async createSuiteCase(casesId: number[], suiteId: number) {
		for (const caseId of casesId) {
			await super.create({
				caseId,
				name: new Date().getTime() + String(caseId),
				suiteId,
			});
		}
	}
}

export const testraySuiteCaseImpl = new TestraySuiteImpl();
