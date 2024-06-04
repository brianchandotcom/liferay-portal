/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useOutletContext} from 'react-router-dom';

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
	const {
		revalidate: {revalidateSubtask},
	} = useOutletContext<OutletContext>();
	const {form} = useFormActions();
	const {updateItemFromList} = useMutate();
	const {onOpenModal, state} = useModalContext();
	const {forceRefetch, modal: completeModal} = useFormModal();

	const actionsRef = useRef([
		{
			action: (subtask, mutate) =>
				testraySubtaskImpl.assignToMe(subtask).then(() => {
					updateItemFromList(
						mutate,
						0,
						{},
						{
							revalidate: true,
						}
					);

					revalidateSubtask();
				}),
			hidden: ({dueStatus}) =>
				dueStatus?.key === SubtaskStatuses.IN_ANALYSIS,
			icon: 'user',
			name: ({dueStatus}) =>
				i18n.sub(
					'assign-to-me-and-x',
					dueStatus.key === SubtaskStatuses.OPEN
						? 'begin-analysis'
						: 'reanalyze'
				),
			permission: 'UPDATE',
		},
		{
			action: (subtask, mutate) =>
				onOpenModal({
					body: (
						<UserListView
							listViewProps={{
								managementToolbarProps: {
									applyFilters: false,
									display: {columns: false},
									filterSchema: 'user',
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
			name: ({dueStatus}) => {
				if (dueStatus.key === SubtaskStatuses.IN_ANALYSIS) {
					return i18n.translate('assign');
				}

				if (dueStatus.key === SubtaskStatuses.OPEN) {
					return i18n.translate('assign-and-begin-analysis');
				}

				if (dueStatus.key === SubtaskStatuses.COMPLETE) {
					return i18n.translate('assign-and-reanalyze');
				}
			},
			permission: 'UPDATE',
		},
		{
			action: (subtask) => completeModal.open(subtask),
			hidden: ({dueStatus, user}) =>
				user?.id !== Number(Liferay.ThemeDisplay.getUserId()) ||
				dueStatus.key !== SubtaskStatuses.IN_ANALYSIS,
			icon: 'polls',
			name: i18n.sub('complete-x', ''),
			permission: 'UPDATE',
		},
		{
			action: (subtask, mutate) =>
				testraySubtaskImpl.returnToOpen(subtask).then(() => {
					updateItemFromList(
						mutate,
						0,
						{},
						{
							revalidate: true,
						}
					);

					revalidateSubtask();
				}),
			hidden: ({dueStatus}) => dueStatus.key !== SubtaskStatuses.COMPLETE,
			icon: 'polls',
			name: i18n.translate('return-to-open'),
			permission: 'UPDATE',
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
