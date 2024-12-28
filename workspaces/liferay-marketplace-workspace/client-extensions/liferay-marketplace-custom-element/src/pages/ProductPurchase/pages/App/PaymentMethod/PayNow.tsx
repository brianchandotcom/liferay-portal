/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import {RadioCard} from '../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../components/Section/Section';
import LicenseTermsCheckbox from '../License/LicenseTermsCheckbox';

const paymentModes = ['PayPal'];

const PayNow = () => (
	<Section label="Payment Method" required>
		{paymentModes.map((paymentMode, index) => (
			<RadioCard
				key={index}
				onChange={() => {}}
				selected
				small
				title={paymentMode}
			/>
		))}

		<LicenseTermsCheckbox />

		<div className="d-flex flex-column mt-5 text-gray text-right">
			<span className="text-2">
				You will be redirected to PayPal to complete payment
			</span>

			<span className="text-2">
				<ClayIcon className="mr-2" symbol="info-panel-open" />
				Terms, privacy, returns, or contact support. All costs are in US
				Dollars
			</span>
		</div>
	</Section>
);

export default PayNow;
