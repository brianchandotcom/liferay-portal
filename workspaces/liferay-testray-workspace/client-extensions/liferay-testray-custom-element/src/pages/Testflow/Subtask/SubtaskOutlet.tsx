/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {Outlet, useOutletContext, useParams} from 'react-router-dom';

import SearchBuilder from '../../../core/SearchBuilder';
import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestraySubTask,
	TestrayTask,
	liferayMessageBoardImpl,
	testraySubTaskImpl,
} from '../../../services/rest';
import {testraySubtaskIssuesImpl} from '../../../services/rest/TestraySubtaskIssues';

type OutletContext = {
	data: {
		testrayTask: TestrayTask;
	};
	revalidate: {
		revalidateTaskUser: () => void;
	};
};

const SubtaskOutlet = () => {
	const {setHeading} = useHeader();
	const {subtaskId} = useParams();
	const {
		data: {testrayTask},
	} = useOutletContext<OutletContext>();

	const {
		data: testraySubtask,
		mutate: mutateSubtask,
		revalidate: revalidateSubtask,
	} = useFetch<TestraySubTask>(
		testraySubTaskImpl.getResource(subtaskId as string),
		{
			transformData: (response) =>
				testraySubTaskImpl.transformData(response),
		}
	);

	const {data: testraySubtaskToMerged} = useFetch<
		APIResponse<TestraySubTask>
	>(testraySubTaskImpl.resource, {
		params: {
			fields: 'name',
			filter: SearchBuilder.eq(
				'r_mergedToTestraySubtask_c_subtaskId',
				subtaskId as string
			),
			pageSize: 100,
		},
		transformData: (response) =>
			testraySubTaskImpl.transformDataFromList(response),
	});

	const {data, mutate: mutateSubtaskIssues} = useFetch(
		testraySubtaskIssuesImpl.resource,
		{
			params: {
				filter: SearchBuilder.eq('subtaskId', subtaskId as string),
			},
			transformData: (response) =>
				testraySubtaskIssuesImpl.transformDataFromList(response),
		}
	);

	const {data: mbMessage} = useFetch(
		testraySubtask?.mbMessageId
			? liferayMessageBoardImpl.getMessagesIdURL(
					testraySubtask.mbMessageId
			  )
			: null
	);

	const {data: testraySubtaskToSplit} = useFetch<APIResponse<TestraySubTask>>(
		testraySubTaskImpl.resource,
		{
			params: {
				fields: 'name',
				filter: SearchBuilder.eq(
					'r_splitFromTestraySubtask_c_subtaskId',
					subtaskId as string
				),
				pageSize: 100,
			},
			transformData: (response) =>
				testraySubTaskImpl.transformDataFromList(response),
		}
	);

	const subtaskIssues = data?.items || [];

	const mergedSubtaskNames = (testraySubtaskToMerged?.items || [])
		.map(({name}) => name)
		.join(', ');

	const splitSubtaskNames = (testraySubtaskToSplit?.items || [])
		.map(({name}) => name)
		.join(', ');

	useEffect(() => {
		if (testraySubtask) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('task'),
						path: `/testflow/${testrayTask?.id}`,
						title: testrayTask.name,
					},
					{
						category: i18n.translate('subtask'),
						title: testraySubtask.name,
					},
				]);
			});
		}
	}, [setHeading, testraySubtask, testrayTask]);

	return (
		<Outlet
			context={{
				data: {
					mbMessage,
					mergedSubtaskNames,
					splitSubtaskNames,
					subtaskIssues,
					testraySubtask,
					testrayTask,
				},
				mutate: {
					mutateSubtask,
					mutateSubtaskIssues,
				},
				revalidate: {
					revalidateSubtask,
				},
			}}
		/>
	);
};

export default SubtaskOutlet;
