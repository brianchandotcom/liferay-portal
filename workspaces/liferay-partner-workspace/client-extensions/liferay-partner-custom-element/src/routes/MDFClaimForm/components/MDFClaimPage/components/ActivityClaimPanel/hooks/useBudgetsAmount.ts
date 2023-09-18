/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

import useDebounce from '../../../../../../../common/hooks/useDebounce';
import MDFClaimBudget from '../../../../../../../common/interfaces/mdfClaimBudget';

export default function useBudgetsAmount(
	budgets: MDFClaimBudget[] | undefined,
	onAmountUpdate: (value: number) => void
) {
	const debouncedBudgets = useDebounce<MDFClaimBudget[] | undefined>(
		budgets,
		500
	);

	useEffect(() => {
		const amountValue = debouncedBudgets?.reduce<number>(
			(previousValue, currentValue) => {
				if (!currentValue.selected) {
					return previousValue;
				}

				return previousValue + Number(currentValue.invoiceAmount);
			},
			0
		);

		if (amountValue !== undefined) {
			onAmountUpdate(amountValue);
		}
	}, [debouncedBudgets, onAmountUpdate]);
}
