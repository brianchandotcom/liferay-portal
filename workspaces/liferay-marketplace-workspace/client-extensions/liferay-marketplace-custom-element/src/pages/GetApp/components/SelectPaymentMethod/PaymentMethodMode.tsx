/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RadioCard} from '../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../components/Section/Section';

const paymentModes = ['PayPal'];

const PaymentMethodMode = ({
	selectedPaymentMethod,
}: {
	selectedPaymentMethod: PaymentMethodSelector;
}) => (
	<Section className="get-app-modal-section" label="Payment Method" required>
		{paymentModes.map((paymentMode, index) => (
			<RadioCard
				key={index}
				onChange={() => {}}
				selected={selectedPaymentMethod === 'pay'}
				small
				title={paymentMode}
			/>
		))}
	</Section>
);

export {PaymentMethodMode};
