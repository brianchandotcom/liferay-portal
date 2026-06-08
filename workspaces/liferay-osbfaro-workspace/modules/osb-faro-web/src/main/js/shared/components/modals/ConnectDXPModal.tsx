/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import Modal from '~/shared/components/modal';
import ConnectDXP from '~/shared/components/modals/onboarding-modal/ConnectDXP';

const MODAL_SCREENS = [ConnectDXP];

interface IConnectDXPModalProps {
	groupId: string;
	id: string;
	onClose: () => void;
}

const ConnectDXPModal: React.FC<IConnectDXPModalProps> = ({
	groupId,
	id,
	onClose,
}) => {
	const [dxpConnected, setDxpConnected] = useState(false);
	const [step, setStep] = useState(0);

	const ScreenComponent = MODAL_SCREENS[step];

	return (
		<Modal className="connect-dxp onboarding-modal-root">
			<ScreenComponent
				dataSourceId={id}
				dxpConnected={dxpConnected}
				groupId={groupId}
				isUpgrading={false}
				onClose={onClose}
				onDxpConnected={setDxpConnected}
				onNext={(increment = 1) => setStep(step + increment)}
			/>
		</Modal>
	);
};

export default ConnectDXPModal;
