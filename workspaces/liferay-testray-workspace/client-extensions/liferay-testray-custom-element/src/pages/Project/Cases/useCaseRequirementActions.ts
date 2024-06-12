/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import useFormModal from '../../../hooks/useFormModal';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {
	TestrayRequirementCase,
	createRequirementCaseBatch,
	deleteRequirementCaseBatch,
	testrayCaseRequirementsImpl,
} from '../../../services/rest';
import {Action} from '../../../types';
import {State} from './CaseRequirementLinkModal';

type UseCaseRequirementActions = {
	caseId?: number;
	requirementId?: number;
};

const useCaseRequirementActions = ({
	caseId,
	requirementId,
}: UseCaseRequirementActions = {}) => {
	const [forceRefetch, setForceRefetch] = useState(0);
	const {removeItemFromList} = useMutate();

	const {forceRefetch: modalForceRefetch, modal} = useFormModal({
		onSave: ({items, state}: {items: number; state: State}) => {
			if (state.length) {
				createRequirementCaseBatch(
					state.map(
						(requirementCase) =>
							({
								caseId,
								requirementId,
								...requirementCase,
							}) as any
					)
				)
					.then(() => {
						deleteRequirementCaseBatch(items)
							.then(() => {
								setTimeout(() => {
									setForceRefetch(new Date().getTime());
								}, 100);
							})
							.catch(console.error);
					})
					.catch(console.error);
			}

			return deleteRequirementCaseBatch(items)
				.then(() => {
					setTimeout(() => {
						setForceRefetch(new Date().getTime());
					}, 100);
				})
				.catch(console.error);
		},
	});

	const actions: Action<TestrayRequirementCase>[] = [
		{
			action: ({id}, mutate) =>
				testrayCaseRequirementsImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(modal.onSave)
					.catch(modal.onError),
			icon: 'trash',
			name: i18n.translate('delete'),
		},
	];

	return {
		actions,
		formModal: {
			...modal,
			forceRefetch: forceRefetch || modalForceRefetch,
		},
	};
};

export default useCaseRequirementActions;
