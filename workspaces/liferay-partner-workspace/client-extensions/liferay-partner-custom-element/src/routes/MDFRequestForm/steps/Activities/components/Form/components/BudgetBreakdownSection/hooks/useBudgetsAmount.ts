/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import useDebounce from '../../../../../../../../../common/hooks/useDebounce';
import MDFRequestBudget from '../../../../../../../../../common/interfaces/mdfRequestBudget';

export default function useBudgetsAmount(
	budgets: MDFRequestBudget[],
	onAmountUpdate: (value: number) => void
) {
	const [budgetsAmount, setBudgetsAmount] = useState<number>(0);
	const debouncedBudgets = useDebounce<MDFRequestBudget[]>(budgets, 500);

	useEffect(() => {
		const amountValue = debouncedBudgets.reduce<number>(
			(previousValue, currentValue) =>
				previousValue + +(!currentValue.removed && currentValue.cost),
			0
		);

		setBudgetsAmount(amountValue);
		onAmountUpdate(amountValue);
	}, [debouncedBudgets, onAmountUpdate]);

	return budgetsAmount;
}
