/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {showAppImage} from '../../../../utils/util';

import './OrderDetailsHeader.scss';
import OrderDetailsStatusDescription from './OrderDetailsStatusDescription';

interface OrderDetailsProps {
	hasOrderDescription?: boolean;
	hasOrderDetails?: boolean;
	orderDetails?: Cart;
	productCreatorAccount?: string;
	productImage?: string;
	productName?: string;
}

const OrderDetailsHeader = ({
	hasOrderDescription = false,
	hasOrderDetails = false,
	orderDetails,
	productCreatorAccount,
	productImage,
	productName,
}: OrderDetailsProps) => (
	<div className="pb-3 pt-5">
		<div className="d-flex flex-row justify-content-between">
			<div className="d-flex flex-row">
				<img
					alt="App Icon"
					className="rounded"
					height="56px"
					src={showAppImage(productImage)}
					width="56px"
				/>

				<div className="align-items-center ml-4">
					<h2 className="text-weight-bold">{productName}</h2>
					{hasOrderDetails && (
						<OrderDetailsStatusDescription
							orderDetails={orderDetails}
							productCreatorAccount={productCreatorAccount}
						/>
					)}

					{hasOrderDescription && <p>Order Description</p>}
				</div>
			</div>
		</div>
	</div>
);

export default OrderDetailsHeader;
