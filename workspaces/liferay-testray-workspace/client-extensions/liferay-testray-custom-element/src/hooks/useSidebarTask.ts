/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import SearchBuilder from '../core/SearchBuilder';
import {Liferay} from '../services/liferay';
import {
	APIResponse,
	TestraySubtask,
	TestrayTaskUser,
	testraySubtaskImpl,
	testrayTaskUsersImpl,
} from '../services/rest';
import {SubtaskStatuses} from '../util/statuses';
import {useFetch} from './useFetch';

export function useSidebarTask() {
	const subtasksFilter = new SearchBuilder()
		.eq('userId', Liferay.ThemeDisplay.getUserId())
		.and()
		.ne('dueStatus', SubtaskStatuses.MERGED)
		.and()
		.ne('dueStatus', SubtaskStatuses.COMPLETE)
		.build();

	const taskFilters = new SearchBuilder()
		.eq('userId', Liferay.ThemeDisplay.getUserId())
		.build();

	const {data: tasksUserResponse} = useFetch<APIResponse<TestrayTaskUser>>(
		testrayTaskUsersImpl.resource,
		{
			params: {
				filter: taskFilters,
			},
			transformData: (response) =>
				testrayTaskUsersImpl.transformDataFromList(response),
		}
	);

	const {data: subtasksResponse} = useFetch<APIResponse<TestraySubtask>>(
		testraySubtaskImpl.resource,
		{
			params: {
				filter: subtasksFilter,
			},
			transformData: (response) =>
				testraySubtaskImpl.transformDataFromList(response),
		}
	);

	const subtasks = useMemo(() => subtasksResponse?.items || [], [
		subtasksResponse?.items,
	]);

	const tasks = useMemo(
		() =>
			(tasksUserResponse?.items || []).map(({task}) => ({
				...task,
				subtasks: subtasksResponse?.items.filter((subtask) => {
					return subtask?.task?.id === task?.id ? subtask : undefined;
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
