/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import usePermission from '~/hooks/usePermission';
import {TestrayRole} from '~/util/constants';

import useFormActions from '../../../../hooks/useFormActions';
import useModalContext from '../../../../hooks/useModalContext';
import useMutate from '../../../../hooks/useMutate';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../services/liferay';
import {
	TestrayCaseResult,
	testrayCaseResultImpl,
} from '../../../../services/rest';
import {Action} from '../../../../types';
import {UserListView} from '../../../Manage/User';

const useBuildTestActions = () => {
	const {form} = useFormActions();
	const {onOpenModal, state} = useModalContext();
	const {removeItemFromList, updateItemFromList} = useMutate();
	const hasPermission = usePermission([
		TestrayRole.TESTRAY_ADMINISTRATOR,
		TestrayRole.TESTRAY_ANALYST,
		TestrayRole.TESTRAY_LEAD,
	]);

	const actionsRef = useRef([
		{
			action: (caseResult, mutate) =>
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
								onClickRow: (user) =>
									testrayCaseResultImpl
										.assignTo(caseResult, user.id)
										.then(() =>
											updateItemFromList(
												mutate,
												caseResult.id ||
													(caseResult.testrayCaseResultId as number),
												{user},
												{revalidate: true}
											)
										)
										.then(form.onSuccess)
										.catch(form.onError)
										.finally(state.onClose),
							}}
						/>
					),
					size: 'full-screen',
					title: i18n.translate('users'),
				}),
			icon: 'user',
			name: i18n.translate('assign'),
			permission: hasPermission,
		},
		{
			action: (caseResult, mutate) => {
				(caseResult.userName === Liferay.ThemeDisplay.getUserName()
					? testrayCaseResultImpl.removeAssign(caseResult)
					: testrayCaseResultImpl.assignToMe(caseResult)
				)
					.then((user) =>
						updateItemFromList(
							mutate,
							caseResult.id ||
								(caseResult.testrayCaseResultId as number),
							{user},
							{revalidate: true}
						)
					)
					.then(form.onSuccess)
					.catch(form.onError);
			},
			icon: 'user',
			name: (caseResult) =>
				i18n.translate(
					caseResult.userName === Liferay.ThemeDisplay.getUserName()
						? 'unassign-myself'
						: 'assign-to-me'
				),
			permission: hasPermission,
		},
		{
			action: ({testrayCaseResultId}, mutate) =>
				testrayCaseResultImpl
					.removeResource(testrayCaseResultId as number)
					?.then(() =>
						removeItemFromList(
							mutate,
							testrayCaseResultId as number
						)
					)
					.then(form.onSuccess)
					.catch(form.onError),
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: hasPermission,
		},
	] as Action<TestrayCaseResult>[]);

	return {
		actions: actionsRef.current,
		form,
	};
};

export default useBuildTestActions;
