/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import ClayModal from '@clayui/modal';
import {useMemo, useState} from 'react';
import i18n from '~/utils/I18n';
import SetupAnalyticsCloudForm from '~/features/project/containers/SetupAnalyticsCloudForm';
import ConfirmationMessageModal from '~/features/project/containers/SetupAnalyticsCloudForm/ConfirmationMessageModal';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {ANALYTICS_STEPS_TYPES} from '~/features/project/utils/constants';
import AlreadySubmittedModal from '../ActivationStatus/AlreadySubmittedModal';

import './AnalyticsCloudModal.css';

const submittedModalTexts = {
	paragraph: i18n.translate(
		'return-to-the-product-activation-page-to-view-the-current-activation-status'
	),
	subtitle: i18n.translate(
		'we-ll-need-a-few-details-to-finish-building-your-analytics-cloud-workspace'
	),
	text: i18n.translate(
		'another-user-already-submitted-the-analytics-cloud-activation-request'
	),
	title: i18n.translate('set-up-analytics-cloud'),
};

const AnalyticsCloudModal = ({
	observer,
	onClose,
	project,
	subscriptionGroupId,
}) => {
	const [currentProcess, setCurrentProcess] = useState(
		ANALYTICS_STEPS_TYPES.setupForm
	);
	const [formAlreadySubmitted, setFormAlreadySubmitted] = useState(false);
	const {client} = useAppPropertiesContext();

	const currentModalForm = useMemo(
		() => ({
			[ANALYTICS_STEPS_TYPES.confirmationForm]: (
				<ConfirmationMessageModal handlePage={onClose} />
			),
			[ANALYTICS_STEPS_TYPES.setupForm]: (
				<SetupAnalyticsCloudForm
					client={client}
					handlePage={(isSuccess) => {
						if (isSuccess) {
							return setCurrentProcess(
								ANALYTICS_STEPS_TYPES.confirmationForm
							);
						}

						onClose();
					}}
					leftButton={i18n.translate('cancel')}
					project={project}
					setFormAlreadySubmitted={setFormAlreadySubmitted}
					subscriptionGroupId={subscriptionGroupId}
				/>
			),
		}),
		[client, onClose, project, subscriptionGroupId]
	);

	return (
		<ClayModal center observer={observer}>
			{formAlreadySubmitted ? (
				<AlreadySubmittedModal
					onClose={onClose}
					submittedModalTexts={submittedModalTexts}
				/>
			) : (
				currentModalForm[currentProcess]
			)}
		</ClayModal>
	);
};

export default AnalyticsCloudModal;
