/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InferType} from 'yup';

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestrayTaskCaseTypes} from './types';

type TaskToCaseTypes = InferType<typeof yupSchema.taskToCaseTypes>;

class TestrayTaskCaseTypesImpl extends Rest<
	TaskToCaseTypes,
	TestrayTaskCaseTypes
> {
	constructor() {
		super({
			adapter: ({
				caseTypeId: r_caseTypeToTasksCaseTypes_c_caseTypeId,
				name: name,
				taskId: r_taskToTasksCaseTypes_c_taskId,
			}) => ({
				name,
				r_caseTypeToTasksCaseTypes_c_caseTypeId,
				r_taskToTasksCaseTypes_c_taskId,
			}),
			nestedFields: 'caseType,task.build.project,task.build.routine,',
			transformData: (taskCaseType) => ({
				...taskCaseType,
				caseType: taskCaseType.r_caseTypeToTasksCaseTypes_c_caseType,
				task: taskCaseType.r_taskToTasksCaseTypes_c_taskId
					? {
							...taskCaseType.r_taskToTasksCaseTypes_c_taskId,
							build: taskCaseType?.r_taskToTasksCaseTypes_c_taskId
								?.r_buildToTasks_c_build
								? {
										...taskCaseType
											?.r_taskToTasksCaseTypes_c_taskId
											?.r_buildToTasks_c_build,
										project:
											taskCaseType
												?.r_taskToTasksCaseTypes_c_taskId
												?.r_buildToTasks_c_build
												?.r_projectToBuilds_c_project,
										routine:
											taskCaseType
												?.r_taskToTasksCaseTypes_c_taskId
												?.r_buildToTasks_c_build
												?.r_routineToBuilds_c_routine,
								  }
								: undefined,
					  }
					: undefined,
			}),
			uri: 'taskscasetypeses',
		});
	}
}

const testrayTaskCaseTypesImpl = new TestrayTaskCaseTypesImpl();

export {testrayTaskCaseTypesImpl};
