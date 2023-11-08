/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {showAppImage} from '../../../../utils/util';

import './OrderDetailsHeader.scss';
import OrderDetailsStatusDescription from './OrderDetailsStatusDescription';

type OrderDetailsProps = {
	className: string;
	hasOrderDescription?: string;
	hasOrderDetails?: boolean;
	image?: string;
	name?: string;
	order?: Cart;
	productOwner?: string;
	version?: string;
};

const OrderDetailsHeader: React.FC<OrderDetailsProps> = ({
	className,
	hasOrderDescription = false,
	hasOrderDetails = false,
	image,
	name,
	order,
	productOwner,
	version,
}) => (
	<div className={className}>
		<div className="d-flex flex-row">
			<img
				alt="App Icon"
				className="rounded"
				height="56px"
				src={showAppImage(image)}
				width="56px"
			/>

			<div className="align-items-center ml-4">
				<div className="d-flex justify-content-start">
					<h2 className="text-weight-bold">{name}</h2>
					{version && <p className="ml-2 my-1">v{version}</p>}
				</div>

				{hasOrderDetails && (
					<OrderDetailsStatusDescription
						order={order}
						productOwner={productOwner}
					/>
				)}

				{hasOrderDescription && (
					<div className="header-description text-capitalize">
						{hasOrderDescription}
					</div>
				)}
			</div>
		</div>
	</div>
);

export default OrderDetailsHeader;
