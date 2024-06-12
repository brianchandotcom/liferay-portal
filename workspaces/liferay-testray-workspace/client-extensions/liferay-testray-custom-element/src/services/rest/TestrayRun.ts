/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestrayRun} from './types';

type RunForm = Omit<typeof yupSchema.run.__outputType, 'id'>;

class TestrayRunImpl extends Rest<RunForm, TestrayRun> {
	constructor() {
		super({
			adapter: ({
				buildId: r_buildToRuns_c_buildId,
				description,
				environmentHash,
				name,
				number,
			}) => ({
				description,
				environmentHash,
				name,
				number,
				r_buildToRuns_c_buildId,
			}),
			nestedFields: 'build.routine,build.projectToBuilds',
			transformData: (run) => {
				const environmentValues = run.testrayRunName
					? run.testrayRunName.split('|')
					: run.name.split('|');

				const [
					applicationServer,
					browser,
					database,
					javaJDK,
					operatingSystem,
				] = environmentValues;

				return {
					...run,
					applicationServer,
					browser,
					build: run?.r_buildToRuns_c_build
						? {
								...run.r_buildToRuns_c_build,
								project:
									run.r_buildToRuns_c_build
										.r_projectToBuilds_c_project,
								routine:
									run.r_buildToRuns_c_build
										.r_routineToBuilds_c_routine,
							}
						: undefined,
					database,
					javaJDK,
					operatingSystem,
				};
			},
			uri: 'runs',
		});
	}
}

export const testrayRunImpl = new TestrayRunImpl();
