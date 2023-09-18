/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dealsChartStatus} from './constants/dealsChartStatus';

export default function isNotOpportunity(opportunity: any) {
	const stagesToSkip = [
		dealsChartStatus.STAGE_CLOSEDLOST,
		dealsChartStatus.STAGE_CLOSEDWON,
		dealsChartStatus.STAGE_DISQUALIFIED,
		dealsChartStatus.STAGE_REJECTED,
		dealsChartStatus.STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY,
	];

	return stagesToSkip.includes(opportunity.stage);
}
