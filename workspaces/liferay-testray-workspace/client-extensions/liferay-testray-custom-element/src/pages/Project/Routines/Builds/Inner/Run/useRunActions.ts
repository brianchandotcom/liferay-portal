/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '~/services/liferay';

import useFormModal from '../../../../../../hooks/useFormModal';
import useMutate from '../../../../../../hooks/useMutate';
import useRuns from '../../../../../../hooks/useRuns';
import i18n from '../../../../../../i18n';
import {TestrayRun, testrayRunImpl} from '../../../../../../services/rest';
import {Action} from '../../../../../../types';

const useRunActions = () => {
	const {removeItemFromList} = useMutate();
	const {setRunA, setRunB} = useRuns();
	const formModal = useFormModal();
	const modal = formModal.modal;

	const actions = [
		{
			action: (run) => modal.open(run),
			icon: 'display',
			name: i18n.translate('select-environment-factors'),
			permission: 'UPDATE',
		},
		{
			action: ({testrayRunId}, mutate) =>
				testrayRunImpl
					.removeResource(testrayRunId)
					?.then(() => removeItemFromList(mutate, testrayRunId))
					.then(modal.onSave)
					.catch(modal.onError),
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
		{
			action: ({testrayRunId}) => {
				setRunA(testrayRunId);

				return Liferay.Util.openToast({
					message: i18n.translate('run-a-successfully-added'),
				});
			},
			icon: 'select-from-list',
			name: i18n.translate('select-run-a'),
		},
		{
			action: ({testrayRunId}) => {
				setRunB(testrayRunId);

				return Liferay.Util.openToast({
					message: i18n.translate('run-b-successfully-added'),
				});
			},
			icon: 'select-from-list',
			name: i18n.translate('select-run-b'),
		},
	] as Action<TestrayRun>[];

	return {
		actions,
		formModal,
	};
};
export default useRunActions;
