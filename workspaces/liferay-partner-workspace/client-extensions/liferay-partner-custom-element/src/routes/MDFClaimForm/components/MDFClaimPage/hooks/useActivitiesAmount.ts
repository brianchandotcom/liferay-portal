/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

import useDebounce from '../../../../../common/hooks/useDebounce';
import MDFClaimActivity from '../../../../../common/interfaces/mdfClaimActivity';

export default function useActivitiesAmount(
	activities: MDFClaimActivity[] | undefined,
	onAmountUpdate: (value: number) => void
) {
	const debouncedActivities = useDebounce<MDFClaimActivity[] | undefined>(
		activities,
		500
	);

	useEffect(() => {
		const amountValue = debouncedActivities?.reduce<number>(
			(previousValue, currentValue) => {
				if (!currentValue.selected) {
					return previousValue;
				}

				return previousValue + Number(currentValue.totalCost);
			},
			0
		);

		if (amountValue !== undefined) {
			onAmountUpdate(amountValue);
		}
	}, [debouncedActivities, onAmountUpdate]);
}
