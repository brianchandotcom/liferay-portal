/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {atom, useAtomValue} from 'jotai';
import {useMemo} from 'react';

import SearchBuilder from '../core/SearchBuilder';
import {Liferay} from '../services/liferay';
import {
	APIResponse,
	TestraySubtask,
	TestrayTaskUser,
	testrayTaskUsersImpl,
} from '../services/rest';
import {SubtaskStatuses, TaskStatuses} from '../util/statuses';
import {useFetch} from './useFetch';

const taskFilters = new SearchBuilder()
	.eq('userId', Liferay.ThemeDisplay.getUserId())
	.and()
	.eq('taskToTasksUsers/dueStatus', TaskStatuses.IN_ANALYSIS)
	.build();

export const taskSidebarRefresh = atom(0);

export function useSidebarTask() {
	const refresh = useAtomValue(taskSidebarRefresh);
	const {data: tasksUserResponse} = useFetch<APIResponse<TestrayTaskUser>>(
		testrayTaskUsersImpl.resource + '&t=' + refresh,
		{
			params: {
				filter: taskFilters,
			},
			transformData: (response) =>
				testrayTaskUsersImpl.transformDataFromList(response),
		}
	);

	const {data: subtasksResponse} = useFetch<APIResponse<TestraySubtask>>(
		`/testray-testflow/testray-subtask?userId=${Liferay.ThemeDisplay.getUserId()}&status=${SubtaskStatuses.IN_ANALYSIS}&t=${refresh}`
	);

	const subtasks = useMemo(
		() => subtasksResponse?.items || [],
		[subtasksResponse?.items]
	);

	const tasks = useMemo(
		() =>
			(tasksUserResponse?.items || []).map(({task}) => ({
				...task,
				subtasks: subtasksResponse?.items.filter((subtask) => {
					return subtask?.testrayTaskId === task?.id
						? subtask
						: undefined;
				}),
			})),
		[subtasksResponse?.items, tasksUserResponse?.items]
	);

	return {
		subtasks,
		tasks,
		tasksUserResponse,
	};
}
