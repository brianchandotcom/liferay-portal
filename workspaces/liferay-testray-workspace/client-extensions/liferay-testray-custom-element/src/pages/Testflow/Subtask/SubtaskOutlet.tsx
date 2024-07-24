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
	TestraySubtask,
	TestrayTask,
	liferayMessageBoardImpl,
	testraySubtaskImpl,
} from '../../../services/rest';

type OutletContext = {
	data: {
		testrayTask: TestrayTask;
	};
	revalidate: {
		revalidateTaskUser: () => void;
	};
};

const uri = '/subtasks';

const SubtaskOutlet = () => {
	const {setHeading} = useHeader();
	const {subtaskId} = useParams();
	const {
		data: {testrayTask},
	} = useOutletContext<OutletContext>();

	const buildId = String(testrayTask?.build?.id);
	const projectId = String(testrayTask?.build?.project?.id);
	const routineId = String(testrayTask?.build?.routine?.id);

	const {
		data: testraySubtask,
		mutate: mutateSubtask,
		revalidate: revalidateSubtask,
	} = useFetch<TestraySubtask>(
		testraySubtaskImpl.getResource(subtaskId as string),
		{
			transformData: (response) =>
				testraySubtaskImpl.transformData(response),
		}
	);

	const {data: testraySubtaskToMerged} = useFetch<
		APIResponse<TestraySubtask>
	>(uri, {
		params: {
			fields: 'name',
			filter: SearchBuilder.eq(
				'r_mergedToTestraySubtask_c_subtaskId',
				subtaskId as string
			),
			pageSize: 100,
		},
		transformData: (response) =>
			testraySubtaskImpl.transformDataFromList(response),
	});

	const {data: mbMessage} = useFetch(
		testraySubtask?.mbMessageId
			? liferayMessageBoardImpl.getMessagesIdURL(
					testraySubtask.mbMessageId
				)
			: null
	);

	const {data: testraySubtaskToSplit} = useFetch<APIResponse<TestraySubtask>>(
		uri,
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
				testraySubtaskImpl.transformDataFromList(response),
		}
	);

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
					buildId,
					mbMessage,
					mergedSubtaskNames,
					projectId,
					routineId,
					splitSubtaskNames,
					testraySubtask,
					testrayTask,
				},
				mutate: {
					mutateSubtask,
				},
				revalidate: {
					revalidateSubtask,
				},
			}}
		/>
	);
};

export default SubtaskOutlet;
