/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import yupSchema from '../../schema/yup';
import {getUniqueList, waitTimeout} from '../../util';
import {SubtaskStatuses} from '../../util/statuses';
import fetcher from '../fetcher';
import {Liferay} from '../liferay';
import {liferayMessageBoardImpl} from './LiferayMessageBoard';
import {testraySubtaskCaseResultImpl} from './TestraySubtaskCaseResults';
import {APIResponse, TestrayCaseResult, TestraySubtask} from './types';

type SubtaskForm = typeof yupSchema.subtask.__outputType & {
	projectId: number;
};

class TestraySubtaskImpl extends Rest<SubtaskForm, TestraySubtask> {
	public PREFIX = 'ST';
	public UNASSIGNED_USER_ID = 0;

	constructor() {
		super({
			adapter: ({
				dueStatus,
				errors,
				issues,
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
				issues,
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
			transformData: (subtask) => ({
				...subtask,
				caseResultIssues:
					subtask.subtaskToCaseResults?.reduce(
						(previousIssues: string[], subtaskCaseResult) => {
							const newIssues = subtaskCaseResult?.issues || '';

							return getUniqueList([
								...previousIssues,
								...(newIssues
									? newIssues
											.split(',')
											.map((name) => name.trim())
											.filter(Boolean)
									: []),
							]);
						},
						[]
					) || [],
				mergedToSubtask: subtask.r_mergedToTestraySubtask_c_subtask,
				splitFromSubtask: subtask.r_splitFromTestraySubtask_c_subtask,
				task: subtask.r_taskToSubtasks_c_task,
				tests: subtask.subtaskToCaseResults?.length,
				user: subtask.r_userToSubtasks_user,
			}),
			uri: 'subtasks',
		});
	}

	public async assignTo(subtask: TestraySubtask, userId: number) {
		const response = await this.update(subtask.id, {
			dueStatus: SubtaskStatuses.IN_ANALYSIS,
			userId,
		});

		return response;
	}

	public async assignToMe(subtask: TestraySubtask) {
		const assignToMeUpdate = await this.update(
			subtask.id || Number(subtask.subtaskId),
			{
				dueStatus: SubtaskStatuses.IN_ANALYSIS,
				userId: Number(Liferay.ThemeDisplay.getUserId()),
			}
		);

		return assignToMeUpdate;
	}

	private async addComment(data: Partial<SubtaskForm>) {
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

	public async complete(
		dueStatus: string,
		issues: string[],
		subtaskcomment: Partial<SubtaskForm>,
		subtaskId: number,
		userId: number
	) {
		const _issues = issues.length ? issues.join(', ') : '';

		if (subtaskcomment.comment) {
			const {mbMessage, mbThreadId} =
				await this.addComment(subtaskcomment);

			subtaskcomment.mbMessageId = mbMessage.id;
			subtaskcomment.mbThreadId = mbThreadId;
		}

		if (!subtaskcomment.comment && subtaskcomment.mbMessageId) {
			subtaskcomment.mbMessageId = 0;
		}

		const subtaskUpdate = await this.update(subtaskId, {
			dueStatus: SubtaskStatuses.COMPLETE,
			issues: _issues,
			mbMessageId: subtaskcomment.mbMessageId,
			mbThreadId: subtaskcomment.mbThreadId,
		});

		await fetcher.put(
			`/testray-testflow/by-testray-subtaskId/${subtaskId}`,
			{
				comment: subtaskcomment.comment,
				dueStatus,
				issues: _issues,
				mbMessageId: subtaskcomment.mbMessageId,
				mbThreadId: subtaskcomment.mbThreadId,
				userId,
			}
		);

		return subtaskUpdate;
	}

	public returnToOpen(subtask: TestraySubtask) {
		return this.update(subtask.id, {
			dueStatus: SubtaskStatuses.OPEN,
			userId: this.UNASSIGNED_USER_ID,
		});
	}

	public async mergedToSubtask(
		subtasks: TestraySubtask[]
	): Promise<APIResponse<TestraySubtask>> {
		return this.fetcher.put(
			`/testray-testflow/testray-subtask/merge`,
			subtasks
		);
	}

	public async split(
		selectedSubtaskCaseResults: TestrayCaseResult[],
		subtask: TestraySubtask,
		subtaskId: number,
		taskId: number
	) {
		const [subtaskResponse, currentSubtask] = await Promise.all([
			this.fetcher(
				`/${this.uri}?filter=${SearchBuilder.eq(
					'taskId',
					taskId
				)}&fields=number&pageSize=1&sort=number:desc`
			),
			this.getOne(subtaskId),
		]);

		const [{number: subtaskIndex}] = (
			subtaskResponse as APIResponse<TestraySubtask>
		)?.items || [{number: 1}];

		const newSubtaskScore = selectedSubtaskCaseResults
			.map(
				(caseResult) =>
					caseResult?.case?.priority ?? Number(caseResult?.priority)
			)
			.reduce(
				(previousValue, currentValue) => previousValue + currentValue
			);

		const newSubtaskIndex = subtaskIndex + 1;

		const newSubtask = await super.create({
			dueStatus: subtask?.dueStatus.key,
			errors: selectedSubtaskCaseResults[0]?.error || ' ',
			name: `${this.PREFIX}-${newSubtaskIndex}`,
			number: newSubtaskIndex,
			score: newSubtaskScore,
			splitFromSubtaskId: subtask?.id,
			taskId,
			userId: subtask?.user?.id,
		} as SubtaskForm);

		for (const {id} of selectedSubtaskCaseResults) {
			await testraySubtaskCaseResultImpl.update(id, {
				name: `${id}-${newSubtask.id}`,
				subtaskId: newSubtask.id,
			});
		}

		const updatedSubtask = await this.update(subtaskId, {
			dueStatus: currentSubtask?.dueStatus.key,
			score: (currentSubtask as TestraySubtask).score - newSubtaskScore,
		});

		return {currentSubtask: updatedSubtask, newSubtask};
	}
}

export const testraySubtaskImpl = new TestraySubtaskImpl();
