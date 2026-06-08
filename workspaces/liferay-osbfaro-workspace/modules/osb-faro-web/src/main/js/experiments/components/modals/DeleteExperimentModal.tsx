/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import {Observer} from '@clayui/modal/lib/types';
import React from 'react';
import {connect} from 'react-redux';
import {useHistory, useParams} from 'react-router-dom';
import BaseModal from '~/experiments/components/modals/BaseModal';
import {EXPERIMENT_DELETE_MUTATION} from '~/experiments/queries/ExperimentMutation';
import {addAlert} from '~/shared/actions/alerts';
import {Alert} from '~/shared/types';
import {Routes, toRoute} from '~/shared/util/router';

interface IDeleteExperimentModalProps {
	addAlert: (alert: {alertType: string; message: string}) => void;
	experimentId: string;
	observer: Observer;
	onClose: () => void;
}

const DeleteExperimentModal = ({
	addAlert,
	experimentId,
	observer,
	onClose,
}: IDeleteExperimentModalProps) => {
	const {channelId, groupId} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const history = useHistory();
	const [mutate] = useMutation(EXPERIMENT_DELETE_MUTATION);

	const onSubmit = () =>
		mutate({
			variables: {
				experimentId,
			},
		});

	const onSuccess = () => {
		addAlert({
			alertType: Alert.Types.Success,
			message: Liferay.Language.get('the-test-has-been-deleted'),
		});

		history.push(toRoute(Routes.TESTS, {channelId, groupId}));
	};

	return (
		<BaseModal
			observer={observer}
			onClose={onClose}
			onSubmit={onSubmit}
			onSuccess={onSuccess}
			status="warning"
			submitMessage={Liferay.Language.get('delete')}
			title={Liferay.Language.get('deleting-test')}
		>
			<p className="font-weight-bold text-secondary">
				{Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this-test'
				)}
			</p>

			<p className="text-secondary">
				{Liferay.Language.get(
					'you-will-permanently-lose-all-test-data-and-results-collected-from-this-test'
				)}{' '}
				{Liferay.Language.get('you-will-not-be-able-to-run-this-test')}{' '}
				{Liferay.Language.get(
					'you-will-not-be-able-to-undo-this-operation'
				)}
			</p>
		</BaseModal>
	);
};

export default connect(null, {addAlert})(DeleteExperimentModal);
