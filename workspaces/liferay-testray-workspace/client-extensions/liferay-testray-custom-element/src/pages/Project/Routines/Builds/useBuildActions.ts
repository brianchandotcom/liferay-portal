/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useNavigate} from 'react-router-dom';
import useAutofillBuild from '~/hooks/useAutofillBuild';
import {Liferay} from '~/services/liferay';

import useFormModal from '../../../../hooks/useFormModal';
import useMutate from '../../../../hooks/useMutate';
import i18n from '../../../../i18n';
import {TestrayBuild, testrayBuildImpl} from '../../../../services/rest';
import {Action, ActionsHookParameter} from '../../../../types';

const useBuildActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const formModal = useFormModal();
	const {removeItemFromList, updateItemFromList} = useMutate();
	const {setBuildA, setBuildB} = useAutofillBuild();
	const navigate = useNavigate();

	const modal = formModal.modal;

	const actionsRef = useRef([
		{
			action: () => alert('Archive'),
			icon: 'download',
			name: i18n.translate('export-csv'),
		},
		{
			action: (testrayBuild) =>
				navigate(
					isHeaderActions
						? 'update'
						: `build/${testrayBuild.id}/update`
				),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-build' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id, promoted}, mutate) => {
				testrayBuildImpl
					.update(id, {
						promoted: !promoted,
					})
					.then(() => {
						if (isHeaderActions) {
							return mutate((prevData: any) => ({
								...prevData,
								promoted: !prevData?.promoted,
							}));
						}

						updateItemFromList(mutate, id, {
							promoted: !promoted,
						});
					})
					.then(modal.onSuccess)
					.catch(modal.onError);
			},
			icon: 'star',
			name: (build) =>
				i18n.translate(build?.promoted ? 'demote' : 'promote'),
			permission: 'UPDATE',
		},
		{
			action: ({archived, id, promoted}, mutate) => {
				if (!promoted) {
					testrayBuildImpl
						.updateArchivedFlag(id, !archived)
						.then(() => mutate())
						.catch(modal.onError);
				}
			},
			disabled: ({promoted, tasks}) => promoted || tasks.length,
			icon: 'archive',
			name: (build) =>
				i18n.translate(build.archived ? 'unarchive' : 'archive'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				testrayBuildImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(modal.onSave)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(modal.onError),
			icon: 'trash',
			name: i18n.translate(isHeaderActions ? 'delete-build' : 'delete'),
			permission: 'DELETE',
		},
		{
			action: ({id}) => {
				setBuildA(id);

				return Liferay.Util.openToast({
					message: i18n.translate('build-a-successfully-added'),
				});
			},
			icon: 'select-from-list',
			name: i18n.translate('select-build-a'),
		},
		{
			action: ({id}) => {
				setBuildB(id);

				return Liferay.Util.openToast({
					message: i18n.translate('build-b-successfully-added'),
				});
			},
			icon: 'select-from-list',
			name: i18n.translate('select-build-b'),
		},
	] as Action<TestrayBuild>[]);

	return {
		actions: actionsRef.current,
		formModal,
	};
};

export default useBuildActions;
