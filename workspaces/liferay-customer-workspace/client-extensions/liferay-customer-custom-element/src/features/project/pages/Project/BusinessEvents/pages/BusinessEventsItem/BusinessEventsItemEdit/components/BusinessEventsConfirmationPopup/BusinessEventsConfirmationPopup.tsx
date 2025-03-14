/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import Button from '~/components/Button';
import i18n from '~/utils/I18n';

import './BusinessEventsConfirmationPopup.css';

import {ClayInput} from '@clayui/form';
import {useState} from 'react';

interface IBusinessEventsConfirmationPopupProps {
	handleSubmit: () => void;
	message: string;
	observer: Observer;
	onClose: () => void;
}

const BusinessEventsConfirmationPopup = ({
	handleSubmit,
	message,
	observer,
	onClose,
}: IBusinessEventsConfirmationPopupProps) => {
	const [reason, setReason] = useState('');

	const handleInputChange = (event: {target: {value: string}}) => {
		setReason(event.target.value);
	};

	return (
		<ClayModal center observer={observer} size="lg">
			<div className="p-4">
				<div className="pb-4">
					<div className="font-weight-bold header-title mb-1">
						{i18n
							.translate('third-party-vendor-integration')
							.toUpperCase()}
					</div>

					<div className="d-flex justify-content-between">
						<h3>{i18n.translate('change-target-go-live')}</h3>

						<Button
							appendIcon="times"
							aria-label="close"
							className="align-self-start edit-modal-close"
							displayType="unstyled"
							onClick={onClose}
						/>
					</div>
				</div>

				<div className="pb-3">
					<p className="mb-3">{message}</p>

					<div>
						<div className="font-weight-bold pb-2">
							{i18n.translate('reason-for-change')}

							<span className="edit-modal-asterisk">*</span>
						</div>

						<ClayInput
							component="textarea"
							onChange={handleInputChange}
							placeholder={i18n.translate(
								'unresolved-tickets-blocked-us-from-going-live'
							)}
							required
							type="text"
							value={reason}
						/>
					</div>
				</div>

				<div>
					<div className="d-flex justify-content-end">
						<ClayButton
							className="mr-3"
							displayType="secondary"
							onClick={onClose}
						>
							{i18n.translate('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!reason.trim()}
							displayType="primary"
							onClick={handleSubmit}
						>
							{i18n.translate('save-changes')}
						</ClayButton>
					</div>
				</div>
			</div>
		</ClayModal>
	);
};

export default BusinessEventsConfirmationPopup;
