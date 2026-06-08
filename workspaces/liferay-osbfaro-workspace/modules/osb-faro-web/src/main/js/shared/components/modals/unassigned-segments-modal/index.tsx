/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import Modal from '~/shared/components/modal';

import AssignSegments from './AssignSegments';
import Welcome from './Welcome';

const MODAL_SCREENS = [Welcome, AssignSegments];

interface IUnassignedSegmentsModalProps {
	groupId: string;
	onClose: () => void;
}

const UnassignedSegmentsModal: React.FC<IUnassignedSegmentsModalProps> = ({
	groupId,
	onClose,
}) => {
	const [step, setStep] = useState(0);

	const ScreenComponent = MODAL_SCREENS[step];

	return (
		<Modal className="unassigned-segments-modal-root" size="xl">
			<ScreenComponent
				groupId={groupId}
				onClose={onClose}
				onNext={(increment = 1) => setStep(step + increment)}
			/>
		</Modal>
	);
};

export default UnassignedSegmentsModal;
