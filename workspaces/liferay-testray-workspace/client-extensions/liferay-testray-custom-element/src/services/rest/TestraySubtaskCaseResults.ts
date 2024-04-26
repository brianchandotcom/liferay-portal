/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestraySubTaskCaseResult} from './types';

type SubtaskCaseResultForm = typeof yupSchema.subtaskToCaseResult.__outputType;

class TestraySubtaskCaseResultImpl extends Rest<
	SubtaskCaseResultForm,
	TestraySubTaskCaseResult
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
				'case.caseType,component.team.name,team,build.project,build.routine,run,user',
			transformData: (caseResult) => ({
				...caseResult,
				build: caseResult?.r_buildToCaseResult_c_build
					? {
							...caseResult?.r_buildToCaseResult_c_build,
							project:
								caseResult?.r_buildToCaseResult_c_build
									?.r_projectToBuilds_c_project,
							routine:
								caseResult.r_buildToCaseResult_c_build
									?.r_routineToBuilds_c_routine,
					  }
					: undefined,
				case: caseResult?.r_caseToCaseResult_c_case
					? {
							...caseResult?.r_caseToCaseResult_c_case,

							component: caseResult?.r_caseToCaseResult_c_case
								?.r_componentToCases_c_component
								? {
										...caseResult?.r_caseToCaseResult_c_case
											?.r_componentToCases_c_component,
										team:
											caseResult
												?.r_caseToCaseResult_c_case
												.r_componentToCases_c_component
												.r_teamToComponents_c_team,
								  }
								: undefined,
					  }
					: undefined,
				component: caseResult?.r_componentToCaseResult_c_component
					? {
							...caseResult.r_componentToCaseResult_c_component,
							team:
								caseResult.r_componentToCaseResult_c_component
									.r_teamToComponents_c_team,
					  }
					: undefined,
				run: caseResult?.r_runToCaseResult_c_run,
			}),
			uri: 'caseresults',
		});
	}
}

export const testraySubtaskCaseResultImpl = new TestraySubtaskCaseResultImpl();
