/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {addDays, format} from 'date-fns';

import BaseWrapper from '../../../components/Form/BaseWrapper';
import {OrderCustomFields} from '../../../enums/Order';
import i18n from '../../../i18n';
import trialOAuth2 from '../../../services/oauth/Trial';
import {getSSASettingsOrDefaultFromCustomFields} from '../util';

type ExtendSSATrialModalProps = {
	onClose: () => void;
	order: PlacedOrder;
};

const ExtendRequestModal: React.FC<ExtendSSATrialModalProps> = ({
	onClose,
	order,
}) => {
	const trialSettings = JSON.parse(
		order?.customFields[OrderCustomFields.TRIAL_SETTINGS]
	);

	const ssaSettings = getSSASettingsOrDefaultFromCustomFields(
		order.customFields
	);

	return (
		<div>
			<div className="d-flex justify-content-between">
				<div>
					<h3>{i18n.translate('details')}</h3>
					<BaseWrapper label={i18n.translate('project-id')} required>
						<ClayInput disabled value={ssaSettings.projectId} />
					</BaseWrapper>
					<BaseWrapper label={i18n.translate('start-date')} required>
						<ClayInput
							disabled
							value={format(
								new Date(order.createDate),
								'dd MMM, yyyy'
							).toString()}
						/>
					</BaseWrapper>
					<BaseWrapper
						label={i18n.translate('expiration-date')}
						required
					>
						<ClayInput
							disabled
							value={
								trialSettings[OrderCustomFields.END_DATE]
									? format(
											new Date(
												trialSettings[
													OrderCustomFields.END_DATE
												]
											),
											'dd MMM, yyyy'
										).toString()
									: 'DNE'
							}
						/>
					</BaseWrapper>
				</div>
				<div>
					<h3>{i18n.translate('extension')}</h3>
					<BaseWrapper
						label={i18n.translate('times-already-extended')}
						required
					>
						<ClayInput disabled value={ssaSettings.extendCount} />
					</BaseWrapper>
					<BaseWrapper
						label={i18n.translate('duration-of-the-extension')}
						required
					>
						<ClayInput disabled value={ssaSettings.duration} />
					</BaseWrapper>
					<BaseWrapper
						label={i18n.translate('new-potential-expiration-date')}
						required
					>
						<ClayInput
							disabled
							value={format(
								addDays(
									new Date(order.createDate),
									ssaSettings.duration
								),
								'dd MMM, yyyy'
							).toString()}
						/>
					</BaseWrapper>
				</div>
			</div>
			<div className="d-flex justify-content-end pt-8">
				<ClayButton
					className="mr-4"
					displayType="secondary"
					onClick={() => {
						trialOAuth2.extendTrial(order.id, -1);
						onClose();
					}}
				>
					{i18n.translate('reject-request')}
				</ClayButton>
				<ClayButton
					onClick={() => {
						trialOAuth2.extendTrial(order.id, ssaSettings.duration);
						onClose();
					}}
				>
					{i18n.translate('approve-request')}
				</ClayButton>
			</div>
		</div>
	);
};

export default ExtendRequestModal;
