/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivity from '../interfaces/mdfRequestActivity';

export default function getTotalMDFRequest(
	mdfRequestActivities: MDFRequestActivity[]
) {
	return mdfRequestActivities.reduce(
		(previousValue: number, currentValue: MDFRequestActivity) => {
			const sumAmount = !currentValue.removed
				? currentValue.mdfRequestAmount
				: 0;

			return previousValue + sumAmount;
		},
		0
	);
}
