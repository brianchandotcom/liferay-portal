/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';

import LabelStatus from '../../../../components/LabelStatus/LabelStatus';

interface OrderDetailsStatusDescriptionProps {
	orderDetails?: Cart;
	productCreatorAccount?: string;
}

const OrderDetailsStatusDescription = ({
	orderDetails,
	productCreatorAccount,
}: OrderDetailsStatusDescriptionProps) => {
	return (
		<div className="align-items-center d-flex">
			<div className="order-details-publisher">
				{productCreatorAccount}
			</div>
			<div className="align-items-center app-details-status d-flex mx-3">
				<LabelStatus
					provisioning={orderDetails?.paymentStatusInfo.label_i18n}
					provisioningLabel={orderDetails?.paymentStatusLabel}
				/>
			</div>
			<ClayLabel className="rounded" displayType="info" large>
				{orderDetails?.orderTypeExternalReferenceCode}
			</ClayLabel>
		</div>
	);
};

export default OrderDetailsStatusDescription;
