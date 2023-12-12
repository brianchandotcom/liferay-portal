/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import yupSchema from '../../schema/yup';
import {waitTimeout} from '../../util';
import {CaseResultStatuses, SubTaskStatuses} from '../../util/statuses';
import {Liferay} from '../liferay';
import {liferayMessageBoardImpl} from './LiferayMessageBoard';
import {testrayCaseResultImpl} from './TestrayCaseResult';
import {testrayIssueImpl} from './TestrayIssues';
import {testraySubtaskCaseResultImpl} from './TestraySubtaskCaseResults';
import {testraySubtaskIssuesImpl} from './TestraySubtaskIssues';
import {APIResponse, TestraySubTask, TestraySubTaskCaseResult} from './types';

type SubtaskForm = typeof yupSchema.subtask.__outputType & {
	projectId: number;
};

class TestraySubtaskImpl extends Rest<SubtaskForm, TestraySubTask> {
	public PREFIX = 'ST';
	public UNASSIGNED_USER_ID = 0;

	constructor() {
		super({
			adapter: ({
				dueStatus,
				errors,
				mbMessageId,
				mbThreadId,
				mergedToSubtaskId: r_mergedToTestraySubtask_c_subtaskId,
				name,
				number,
				score,
				splitFromSubtaskId: r_splitFromTestraySubtask_c_subtaskId,
				taskId: r_taskToSubtasks_c_taskId,
				userId: r_userToSubtasks_userId,
			}) => ({
				dueStatus,
				errors,
				mbMessageId,
				mbThreadId,
				name,
				number,
				r_mergedToTestraySubtask_c_subtaskId,
				r_splitFromTestraySubtask_c_subtaskId,
				r_taskToSubtasks_c_taskId,
				r_userToSubtasks_userId,
				score,
			}),
			nestedFields: 'tasks,users,subtask',
			transformData: (subTask) => ({
				...subTask,
				mergedToSubtaskId: subTask.r_mergedToTestraySubtask_c_subtaskId,
				splitFromSubtask: subTask.r_splitFromTestraySubtask_c_subtask,
				task: subTask.r_taskToSubtasks_c_task,
				user: subTask.r_userToSubtasks_user,
			}),
			uri: 'subtasks',
		});
	}

	private async getCaseResultsFromSubtask(subTaskId: number) {
		const subTaskCaseResultResponse = await testraySubtaskCaseResultImpl.getAll(
			{filter: SearchBuilder.eq('subtaskId', subTaskId)}
		);

		if (!subTaskCaseResultResponse) {
			return [];
		}

		const subTaskCaseResults =
			testraySubtaskCaseResultImpl.transformDataFromList(
				subTaskCaseResultResponse
			)?.items || [];

		return subTaskCaseResults;
	}

	public async assignTo(subTask: TestraySubTask, userId: number) {
		const caseResults = await this.getCaseResultsFromSubtask(subTask.id);

		const caseResultIds = caseResults.map((caseResult) =>
			Number(caseResult.caseResult?.id)
		);

		const response = await this.update(subTask.id, {
			dueStatus: SubTaskStatuses.IN_ANALYSIS,
			userId,
		});

		await testrayCaseResultImpl.updateBatch(
			caseResultIds,
			caseResultIds.map(() => ({
				dueStatus: CaseResultStatuses.IN_PROGRESS,
				userId,
			}))
		);

		return response;
	}

	public async assignToMe(subTask: TestraySubTask) {
		const assignToMeUpdate = await this.update(subTask.id, {
			dueStatus: SubTaskStatuses.IN_ANALYSIS,
			userId: Number(Liferay.ThemeDisplay.getUserId()),
		});

		const caseResults = await this.getCaseResultsFromSubtask(subTask.id);

		const caseResultIds = caseResults.map((caseResult) =>
			Number(caseResult.caseResult?.id)
		);

		const userId = Number(Liferay.ThemeDisplay.getUserId());

		await testrayCaseResultImpl.updateBatch(
			caseResultIds,
			caseResultIds.map(() => ({userId}))
		);

		return assignToMeUpdate;
	}

	private async addComment(data: Partial<SubtaskForm>) {
		try {
			const message = data.comment as string;
			let mbThreadId = data.mbThreadId;

			if (!mbThreadId) {
				const mbThread = await liferayMessageBoardImpl.createMbThread(
					message
				);

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

	public async complete(
		dueStatus: string,
		issues: string[],
		subTaskcomment: Partial<SubtaskForm>,
		subTaskId: number
	) {
		const subtaskIssuesResponse = await testraySubtaskIssuesImpl.getAll({
			filter: SearchBuilder.eq('subtaskId', subTaskId),
		});

		for (const issue of issues) {
			const testrayIssue = await testrayIssueImpl.createIfNotExist({
				name: issue,
			});

			await testraySubtaskIssuesImpl.createIfNotExist({
				issueId: testrayIssue?.id,
				name: `${issue}${testrayIssueImpl.DELIMITER}${subTaskId}`,
				subTaskId,
			});
		}

		if (subtaskIssuesResponse?.items) {
			const caseResultIssuesTransform = await testraySubtaskIssuesImpl.transformDataFromList(
				subtaskIssuesResponse
			);

			const subtaskIssueIdsToRemove = caseResultIssuesTransform.items
				.filter(({issue}) => !issues.includes(issue?.name || ''))
				.map(({id}) => id);

			for (const caseResultIssueId of subtaskIssueIdsToRemove) {
				await testraySubtaskIssuesImpl.remove(caseResultIssueId);
			}
		}

		if (subTaskcomment.comment) {
			const {mbMessage, mbThreadId} = await this.addComment(
				subTaskcomment
			);

			subTaskcomment.mbMessageId = mbMessage.id;
			subTaskcomment.mbThreadId = mbThreadId;
		}

		if (!subTaskcomment.comment && subTaskcomment.mbMessageId) {
			subTaskcomment.mbMessageId = 0;
		}

		const subTaskUpdate = await this.update(subTaskId, {
			dueStatus: SubTaskStatuses.COMPLETE,
			mbMessageId: subTaskcomment.mbMessageId,
			mbThreadId: subTaskcomment.mbThreadId,
		});

		const caseResults = await this.getCaseResultsFromSubtask(subTaskId);

		const caseResultIds = caseResults.map((caseResult) =>
			Number(caseResult.caseResult?.id)
		);

		await testrayCaseResultImpl.updateBatch(
			caseResultIds,
			caseResultIds.map(() => ({
				defaultMessageId: subTaskcomment.mbMessageId,
				dueStatus,
				mbMessageId: subTaskcomment.mbMessageId,
				mbThreadId: subTaskcomment.mbThreadId,
			}))
		);

		for (const caseResultId of caseResultIds) {
			await testrayCaseResultImpl.assignCaseResultIssue(
				caseResultId,
				issues
			);
		}

		return subTaskUpdate;
	}

	public returnToOpen(subTask: TestraySubTask) {
		return this.update(subTask.id, {
			dueStatus: SubTaskStatuses.OPEN,
			userId: this.UNASSIGNED_USER_ID,
		});
	}

	public async mergedToSubtask(subTasks: TestraySubTask[]) {
		const [parentTestraySubtask, ...childTestraySubtasks] = subTasks.sort(
			({score: scoreA}, {score: scoreB}) => scoreB - scoreA
		);

		let sumScore = parentTestraySubtask.score ?? 0;

		for (const testraySubTask of childTestraySubtasks) {
			await this.update(testraySubTask.id, {
				dueStatus: SubTaskStatuses.MERGED,
				mergedToSubtaskId: parentTestraySubtask.id,
				score: 0,
			});

			const caseResults = await this.getCaseResultsFromSubtask(
				testraySubTask.id
			);

			for (const caseResult of caseResults) {
				sumScore += caseResult?.caseResult?.case?.priority || 0;

				await testraySubtaskCaseResultImpl.update(caseResult.id, {
					name: `${parentTestraySubtask.id}`,
					subtaskId: parentTestraySubtask.id,
				});
			}
		}

		await this.update(parentTestraySubtask.id, {
			dueStatus: parentTestraySubtask.dueStatus.key,
			score: sumScore,
		});
	}

	public async split(
		selectedSubTaskCaseResults: TestraySubTaskCaseResult[],
		subTaskId: number,
		taskId: number
	) {
		const [subtaskResponse, currentSubtask] = await Promise.all([
			this.fetcher(
				`/${this.uri}?filter=${SearchBuilder.eq(
					'taskId',
					taskId
				)}&fields=number&pageSize=1&sort=number:desc`
			),
			this.getOne(subTaskId),
		]);

		const [{number: subtaskIndex}] = (subtaskResponse as APIResponse<
			TestraySubTask
		>)?.items || [{number: 1}];

		const [selectedSubTask] = selectedSubTaskCaseResults.map(
			({subTask}) => subTask as TestraySubTask
		);

		const newSubtaskScore = selectedSubTaskCaseResults
			.map(({caseResult}) => caseResult?.case?.priority ?? 0)
			.reduce((prev, next) => prev + next);

		const newSubtaskIndex = subtaskIndex + 1;

		const newSubtask = await super.create({
			dueStatus: selectedSubTask.dueStatus.key,
			errors: selectedSubTaskCaseResults[0]?.caseResult?.errors || ' ',
			name: `${this.PREFIX}-${newSubtaskIndex}`,
			number: newSubtaskIndex,
			score: newSubtaskScore,
			splitFromSubtaskId: selectedSubTask.id,
			taskId,
			userId: selectedSubTask.user?.id,
		} as SubtaskForm);

		for (const {id} of selectedSubTaskCaseResults) {
			await testraySubtaskCaseResultImpl.update(id, {
				name: `${id}-${newSubtask.id}`,
				subtaskId: newSubtask.id,
			});
		}

		const updatedSubtask = await this.update(subTaskId, {
			dueStatus: currentSubtask?.dueStatus.key,
			score: (currentSubtask as TestraySubTask).score - newSubtaskScore,
		});

		return {currentSubtask: updatedSubtask, newSubtask};
	}
}

export const testraySubTaskImpl = new TestraySubtaskImpl();
