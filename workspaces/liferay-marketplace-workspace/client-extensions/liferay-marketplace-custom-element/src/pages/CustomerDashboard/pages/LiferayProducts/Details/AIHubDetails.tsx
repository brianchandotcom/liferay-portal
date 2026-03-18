/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useOutletContext} from 'react-router-dom';

import {DetailedCard} from '../../../../../components/DetailedCard/DetailedCard';
import QATable, {Orientation} from '../../../../../components/QATable';
import {
	OrderCustomFields,
	OrderWorkflowStatusCode,
} from '../../../../../enums/Order';
import i18n from '../../../../../i18n';
import ActivationKeyAlert from '../Licenses/LicenseAlert';

const AIHubDetails = () => {
	const {placedOrder} = useOutletContext<any>();

	const orderStatusCode = placedOrder?.orderStatusInfo
		?.code as OrderWorkflowStatusCode;

	const orderMetadata = placedOrder
		? JSON.parse(placedOrder.customFields[OrderCustomFields.ORDER_METADATA])
		: {};

	const orderAdditionalInformation = orderMetadata?.aiHubForm || {};

	return (
		<div>
			{orderStatusCode !== OrderWorkflowStatusCode.COMPLETED && (
				<ActivationKeyAlert
					className="license-alert"
					dismissible={false}
					symbol="check-circle"
					title="Your Beta Access Request is Pending"
					type="info"
				>
					Our team is currently reviewing your request. An
					administrator will approve your access shortly, and you will
					receive a notification via email as soon as your account is
					activated.
				</ActivationKeyAlert>
			)}

			<DetailedCard
				cardIconAltText="Profile Icon"
				cardTitle={i18n.translate('ai-hub-account-details')}
				clayIcon="order-form-tag"
			>
				<QATable
					columns={2}
					items={[
						{
							title: i18n.translate('ai-hub-account-name'),
							value: orderAdditionalInformation.fullName,
						},
						{
							title: i18n.translate('ai-administration-email'),
							value: orderAdditionalInformation.businessEmail,
						},
					]}
					orientation={Orientation.VERTICAL}
				/>
			</DetailedCard>
		</div>
	);
};

export default AIHubDetails;
