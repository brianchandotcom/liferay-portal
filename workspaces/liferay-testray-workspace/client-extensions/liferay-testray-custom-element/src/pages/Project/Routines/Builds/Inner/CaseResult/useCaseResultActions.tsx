/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useNavigate} from 'react-router-dom';

import useFormActions from '../../../../../../hooks/useFormActions';
import useMutate from '../../../../../../hooks/useMutate';
import i18n from '../../../../../../i18n';
import {
	TestrayCaseResult,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import {Action, ActionsHookParameter} from '../../../../../../types';

const useCaseResultActions = (
	{isHeaderActions}: ActionsHookParameter = {isHeaderActions: true}
) => {
	const {form} = useFormActions();
	const {removeItemFromList} = useMutate();
	const navigate = useNavigate();
	const actionsRef = useRef([
		{
			action: ({id}, mutate) =>
				testrayCaseResultImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSave)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(form.onError),
			icon: 'trash',
			name: i18n.translate(
				isHeaderActions ? 'delete-case-result' : 'delete'
			),
			permission: 'DELETE',
		},
	] as Action<TestrayCaseResult>[]);

	return {
		actions: actionsRef.current,
	};
};

export default useCaseResultActions;
