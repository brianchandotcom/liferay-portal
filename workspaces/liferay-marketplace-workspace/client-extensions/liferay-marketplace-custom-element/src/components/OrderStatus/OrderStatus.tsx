/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import './OrderStatus.scss';
import {OrderStatus as Status} from '../../enums/Order';

type OrderStatusProps = {
	children?: string;
	orderStatus?: string;
};

const OrderStatus = ({children, orderStatus}: OrderStatusProps) => (
	<>
		<ClayIcon
			className={classNames('mr-2 order-status-icon', {
				'order-status-icon-completed': [
					Status.COMPLETED,
					Status.APPROVED,
				].includes(orderStatus as Status),
				'order-status-icon-pending': orderStatus === Status.PENDING,
				'order-status-icon-processing':
					orderStatus === Status.PROCESSING,
			})}
			symbol="circle"
		/>

		<span className="order-status-text">{children}</span>
	</>
);

export default OrderStatus;
