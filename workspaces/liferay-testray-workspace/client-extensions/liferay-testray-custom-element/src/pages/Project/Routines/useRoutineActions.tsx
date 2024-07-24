/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useNavigate} from 'react-router-dom';
import usePermission from '~/hooks/usePermission';
import {TestrayRole} from '~/util/constants';

import useFormActions from '../../../hooks/useFormActions';
import useModalContext from '../../../hooks/useModalContext';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestrayRoutine, testrayRoutineImpl} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';
import EnvironmentFactorsModal from '../../Standalone/EnvironmentFactors/EnviromentFactorsModal';

const useRoutineActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const {form} = useFormActions();
	const {onOpenModal, state} = useModalContext();
	const {removeItemFromList} = useMutate();
	const hasPermission = usePermission([TestrayRole.TESTRAY_ADMINISTRATOR]);
	const navigate = useNavigate();

	const actionsRef = useRef([
		{
			action: (routine) => {
				const routineId = routine.id
					? routine.id
					: routine.testrayRoutineId;

				return navigate(
					isHeaderActions ? 'update' : `${routineId}/update`
				);
			},
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-routine' : 'edit'),
			permission: hasPermission,
		},
		{
			action: (routine) => {
				const routineId = routine.id
					? routine.id
					: routine.testrayRoutineId;

				return navigate(
					isHeaderActions ? 'templates' : `${routineId}/templates`
				);
			},
			icon: 'cog',
			name: i18n.translate('manage-templates'),
			permission: hasPermission,
		},
		{
			action: (routine) => {
				const routineId = routine.id
					? routine.id
					: (routine.testrayRoutineId as number);

				onOpenModal({
					body: (
						<EnvironmentFactorsModal
							onCloseModal={state.onClose}
							routineId={routineId}
						/>
					),
					footer: <div id="environment-factor-modal-footer"></div>,
					footerDefault: false,
					size: 'full-screen',
					title: i18n.translate('select-default-environment-factors'),
				});
			},

			icon: 'display',
			name: i18n.translate('select-default-environment-factors'),
			permission: hasPermission,
		},
		{
			action: (routine, mutate) => {
				const routineId = routine.id
					? routine.id
					: (routine.testrayRoutineId as number);

				testrayRoutineImpl
					.removeResource(routineId)
					?.then(() => removeItemFromList(mutate, routineId))
					.then(form.onSuccess)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(form.onError);
			},
			icon: 'trash',
			name: i18n.translate(isHeaderActions ? 'delete-routine' : 'delete'),
			permission: hasPermission,
		},
	] as Action<TestrayRoutine>[]);

	return {
		actions: actionsRef.current,
		navigate,
	};
};

export default useRoutineActions;
