/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {TaskStatuses} from '../../util/statuses';
import {testrayTaskUsersImpl} from './TestrayTaskUsers';
import {APIResponse, TestrayTask, UserAccount} from './types';

type TaskForm = typeof yupSchema.task.__outputType & {
	assignedUsers: string;
	dispatchTriggerId: number;
	projectId: number;
};

type NestedObjectOptions =
	| 'taskToSubtasks'
	| 'taskToTasksCaseTypes'
	| 'taskToTasksUsers';

class TestrayTaskImpl extends Rest<TaskForm, TestrayTask, NestedObjectOptions> {
	constructor() {
		super({
			adapter: ({
				assignedUsers,
				buildId: r_buildToTasks_c_buildId,

				dispatchTriggerId,
				dueStatus = TaskStatuses.OPEN,
				name,
			}) => ({
				assignedUsers,
				dispatchTriggerId,
				dueStatus,
				name,
				r_buildToTasks_c_buildId,
			}),
			nestedFields:
				'build.project,build.routine,taskToTasksUsers,r_userToTasksUsers_userId',
			transformData: (testrayTask) => ({
				...testrayTask,
				build: testrayTask.r_buildToTasks_c_build
					? {
							...testrayTask.r_buildToTasks_c_build,
							productVersion:
								testrayTask.r_buildToTasks_c_build
									.r_productVersionToBuilds_c_productVersion,
							project:
								testrayTask.r_buildToTasks_c_build
									.r_projectToBuilds_c_project,
							routine:
								testrayTask.r_buildToTasks_c_build
									.r_routineToBuilds_c_routine,
						}
					: undefined,
				users: testrayTask.taskToTasksUsers
					? testrayTask.taskToTasksUsers.map(
							({
								r_userToTasksUsers_user,
							}: {
								r_userToTasksUsers_user: UserAccount;
							}) => r_userToTasksUsers_user
						)
					: undefined,
			}),
			uri: 'tasks',
		});
	}

	public abandon(task: TestrayTask) {
		return this.fetcher.patch(
			`/${this.uri}/${task.id}`,
			this.adapter({
				dueStatus: TaskStatuses.ABANDONED,
			})
		);
	}

	public async assignTo(task: TestrayTask, userIds: number[]) {
		return testrayTaskUsersImpl.assign(task.id, userIds);
	}

	protected async beforeCreate(task: TaskForm): Promise<void> {
		await this.validate(task);
	}

	protected async beforeUpdate(id: number, task: TaskForm): Promise<void> {
		await this.validate(task, id);
	}

	public complete(task: TestrayTask) {
		return this.fetcher.patch(
			`/${this.uri}/${task.id}`,
			this.adapter({
				dueStatus: TaskStatuses.COMPLETE,
			})
		);
	}

	public async create(data: TaskForm): Promise<TestrayTask> {
		const task = await super.create(data);

		await this.fetcher.post(`/testray-testflow/${task.id}`);

		return task;
	}

	public getTasksByBuildId(buildId: number) {
		return this.fetcher<APIResponse<TestrayTask>>(
			`/tasks?filter=${SearchBuilder.eq('buildId', buildId)}`
		);
	}

	public async reanalyze(task: TestrayTask) {
		return this.fetcher.patch(
			`/${this.uri}/${task.id}`,
			this.adapter({
				dueStatus: TaskStatuses.IN_ANALYSIS,
			})
		);
	}

	protected async validate(task: TaskForm, id?: number) {
		const searchBuilder = new SearchBuilder({useURIEncode: true});

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		if (task.name) {
			searchBuilder.eq('name', task.name);
		}

		const filter = searchBuilder.build();

		const response = await this.fetcher<APIResponse<TestrayTask>>(
			`/tasks?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'tasks')
			);
		}
	}
}

export const testrayTaskImpl = new TestrayTaskImpl();
