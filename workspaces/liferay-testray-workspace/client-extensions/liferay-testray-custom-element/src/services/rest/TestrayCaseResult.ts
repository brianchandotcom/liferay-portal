/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {waitTimeout} from '../../util';
import {CaseResultStatuses} from '../../util/statuses';
import {Liferay} from '../liferay';
import {liferayMessageBoardImpl} from './LiferayMessageBoard';
import {CaseResultAggregation, TestrayCaseResult} from './types';

type CaseResultForm = typeof yupSchema.caseResult.__outputType;

class TestrayCaseResultRest extends Rest<CaseResultForm, TestrayCaseResult> {
	public UNASSIGNED_USER_ID = 0;

	constructor() {
		super({
			adapter: ({
				buildId: r_buildToCaseResult_c_buildId,
				caseId: r_caseToCaseResult_c_caseId,
				comment,
				dueStatus,
				errors,
				issues,
				mbMessageId,
				mbThreadId,
				runId: r_runToCaseResult_c_runId,
				startDate,
				userId: r_userToCaseResults_userId = Liferay.ThemeDisplay.getUserId(),
			}) => ({
				comment,
				dueStatus,
				errors,
				issues,
				mbMessageId,
				mbThreadId,
				r_buildToCaseResult_c_buildId,
				r_caseToCaseResult_c_caseId,
				r_runToCaseResult_c_runId,
				r_userToCaseResults_userId,
				startDate,
			}),
			nestedFields:
				'case.caseType,component.team.name,team,build.productVersion,build.project,build.routine,run,user',
			nestedFieldsDepth: 2,
			transformData: (caseResult) => ({
				...caseResult,
				...this.normalizeCaseResultAggregation(caseResult),
				build: caseResult?.r_buildToCaseResult_c_build
					? {
							...caseResult?.r_buildToCaseResult_c_build,
							productVersion:
								caseResult.r_buildToCaseResult_c_build
									?.r_productVersionToBuilds_c_productVersion,
							project:
								caseResult.r_buildToCaseResult_c_build
									?.r_projectToBuilds_c_project,
							routine:
								caseResult.r_buildToCaseResult_c_build
									?.r_routineToBuilds_c_routine,
						}
					: undefined,
				case: caseResult?.r_caseToCaseResult_c_case
					? {
							...caseResult?.r_caseToCaseResult_c_case,
							caseType:
								caseResult?.r_caseToCaseResult_c_case
									?.r_caseTypeToCases_c_caseType,
						}
					: undefined,
				component: caseResult?.r_componentToCaseResult_c_component
					? {
							...caseResult.r_componentToCaseResult_c_component,
							team: caseResult.r_componentToCaseResult_c_component
								.r_teamToComponents_c_team,
						}
					: undefined,
				run: caseResult?.r_runToCaseResult_c_run
					? {
							...caseResult?.r_runToCaseResult_c_run,
							build: caseResult?.r_runToCaseResult_c_run?.build,
						}
					: undefined,
				runId: caseResult?.r_runToCaseResult_c_runId,
				user: caseResult?.r_userToCaseResults_user,
			}),
			uri: 'caseresults',
		});
	}

	public normalizeCaseResultAggregation(
		caseResultAggregation: CaseResultAggregation
	): CaseResultAggregation {
		return {
			caseResultBlocked: Number(
				caseResultAggregation?.caseResultBlocked ?? 0
			),
			caseResultFailed: Number(
				caseResultAggregation?.caseResultFailed ?? 0
			),
			caseResultInProgress: Number(
				caseResultAggregation?.caseResultInProgress ?? 0
			),
			caseResultIncomplete: Number(
				caseResultAggregation?.caseResultIncomplete ?? 0
			),
			caseResultPassed: Number(
				caseResultAggregation?.caseResultPassed ?? 0
			),
			caseResultTestFix: Number(
				caseResultAggregation?.caseResultTestFix ?? 0
			),
			caseResultUntested: Number(
				caseResultAggregation?.caseResultUntested ?? 0
			),
		};
	}

	public assignTo(caseResult: TestrayCaseResult, userId: number) {
		return this.update(
			caseResult.id || Number(caseResult.testrayCaseResultId),
			{
				userId,
			}
		);
	}

	public assignToMe(caseResult: TestrayCaseResult) {
		return this.update(
			caseResult.id || Number(caseResult.testrayCaseResultId),
			{
				userId: Number(Liferay.ThemeDisplay.getUserId()),
			}
		);
	}

	public async exportCaseResults(buildId: number) {
		const response = await Liferay.Util.fetch(
			`/o/testray-rest/v1.0/testray-export-case-result/${buildId}`
		);

		const responseHeaders = response.headers.get('Content-Disposition');

		if (response.ok && responseHeaders?.includes('attachment')) {
			const downloadElement = document.createElement('a');

			downloadElement.download =
				responseHeaders.match(/filename="([^"]+)"/)![1];
			downloadElement.href = URL.createObjectURL(await response.blob());

			document.body.appendChild(downloadElement);
			downloadElement.click();
		}
	}

	public removeAssign(caseResult: TestrayCaseResult) {
		return this.update(
			caseResult.id || Number(caseResult.testrayCaseResultId),
			{
				userId: this.UNASSIGNED_USER_ID,
			}
		);
	}

	public reopenTest(caseResult: TestrayCaseResult) {
		return this.update(caseResult.id, {
			dueStatus: CaseResultStatuses.UNTESTED,
		});
	}

	public resetTest(caseResult: TestrayCaseResult) {
		return this.update(caseResult.id, {
			dueStatus: CaseResultStatuses.UNTESTED,
			userId: this.UNASSIGNED_USER_ID,
		});
	}

	public startTest(caseResult: TestrayCaseResult) {
		return this.update(caseResult.id, {
			dueStatus: CaseResultStatuses.IN_PROGRESS,
			startDate: caseResult.startDate,
		});
	}

	private async addComment(data: Partial<CaseResultForm>) {
		try {
			const message = data.comment as string;
			let mbThreadId = data.mbThreadId;

			if (!mbThreadId) {
				const mbThread =
					await liferayMessageBoardImpl.createMbThread(message);

				mbThreadId = mbThread.id;

				await waitTimeout(1500);
			}

			const mbMessage = await liferayMessageBoardImpl.createMbMessage(
				message,
				mbThreadId as number
			);

			return {mbMessage, mbThreadId};
		}
		catch {
			return {};
		}
	}

	public async update(
		id: number,
		data: Partial<
			CaseResultForm & {
				defaultMessageId?: number;
			}
		>
	): Promise<TestrayCaseResult> {
		if (data.comment) {
			const {mbMessage, mbThreadId} = await this.addComment(data);

			data.mbMessageId = mbMessage.id;
			data.mbThreadId = mbThreadId;
		}

		if (!data.comment && data.mbMessageId) {
			data.mbMessageId = data.defaultMessageId ?? 0;
		}

		return super.update(id, {...data});
	}
}

export const testrayCaseResultImpl = new TestrayCaseResultRest();
