/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CardButton} from '../CardButton/CardButton';

type PaymentMethod = 'order' | 'pay' | 'trial';

export function PaymentMethodSelector({
	enableTrial,
	selectedPaymentMethod,
	setSelectedPaymentMethod,
}: {
	enableTrial: boolean;
	selectedPaymentMethod: string;
	setSelectedPaymentMethod: (value: PaymentMethodSelector) => void;
}) {
	return (
		<>
			{['trial', 'pay', 'order'].map((method) => {
				let description;
				let title;
				let disabled = false;
				if (method === 'pay') {
					description = 'Pay today';
					title = 'Pay Now';
				}
				else if (method === 'trial') {
					description = 'Try now. Pay later.';
					disabled = !enableTrial;
					title = '30-day Trial';
				}
				else {
					description = 'Request a PO';
					title = 'Purchase Order';
				}

				return (
					<CardButton
						description={description}
						disabled={disabled}
						key={method}
						onClick={() => {
							if (!disabled) {
								setSelectedPaymentMethod(
									method as PaymentMethod
								);
							}
						}}
						selected={method === selectedPaymentMethod}
						title={title}
					/>
				);
			})}
		</>
	);
}
