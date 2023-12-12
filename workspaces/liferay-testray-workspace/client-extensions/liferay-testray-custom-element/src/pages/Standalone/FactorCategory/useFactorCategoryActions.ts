/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useFormModal from '../../../hooks/useFormModal';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {
	TestrayFactorCategory,
	testrayFactorCategoryRest,
} from '../../../services/rest';
import {Action} from '../../../types';

const useFactorCategoryActions = () => {
	const {removeItemFromList} = useMutate();
	const formModal = useFormModal();
	const modal = formModal.modal;

	const actions: Action<TestrayFactorCategory>[] = [
		{
			action: (factorCategory) => modal.open(factorCategory),
			icon: 'pencil',
			name: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				testrayFactorCategoryRest
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id), modal.onSave)
					.then(modal.onSuccess)
					.catch(modal.onError),
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

export default useFactorCategoryActions;
