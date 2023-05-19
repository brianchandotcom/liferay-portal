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
