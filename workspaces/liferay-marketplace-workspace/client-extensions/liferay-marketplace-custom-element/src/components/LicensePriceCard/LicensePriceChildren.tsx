/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import './LicensePriceChildren.scss';
import unitedStatesIcon from '../../assets/icons/united_states_icon.svg';

type Quantity = {
	from: string;
	to: string;
};

interface LicensePriceChildren {
	currency: string;
	quantity: Quantity;
	value: string;
}

export function LicensePriceChildren({
	currency,
	quantity,
	value,
}: LicensePriceChildren) {
	return (
		<div className="license-container">
			<div className="license-type">
				<span>Standard Licenses</span>
			</div>

			<div>
				<span className="license-title">From</span>

				<span className="license-value">{quantity.from}</span>

				<span className="license-title">To</span>

				<span className="license-value">{quantity.to}</span>
			</div>

			<div>
				<span>-</span>
			</div>

			<div className="license-currency">
				<div className="license-currency-icon">
					<img src={unitedStatesIcon} />
				</div>

				<span className="license-currency-country-abbreviation">
					{currency}
				</span>

				<span className="license-currency-value">{value}</span>
			</div>

			<div>
				<span>-</span>
			</div>

			<div className="license-total">
				<span className="license-title">Total</span>

				<span className="license-value">{value}</span>
			</div>
		</div>
	);
}
