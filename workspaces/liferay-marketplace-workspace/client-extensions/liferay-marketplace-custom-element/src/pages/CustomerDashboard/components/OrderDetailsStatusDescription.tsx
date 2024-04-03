/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';

import purchasedAppIcon from '../../../assets/icons/purchased_app_icon.svg';
import OrderStatus from '../../../components/OrderStatus';
import {OrderType} from '../../../enums/OrderType';

enum OrderAppTypeEnum {
	DXPAPP = 'DXP APP',
	CLOUDAPP = 'CLOUD APP',
}

type OrderDetailsStatusDescriptionProps = {
	order?: Cart;
	productOwner?: string;
};

const getOrderDetailsType = (orderTypeExternalReferenceCode: string) =>
	orderTypeExternalReferenceCode === OrderType.DXP
		? OrderAppTypeEnum.DXPAPP
		: OrderAppTypeEnum.CLOUDAPP;

const OrderDetailsStatusDescription = ({
	order,
	productOwner,
}: OrderDetailsStatusDescriptionProps) => (
	<div className="align-items-center d-flex">
		<div className="order-details-publisher">{productOwner}</div>

		<div className="align-items-center app-details-status d-flex mx-3">
			<OrderStatus orderStatus={order?.orderStatusInfo.label}>
				{order?.orderStatusInfo.label}
			</OrderStatus>
		</div>

		<ClayLabel className="rounded" displayType="info" large>
			<div className="align-items-center d-flex">
				<img
					alt="Purchased Order Icon"
					className="mr-1"
					src={purchasedAppIcon}
				/>
				{getOrderDetailsType(
					order?.orderTypeExternalReferenceCode as string
				)}
			</div>
		</ClayLabel>
	</div>
);

export default OrderDetailsStatusDescription;
