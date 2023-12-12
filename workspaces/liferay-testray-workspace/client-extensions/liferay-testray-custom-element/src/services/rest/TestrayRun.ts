/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import yupSchema from '../../schema/yup';
import {DISPATCH_TRIGGER_TYPE} from '../../util/enum';
import {DispatchTriggerStatuses} from '../../util/statuses';
import {liferayDispatchTriggerImpl} from './LiferayDispatchTrigger';
import {testrayCaseResultImpl} from './TestrayCaseResult';
import {testrayDispatchTriggerImpl} from './TestrayDispatchTrigger';
import {APIResponse, TestrayRun} from './types';

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
				const environmentValues = run.name.split('|');

				const [
					applicationServer,
					browser,
					database,
					javaJDK,
					operatingSystem,
				] = environmentValues;

				return {
					...run,
					...testrayCaseResultImpl.normalizeCaseResultAggregation(
						run
					),
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

	public async autofill(
		objectEntryId1: number,
		objectEntryId2: number,
		autofillType: 'Build' | 'Run'
	) {
		const name = `AUTOFILL-${objectEntryId1}/${objectEntryId2}-${autofillType}-${new Date().getTime()}`;

		if (autofillType === 'Build') {
			const response = await this.getAll({
				filter: SearchBuilder.in('id', [
					objectEntryId1,
					objectEntryId2,
				]),
			});

			const [runA, runB] =
				this.transformDataFromList(response as APIResponse<TestrayRun>)
					?.items ?? [];

			objectEntryId1 = runA.build?.id as number;
			objectEntryId2 = runB.build?.id as number;
		}

		const response = await liferayDispatchTriggerImpl.create({
			active: true,
			dispatchTaskExecutorType: DISPATCH_TRIGGER_TYPE.AUTO_FILL,
			dispatchTaskSettings: {
				autofillType,
				objectEntryId1,
				objectEntryId2,
			},
			externalReferenceCode: name,
			name,
			overlapAllowed: false,
		});

		const body = {
			dueStatus: DispatchTriggerStatuses.INPROGRESS,
			output: '',
		};

		try {
			await liferayDispatchTriggerImpl.run(
				response.liferayDispatchTrigger.id
			);
		}
		catch (error) {
			body.dueStatus = DispatchTriggerStatuses.FAILED;
			body.output = (error as TestrayError)?.message;
		}

		await testrayDispatchTriggerImpl.update(
			response.testrayDispatchTrigger.id,
			body
		);
	}
}

export const testrayRunImpl = new TestrayRunImpl();
