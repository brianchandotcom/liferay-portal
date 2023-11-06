/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {showAppImage} from '../../../../utils/util';

import './OrderDetailsHeader.scss';
import OrderDetailsStatusDescription from './OrderDetailsStatusDescription';

type OrderDetailsProps = {
	hasOrderDescription?: boolean;
	hasOrderDetails?: boolean;
	image?: string;
	name?: string;
	order?: Cart;
	productOwner?: string;
};

const OrderDetailsHeader: React.FC<OrderDetailsProps> = ({
	hasOrderDescription = false,
	hasOrderDetails = false,
	image,
	name,
	order,
	productOwner,
}) => (
	<div className="d-flex flex-row justify-content-between pb-3 pt-5">
		<div className="d-flex flex-row">
			<img
				alt="App Icon"
				className="rounded"
				height="56px"
				src={showAppImage(image)}
				width="56px"
			/>

			<div className="align-items-center ml-4">
				<h2 className="text-weight-bold">{name}</h2>

				{hasOrderDetails && (
					<OrderDetailsStatusDescription
						order={order}
						productOwner={productOwner}
					/>
				)}

				{hasOrderDescription && <p>Order Description</p>}
			</div>
		</div>
	</div>
);

export default OrderDetailsHeader;
