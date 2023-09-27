/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dealsChartStatus} from './constants/dealsChartStatus';

export default function isNotOpportunity(opportunity: any) {
	const stagesToSkip = [
		dealsChartStatus.STAGE_CLOSEDWON,
		dealsChartStatus.STAGE_REJECTED,
	];

	return stagesToSkip.includes(opportunity.stage);
}
