/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import ClayAlert from '@clayui/alert';
import {useModal} from '@clayui/modal';
import {useState} from 'react';

import {DXPIcon} from '../../../../../common/icons';
import {useGetAccountSubscriptionGroups} from '../../../../../common/services/liferay/graphql/account-subscription-groups/queries/useGetAccountSubscriptionGroups';
import {ALERT_UPDATE_LIFERAY_EXPERIENCE_CLOUD_STATUS} from '../../../containers/ActivationKeysTable/utils/constants/alertUpdateLiferayExperienceCloud';
import {
	AUTO_CLOSE_ALERT_TIME,
	STATUS_TAG_TYPE_NAMES,
} from '../../../utils/constants';
import ActivationStatusLayout from '../Layout';
import LiferayExperienceCloudModal from './LiferayExperienceCloudModal';
import SetupLiferayExperienceCloudModal from './components/SetupLXCModal';
import useActivationStatusDate from './hooks/useActivationStatusDate';
import useOnCloseSetupModal from './hooks/useOnCloseSetupModal';
import getActivationStatusCardLayout from './utils/getActivationStatusCardLayout';

const ActivationStatusLiferayExperienceCloud = ({
	dispatch,
	lxcEnvironment,
	project,
	subscriptionGroupLxcEnvironment,
	subscriptionGroups,
	userAccount,
}) => {
	const [isVisibleSetupLxcModal, setIsVisibleSetupLxcModal] = useState(false);
	const [hasFinishedUpdate, setHasFinishedUpdate] = useState(false);
	const [visible, setVisible] = useState(false);
	const [lxcStatusActivation, setStatusLxcActivation] = useState(
		subscriptionGroupLxcEnvironment?.activationStatus
	);

	const {activationStatusDate} = useActivationStatusDate(project);

	const currentActivationStatus = getActivationStatusCardLayout(
		lxcEnvironment,
		project,
		() => setIsVisibleSetupLxcModal(true),
		() => setVisible(true),
		userAccount
	);

	const {
		observer: observerStatusModal,
		onClose: onCloseStatusModal,
	} = useModal({
		onClose: () => setVisible(false),
	});

	const {data: dataSubscriptionGroups} = useGetAccountSubscriptionGroups({
		fetchPolicy: 'network-only',
		filter: `accountKey eq '${project.accountKey}'`,
	});

	const {handleSubmitLxcEnvironment, observer} = useOnCloseSetupModal(
		dataSubscriptionGroups,
		() => setIsVisibleSetupLxcModal(false),
		setStatusLxcActivation
	);

	const activationStatus =
		currentActivationStatus[
			lxcStatusActivation || STATUS_TAG_TYPE_NAMES.notActivated
		];

	return (
		<div>
			{isVisibleSetupLxcModal && (
				<SetupLiferayExperienceCloudModal
					handleOnLeftButtonClick={() =>
						setIsVisibleSetupLxcModal(false)
					}
					observer={observer}
					onClose={handleSubmitLxcEnvironment}
					project={project}
					subscriptionGroupLxcId={
						subscriptionGroupLxcEnvironment.accountSubscriptionGroupId
					}
				/>
			)}

			{visible && (
				<LiferayExperienceCloudModal
					accountKey={project.accountKey}
					dispatch={dispatch}
					handleFinishUpdate={() => setHasFinishedUpdate(true)}
					handleStatusLxcActivation={() =>
						setStatusLxcActivation(STATUS_TAG_TYPE_NAMES.active)
					}
					lxcEnvironment={lxcEnvironment}
					observer={observerStatusModal}
					onClose={onCloseStatusModal}
					project={project}
					subscriptionGroupLxcEnvironment={
						subscriptionGroupLxcEnvironment
					}
					subscriptionGroups={subscriptionGroups}
				/>
			)}

			{hasFinishedUpdate && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={AUTO_CLOSE_ALERT_TIME.success}
						displayType="success"
						onClose={() => setHasFinishedUpdate(false)}
					>
						{ALERT_UPDATE_LIFERAY_EXPERIENCE_CLOUD_STATUS.success}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}

			<ActivationStatusLayout
				activationStatus={activationStatus}
				activationStatusDate={activationStatusDate}
				iconPath={DXPIcon}
				project={project}
				subscriptionGroupActivationStatus={lxcStatusActivation}
			/>
		</div>
	);
};

export default ActivationStatusLiferayExperienceCloud;
