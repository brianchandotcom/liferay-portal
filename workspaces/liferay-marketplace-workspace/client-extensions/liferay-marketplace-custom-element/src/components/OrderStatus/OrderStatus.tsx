/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import './OrderStatus.scss';

export enum Statuses {
	APPROVED = 'approved',
	COMPLETED = 'completed',
	PENDING = 'pending',
	PROCESSING = 'processing',
}

type OrderStatusProps = {
	children?: string;
	orderStatus?: string;
};

const OrderStatus = ({children, orderStatus}: OrderStatusProps) => (
	<>
		<ClayIcon
			className={classNames('mr-2 order-status-icon', {
				'order-status-icon-completed': [
					Statuses.COMPLETED,
					Statuses.APPROVED,
				].includes(orderStatus as Statuses),
				'order-status-icon-pending': orderStatus === Statuses.PENDING,
				'order-status-icon-processing':
					orderStatus === Statuses.PROCESSING,
			})}
			symbol="circle"
		/>

		<span className="order-status-text">{children}</span>
	</>
);

export default OrderStatus;
