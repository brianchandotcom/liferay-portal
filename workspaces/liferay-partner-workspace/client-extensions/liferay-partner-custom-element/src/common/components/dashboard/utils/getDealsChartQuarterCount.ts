/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {quarterIndexes} from './constants/quartersIndex';

export default function getChartQuarterCount(values: any[], dateCreated: any) {
	const quarter = Math.ceil((new Date(dateCreated).getMonth() + 1) / 3);

	if (quarter === 1) {
		values[quarterIndexes.QUARTER_1_INDEX]++;
	}
	if (quarter === 2) {
		values[quarterIndexes.QUARTER_2_INDEX]++;
	}
	if (quarter === 3) {
		values[quarterIndexes.QUARTER_3_INDEX]++;
	}
	if (quarter === 4) {
		values[quarterIndexes.QUARTER_4_INDEX]++;
	}

	return values;
}
