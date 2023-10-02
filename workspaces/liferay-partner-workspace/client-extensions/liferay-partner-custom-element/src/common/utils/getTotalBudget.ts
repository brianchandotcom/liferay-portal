/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivity from '../interfaces/mdfRequestActivity';

export default function getTotalBudget(
	mdfRequestActivities: MDFRequestActivity[]
) {
	return mdfRequestActivities.reduce(
		(previousValue: number, currentValue: MDFRequestActivity) => {
			if (!currentValue.removed) {
				const sumBudgets = currentValue.budgets.reduce(
					(previousValue, currentValue) =>
						previousValue +
						((!currentValue.removed && currentValue.cost) || 0),
					0
				);

				return previousValue + sumBudgets;
			}

			return previousValue;
		},
		0
	);
}
