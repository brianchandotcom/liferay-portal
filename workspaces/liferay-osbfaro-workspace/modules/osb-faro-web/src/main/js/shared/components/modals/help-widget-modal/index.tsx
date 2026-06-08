/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import Modal from '~/shared/components/modal';

import IssueSubmitted from './IssueSubmitted';
import ReportIssue from './ReportIssue';

const MODAL_SCREENS = [ReportIssue, IssueSubmitted];

interface IHelpWidgetModalProps {
	groupId: string;
	onClose: () => void;
}

const HelpWidgetModal: React.FC<IHelpWidgetModalProps> = ({
	groupId,
	onClose,
}) => {
	const [step, setStep] = useState(0);

	const ScreenComponent = MODAL_SCREENS[step];

	return (
		<Modal className="help-widget-modal-root">
			<ScreenComponent
				groupId={groupId}
				onClose={onClose}
				onNext={(increment = 1) => setStep(step + increment)}
			/>
		</Modal>
	);
};

export default HelpWidgetModal;
