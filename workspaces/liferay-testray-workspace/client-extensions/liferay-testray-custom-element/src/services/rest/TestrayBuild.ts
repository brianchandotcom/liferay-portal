/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TestrayBuildsCases} from '.';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import {CategoryOptions} from '../../pages/Project/Routines/Builds/BuildForm/Stack/RunsList';
import yupSchema from '../../schema/yup';
import {CaseResultStatuses} from '../../util/statuses';
import fetcher from '../fetcher';
import {testrayCaseResultImpl} from './TestrayCaseResult';
import {testrayFactorRest} from './TestrayFactor';
import {testrayRunImpl} from './TestrayRun';

import type {
	APIResponse,
	TestrayBuild,
	TestrayRoutine,
	TestrayRun,
} from './types';

type Build = typeof yupSchema.build.__outputType & {projectId: number};

class TestrayBuildImpl extends Rest<Build, TestrayBuild> {
	constructor() {
		super({
			adapter: ({
				description,
				dueStatus,
				gitHash,
				name,
				productVersionId: r_productVersionToBuilds_c_productVersionId,
				projectId: r_projectToBuilds_c_projectId,
				promoted,
				routineId: r_routineToBuilds_c_routineId,
				template,
				templateTestrayBuildId,
			}) => ({
				archived: false,
				description,
				dueStatus,
				gitHash,
				name,
				promoted,
				r_productVersionToBuilds_c_productVersionId,
				r_projectToBuilds_c_projectId,
				r_routineToBuilds_c_routineId,
				template,
				templateTestrayBuildId,
			}),
			nestedFields: 'buildToTasks,productVersion',
			transformData: (testrayBuild) => ({
				...testrayBuild,
				...testrayCaseResultImpl.normalizeCaseResultAggregation(
					testrayBuild
				),
				creator: testrayBuild?.creator || {},
				productVersion:
					testrayBuild?.r_productVersionToBuilds_c_productVersion,
				tasks: testrayBuild.buildToTasks ?? [],
			}),
			uri: 'builds',
		});
	}

	public async create(data: Build): Promise<TestrayBuild> {
		const build = await super.create(data);

		const caseIds = data.caseIds || [];
		const runs = data.factorStacks || [];

		let runIndex = 1;

		for (const run of runs) {
			const factorOptions = (
				Object.values(run) as CategoryOptions[]
			).filter(Boolean);

			const factorOptionsList = factorOptions
				.filter(({factorOption}) => Boolean(factorOption))
				.map(({factorOption}) => factorOption);

			const testrayRunName = factorOptionsList.join('|');

			if (!testrayRunName) {
				continue;
			}

			const testrayRun = await testrayRunImpl.create({
				buildId: build.id,
				description: undefined,
				environmentHash: testrayRunName,
				name: testrayRunName,
				number: runIndex,
			});

			for (const factorOption of factorOptions) {
				if (
					factorOption.factorCategoryId &&
					factorOption.factorOptionId
				) {
					await testrayFactorRest.create({
						factorCategoryId:
							factorOption.factorCategoryId?.toString(),
						factorOptionId: factorOption.factorOptionId?.toString(),
						name: '',
						routineId: undefined,
						runId: testrayRun.id,
					});
				}
			}

			await fetcher.post(
				'/buildscaseses/batch',
				caseIds.map((caseId) => ({
					r_buildToBuildsCases_c_buildId: build.id,
					r_caseToBuildsCases_c_caseId: caseId,
				}))
			);

			await testrayCaseResultImpl.createBatch(
				caseIds.map((caseId) => ({
					buildId: build.id,
					caseId,
					comment: undefined,
					dueStatus: CaseResultStatuses.UNTESTED,
					errors: undefined,
					issues: undefined,
					mbMessageId: 0,
					mbThreadId: 0,
					runId: testrayRun.id,
					startDate: undefined,
					userId: testrayCaseResultImpl.UNASSIGNED_USER_ID,
				}))
			);

			runIndex++;
		}

		return build;
	}

	public async hasBuildsInProjectId(projectId: number): Promise<boolean> {
		const routineResponse = await this.fetcher<APIResponse<TestrayRoutine>>(
			`/routines?filter=${SearchBuilder.eq(
				'projectId',
				projectId
			)}&fields=id`
		);

		const [routine] = routineResponse?.items || [];

		if (!routine) {
			return false;
		}

		const buildResponse = await this.fetcher<APIResponse<TestrayBuild>>(
			`/${this.uri}?filter=${SearchBuilder.eq(
				'routineId',
				routine.id
			)}&fields=id`
		);

		return !!buildResponse?.totalCount;
	}

	public async archiveUpdate(id: number, archived: boolean | undefined) {
		await this.fetcher.patch(`/builds/${id}`, {
			archived: !archived,
		});
	}

	public async autofill(objectEntryId1: number, objectEntryId2: number) {
		const response = await this.fetcher.post(
			`/testray-build-autofill/${objectEntryId1}/${objectEntryId2}`
		);

		return response;
	}

	public async getCurrentCaseIds(buildId: string | number) {
		const response = await this.fetcher(
			`/buildscaseses?filter=${SearchBuilder.eq(
				'buildId',
				buildId
			)}&pageSize=1000&fields=r_caseToBuildsCases_c_caseId`
		);

		const caseIds: number[] =
			response?.items.map(
				(item: TestrayBuildsCases) => item.r_caseToBuildsCases_c_caseId
			) || [];

		return caseIds;
	}

	public async updateBuild(
		id: number,
		data: Partial<Build>,
		runItems: TestrayRun[]
	): Promise<TestrayBuild> {
		const getLastRunNumber = () => {
			const runs = runItems?.map((item: TestrayRun) => item.number) || [];

			return runs[runs?.length - 1];
		};

		let runNumber = getLastRunNumber() as unknown as number;

		for (const run of data.runOptions) {
			const runId = run?.runId;
			const fieldId = run?.id;

			const runName = Object.values(run)
				.filter((key, _) => key !== runId && key !== id)
				.join('|');

			if (!runName) {
				continue;
			}

			if (!runId) {
				await testrayRunImpl.create({
					buildId: id,
					description: undefined,
					environmentHash: runName,
					name: runName,
					number: runNumber + 1,
				});

				runNumber++;
			}

			if (fieldId && runId) {
				await testrayRunImpl.update(runId, {
					...run,
					environmentHash: runName,
					name: runName,
				});
			}
		}

		return this.update(id, data);
	}

	public async updateBuildSummary(id: string) {
		return fetcher.patch(`/testray-build/${id}`, null);
	}

	public async updateArchivedFlag(id: number, archived: boolean) {
		const dateArchived = archived ? new Date().toISOString() : '';

		await this.fetcher.patch(`/builds/${id}`, {
			archived,
			dateArchived,
		});
	}
}

export const testrayBuildImpl = new TestrayBuildImpl();
