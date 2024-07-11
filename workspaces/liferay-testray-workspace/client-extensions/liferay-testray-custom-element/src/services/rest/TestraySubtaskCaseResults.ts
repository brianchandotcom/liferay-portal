/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestrayCaseResult} from './types';

type SubtaskCaseResultForm = typeof yupSchema.subtaskToCaseResult.__outputType;

class TestraySubtaskCaseResultImpl extends Rest<
	SubtaskCaseResultForm,
	TestrayCaseResult
> {
	constructor() {
		super({
			adapter: ({
				issues,
				subtaskId: r_subtaskToCaseResults_c_subtaskId,
			}) => ({
				issues,
				r_subtaskToCaseResults_c_subtaskId,
			}),
			nestedFields:
				'case.caseType,component.team.name,team,build.project,build.routine,run,user,subtask',
			transformData: (caseResult) => ({
				...caseResult,
				id: caseResult.testrayCaseResultId ?? 0,
			}),
			uri: 'caseresults',
		});
	}
}

export const testraySubtaskCaseResultImpl = new TestraySubtaskCaseResultImpl();
