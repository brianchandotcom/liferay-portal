/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import Modal from '~/shared/components/modal';

import ConfigureWorkspace from './ConfigureWorkspace';
import ConnectDXP from './ConnectDXP';
import InvitePeople from './InvitePeople';
import ReadyToGo from './ReadyToGo';
import Welcome from './Welcome';

const MODAL_SCREENS = [
	Welcome,
	ConfigureWorkspace,
	ConnectDXP,
	InvitePeople,
	ReadyToGo,
];

interface IOnboardingModalProps {
	groupId: string;
	onClose: () => void;
}

const OnboardingModal: React.FC<IOnboardingModalProps> = ({
	groupId,
	onClose,
}) => {
	const [dxpConnected, setDxpConnected] = useState(false);
	const [step, setStep] = useState(0);

	const ScreenComponent = MODAL_SCREENS[step];

	return (
		<Modal className="onboarding-modal-root">
			<ScreenComponent
				dxpConnected={dxpConnected}
				groupId={groupId}
				onClose={onClose}
				onDxpConnected={setDxpConnected}
				onNext={(increment = 1) => setStep(step + increment)}
				onPrevious={() => setStep(step - 1)}
				onboarding
			/>
		</Modal>
	);
};

export default OnboardingModal;
