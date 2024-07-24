/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useAtom} from 'jotai';
import {useRef} from 'react';
import {useOutletContext} from 'react-router-dom';
import usePermission from '~/hooks/usePermission';
import {taskSidebarRefresh} from '~/hooks/useSidebarTask';
import {TestrayRole} from '~/util/constants';

import useFormActions from '../../../hooks/useFormActions';
import useFormModal from '../../../hooks/useFormModal';
import useModalContext from '../../../hooks/useModalContext';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay';
import {TestraySubtask} from '../../../services/rest';
import {testraySubtaskImpl} from '../../../services/rest/TestraySubtask';
import {Action} from '../../../types';
import {SubtaskStatuses} from '../../../util/statuses';
import {UserListView} from '../../Manage/User';

type OutletContext = {
	revalidate: {
		revalidateSubtask: () => void;
	};
};

const useSubtasksActions = () => {
	const [, setTaskSidebarRefresh] = useAtom(taskSidebarRefresh);
	const {forceRefetch, modal: completeModal} = useFormModal();
	const {form} = useFormActions();
	const {onOpenModal, state} = useModalContext();
	const {
		revalidate: {revalidateSubtask},
	} = useOutletContext<OutletContext>();
	const {updateItemFromList} = useMutate();
	const hasPermission = usePermission([
		TestrayRole.TESTRAY_ADMINISTRATOR,
		TestrayRole.TESTRAY_ANALYST,
		TestrayRole.TESTRAY_LEAD,
	]);

	const actionsRef = useRef([
		{
			action: (subtask, mutate) =>
				testraySubtaskImpl
					.assignToMe(subtask)
					.then(() => {
						updateItemFromList(
							mutate,
							0,
							{},
							{
								revalidate: true,
							}
						);

						revalidateSubtask();
					})
					.then(() => setTaskSidebarRefresh(new Date().getTime())),
			hidden: ({status}) => status === SubtaskStatuses.IN_ANALYSIS,
			icon: 'user',
			name: ({status}) =>
				i18n.sub(
					'assign-to-me-and-x',
					status === SubtaskStatuses.OPEN
						? 'begin-analysis'
						: 'reanalyze'
				),
			permission: hasPermission,
		},
		{
			action: (subtask, mutate) =>
				onOpenModal({
					body: (
						<UserListView
							listViewProps={{
								managementToolbarProps: {
									display: {columns: false},
									hasSearch: true,
								},
							}}
							tableProps={{
								onClickRow: (user) => {
									testraySubtaskImpl
										.assignTo(subtask, user.id)
										.then(() => {
											updateItemFromList(
												mutate,
												subtask.id,
												{user},
												{revalidate: true}
											);

											revalidateSubtask();
										})
										.then(() =>
											setTaskSidebarRefresh(
												new Date().getTime()
											)
										)
										.then(form.onSuccess)
										.catch(form.onError)
										.finally(state.onClose);
								},
							}}
						/>
					),
					size: 'lg',
					title: i18n.translate('users'),
				}),
			icon: 'user',
			name: ({status}) => {
				if (status === SubtaskStatuses.IN_ANALYSIS) {
					return i18n.translate('assign');
				}

				if (status === SubtaskStatuses.OPEN) {
					return i18n.translate('assign-and-begin-analysis');
				}

				if (status === SubtaskStatuses.COMPLETE) {
					return i18n.translate('assign-and-reanalyze');
				}
			},
			permission: hasPermission,
		},
		{
			action: (subtask) => completeModal.open(subtask),
			hidden: ({status, userId}) =>
				userId !== Number(Liferay.ThemeDisplay.getUserId()) ||
				status !== SubtaskStatuses.IN_ANALYSIS,
			icon: 'polls',
			name: i18n.sub('complete-x', ''),
			permission: hasPermission,
		},
		{
			action: (subtask, mutate) =>
				testraySubtaskImpl
					.returnToOpen(subtask)
					.then(() => {
						updateItemFromList(
							mutate,
							0,
							{},
							{
								revalidate: true,
							}
						);

						revalidateSubtask();
					})
					.then(() => setTaskSidebarRefresh(new Date().getTime())),
			hidden: ({status}) => status === SubtaskStatuses.OPEN,
			icon: 'polls',
			name: i18n.translate('return-to-open'),
			permission: hasPermission,
		},
	] as Action<TestraySubtask>[]);

	return {
		actions: actionsRef.current,
		completeModal,
		forceRefetch,
		form,
	};
};

export default useSubtasksActions;
