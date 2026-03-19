/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {
	OrderTypes,
	OrderWorkflowStatusCode,
	getOrderStatusLabel,
	orderWorkflowStatusCodeLabels,
} from '../../enums/Order';

import './OrderStatus.scss';

type OrderStatusProps = {
	placedOrder: PlacedOrder;
};

const OrderStatus = ({placedOrder}: OrderStatusProps) => {
	const orderStatusLabel = getOrderStatusLabel(placedOrder);

	const getOrderStatusClassName = () => {
		const orderStatus = placedOrder.orderStatusInfo.code;

		if (
			placedOrder.orderStatusInfo.code !==
				OrderWorkflowStatusCode.COMPLETED &&
			placedOrder.orderTypeExternalReferenceCode === OrderTypes.AI_HUB
		) {
			return 'order-status-icon-processing';
		}

		if (
			orderStatusLabel ===
				orderWorkflowStatusCodeLabels[
					OrderWorkflowStatusCode.PENDING_PAYMENT
				] ||
			OrderWorkflowStatusCode.PENDING === orderStatus
		) {
			return 'order-status-icon-pending';
		}

		if (OrderWorkflowStatusCode.COMPLETED === orderStatus) {
			return 'order-status-icon-completed';
		}

		return 'order-status-icon-processing';
	};

	return (
		<>
			<ClayIcon
				className={classNames(
					'mr-2 order-status-icon',
					getOrderStatusClassName()
				)}
				symbol="circle"
			/>

			<span className="order-status-text">{orderStatusLabel}</span>
		</>
	);
};

export default OrderStatus;
