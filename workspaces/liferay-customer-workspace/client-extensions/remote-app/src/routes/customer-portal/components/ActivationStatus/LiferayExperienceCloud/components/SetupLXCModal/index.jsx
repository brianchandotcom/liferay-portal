/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import ClayModal from '@clayui/modal';
import {useMemo, useState} from 'react';
import i18n from '../../../../../../../common/I18n';
import {LXC_STEPS_TYPES} from '../../../../../utils/constants/';
import AlreadySubmittedFormModal from '../../../AlreadySubmittedModal';
import ConfirmationMessageModal from '../ConfirmationMessageModal';
import SetupLiferayExperienceCloudForm from './components/SetupLXCForm';
import {submittedModalTexts} from './utils/submittedModalTexts';

const SetupLiferayExperienceCloudModal = ({
	handleOnLeftButtonClick,
	observer,
	onClose,
	project,
	subscriptionGroupLxcId,
}) => {
	const [currentProcess, setCurrentProcess] = useState(
		LXC_STEPS_TYPES.setupForm
	);
	const [formAlreadySubmitted, setFormAlreadySubmitted] = useState(false);

	const currentModalForm = useMemo(() => {
		const handleChangeForm = (isSuccess) => {
			if (isSuccess) {
				return setCurrentProcess(LXC_STEPS_TYPES.confirmationForm);
			}
			onClose();
		};

		return {
			[LXC_STEPS_TYPES.confirmationForm]: (
				<ConfirmationMessageModal onClose={onClose} />
			),
			[LXC_STEPS_TYPES.setupForm]: (
				<SetupLiferayExperienceCloudForm
					handleChangeForm={handleChangeForm}
					handleOnLeftButtonClick={handleOnLeftButtonClick}
					leftButton={i18n.translate('cancel')}
					project={project}
					setFormAlreadySubmitted={setFormAlreadySubmitted}
					subscriptionGroupLxcId={subscriptionGroupLxcId}
				/>
			),
		};
	}, [handleOnLeftButtonClick, onClose, project, subscriptionGroupLxcId]);

	return (
		<ClayModal center observer={observer}>
			{formAlreadySubmitted ? (
				<AlreadySubmittedFormModal
					onClose={onClose}
					submittedModalTexts={submittedModalTexts}
				/>
			) : (
				currentModalForm[currentProcess]
			)}
		</ClayModal>
	);
};

export default SetupLiferayExperienceCloudModal;
