/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useNavigate} from 'react-router-dom';

import useFormActions from '../../../hooks/useFormActions';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestraySuite, testraySuiteImpl} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';

const useSuiteActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const {form} = useFormActions();
	const navigate = useNavigate();
	const {removeItemFromList} = useMutate();

	const actionsRef = useRef([
		{
			action: (suite) =>
				navigate(isHeaderActions ? 'update' : `${suite.id}/update`),
			icon: 'pencil',
			name: isHeaderActions
				? i18n.sub('edit-x', 'suite')
				: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				testraySuiteImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSuccess)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(form.onError),
			icon: 'trash',
			name: isHeaderActions
				? i18n.sub('delete-x', 'suite')
				: i18n.translate('delete'),
			permission: 'DELETE',
		},
	] as Action<TestraySuite>[]);

	return {
		actions: actionsRef.current,
		navigate,
	};
};

export default useSuiteActions;
