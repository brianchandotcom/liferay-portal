/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
