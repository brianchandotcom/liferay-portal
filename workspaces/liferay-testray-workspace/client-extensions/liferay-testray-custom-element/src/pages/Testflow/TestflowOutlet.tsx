/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useAtomValue} from 'jotai';
import {useEffect} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';
import PageRenderer from '~/components/PageRenderer';
import {taskSidebarRefresh} from '~/hooks/useSidebarTask';

import SearchBuilder from '../../core/SearchBuilder';
import {useFetch} from '../../hooks/useFetch';
import useHeader from '../../hooks/useHeader';
import useSearchBuilder from '../../hooks/useSearchBuilder';
import i18n from '../../i18n';
import {
	APIResponse,
	TestraySubtask,
	TestrayTask,
	TestrayTaskCaseTypes,
	TestrayTaskUser,
	testraySubtaskImpl,
	testrayTaskImpl,
	testrayTaskUsersImpl,
} from '../../services/rest';
import {testrayTaskCaseTypesImpl} from '../../services/rest/TestrayTaskCaseTypes';
import {SubtaskStatuses, TaskStatuses} from '../../util/statuses';
import TestflowLoading from './TestflowLoading';

const TestflowNavigationOutlet = () => {
	const {pathname} = useLocation();

	const currentPathIsActive = pathname === '/testflow';
	const archivedPathIsActive = pathname === '/testflow/archived';

	const {setTabs} = useHeader({
		dropdown: [],
		headerActions: {
			actions: [],
		},
		heading: [
			{
				category: i18n.translate('task').toUpperCase(),
				title: i18n.translate('testflow'),
			},
		],
		shouldUpdate: currentPathIsActive || archivedPathIsActive,
	});

	useEffect(() => {
		setTabs([
			{
				active: currentPathIsActive,
				path: '/testflow',
				title: i18n.translate('current'),
			},
			{
				active: archivedPathIsActive,
				path: '/testflow/archived',
				title: i18n.translate('archived'),
			},
		]);
	}, [archivedPathIsActive, currentPathIsActive, setTabs]);

	return <Outlet />;
};

const TestflowOutlet = () => {
	const params = useParams();
	const refresh = useAtomValue(taskSidebarRefresh);

	const taskId = params.taskId as string;

	const {
		data: testrayTask,
		error,
		loading,
		mutate: mutateTask,
	} = useFetch<TestrayTask>(
		testrayTaskImpl.getResource(taskId) + '&t=' + refresh,
		{
			transformData: (response) =>
				testrayTaskImpl.transformData(response),
		}
	);
	const projectId = String(testrayTask?.build?.project?.id);

	const {data: testrayTaskCaseTypes} = useFetch<
		APIResponse<TestrayTaskCaseTypes>
	>(testrayTaskCaseTypesImpl.resource, {
		params: {
			filter: SearchBuilder.eq('taskId', taskId),
		},
		transformData: (response) =>
			testrayTaskCaseTypesImpl.transformDataFromList(response),
	});

	const {data: testrayTaskUser, revalidate: revalidateTaskUser} = useFetch<
		APIResponse<TestrayTaskUser>
	>(testrayTaskUsersImpl.resource, {
		params: {
			filter: SearchBuilder.eq('taskId', taskId),
			nestedFields: 'task,user',
		},
		transformData: (response) =>
			testrayTaskUsersImpl.transformDataFromList(response),
	});

	const searchBuilder = useSearchBuilder({useURIEncode: false});

	const subtaskFilter = searchBuilder
		.eq('taskId', taskId)
		.and()
		.in('dueStatus', [SubtaskStatuses.IN_ANALYSIS, SubtaskStatuses.OPEN])
		.build();

	const {data: testraySubtasks, revalidate: revalidateSubtask} = useFetch<
		APIResponse<TestraySubtask>
	>(testraySubtaskImpl.resource, {
		params: {
			fields: 'id',
			filter: subtaskFilter,
			pageSize: 1,
		},
	});

	return (
		<PageRenderer error={error} loading={loading}>
			{[TaskStatuses.PROCESSING, TaskStatuses.OPEN].includes(
				(testrayTask as TestrayTask)?.dueStatus.key as TaskStatuses
			) ? (
				<TestflowLoading
					mutateTask={mutateTask}
					testrayTask={testrayTask as TestrayTask}
				/>
			) : (
				<Outlet
					context={{
						actions: testrayTask?.actions,
						data: {
							projectId,
							testraySubtasks,
							testrayTask,
							testrayTaskCaseTypes:
								testrayTaskCaseTypes?.items ?? [],
							testrayTaskUser: testrayTaskUser?.items ?? [],
						},
						mutate: {
							mutateTask,
						},
						revalidate: {
							revalidateSubtask,
							revalidateTaskUser,
						},
					}}
				/>
			)}
		</PageRenderer>
	);
};

export {TestflowNavigationOutlet};

export default TestflowOutlet;
