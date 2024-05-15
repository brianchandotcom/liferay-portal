/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import useFormModal from '~/hooks/useFormModal';
import usePusher from '~/hooks/usePusher';
import {Liferay} from '~/services/liferay';

import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {
	TestrayRequirement,
	testrayRequirementsImpl,
} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';

const useRequirementActions = ({
	isHeaderActions = false,
}: ActionsHookParameter = {}) => {
	const [forceRefetch, setForceRefetch] = useState(0);
	const {
		modal: {onError, onSave},
	} = useFormModal();
	const {removeItemFromList} = useMutate();
	const navigate = useNavigate();

	const pusher = usePusher();

	useEffect(() => {
		if (!pusher) {
			return;
		}

		const channel = pusher.subscribe(
			`${Liferay.ThemeDisplay.getUserId()}-requirements`
		);

		channel.bind('processed', ({message}: {message: string}) => {
			setForceRefetch(new Date().getTime());

			Liferay.Util.openToast({
				message,
			});
		});

		return () =>
			pusher.unsubscribe(
				`${Liferay.ThemeDisplay.getUserId()}-requirements`
			);
	}, [pusher]);

	const actionsRef = useRef([
		{
			action: ({id}) =>
				navigate(isHeaderActions ? 'update' : `${id}/update`),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-requirement' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}) => navigate(`${id}`),
			icon: 'list-ul',
			name: i18n.translate('link-cases'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				testrayRequirementsImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(onSave)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(onError),
			icon: 'trash',
			name: i18n.translate(
				isHeaderActions ? 'delete-requirement' : 'delete'
			),
			permission: 'DELETE',
		},
	] as Action<TestrayRequirement>[]);

	return {
		actions: actionsRef.current,
		forceRefetch,
		navigate,
	};
};

export default useRequirementActions;
