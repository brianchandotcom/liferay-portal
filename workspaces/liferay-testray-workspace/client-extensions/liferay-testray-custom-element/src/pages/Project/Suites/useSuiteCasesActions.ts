/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate, useParams} from 'react-router-dom';

import useFormModal from '../../../hooks/useFormModal';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestraySuiteCase, testraySuiteCaseImpl} from '../../../services/rest';
import {Action} from '../../../types';

const useSuiteCasesActions = ({isSmartSuite}: {isSmartSuite: boolean}) => {
	const {removeItemFromList} = useMutate();

	const {projectId} = useParams();
	const formModal = useFormModal();
	const navigate = useNavigate();

	const modal = formModal.modal;

	const actions: Action<TestraySuiteCase>[] = [
		{
			action: (suiteCase) =>
				navigate(
					`/project/${projectId}/cases/${suiteCase?.case?.id}/update`
				),
			icon: 'pencil',
			name: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: (suiteCase, mutate) =>
				testraySuiteCaseImpl
					.removeResource(suiteCase.id)
					?.then(() => removeItemFromList(mutate, suiteCase.id))
					.then(modal.onSuccess)
					.catch(modal.onError),
			disabled: isSmartSuite,
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
	];

	return {
		actions,
		formModal,
	};
};

export default useSuiteCasesActions;
