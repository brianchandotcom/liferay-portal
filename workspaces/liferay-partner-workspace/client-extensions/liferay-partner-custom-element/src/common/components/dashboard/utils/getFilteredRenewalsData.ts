/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OpportunityType} from '../../../enums/opportunityType';
import Opportunity from '../../../interfaces/opportunity';
import {stageStatus} from './constants/stageStatusRenewalsChart';

const EXPIRATION_DAYS = 30;
const TODAY = new Date();
const MILISECONDS_PER_DAY = 1000 * 3600 * 24;

export default function getFilteredRenewals(data: any) {
	const newRenewalsArray: Opportunity[] = [];

	data?.items?.forEach((renewal: Opportunity) => {
		const expirationDate = new Date(renewal.closeDate);
		const differenceOfTime = expirationDate.getTime() - TODAY.getTime();

		const differenceOfDays =
			Math.floor(differenceOfTime / MILISECONDS_PER_DAY) + 1;

		if (
			differenceOfDays > 0 &&
			differenceOfDays <= EXPIRATION_DAYS &&
			renewal.type !== OpportunityType.NEW_BUSINESS &&
			renewal.stage !== stageStatus.REJECTED &&
			renewal.stage !== stageStatus.ROLLED_INTO_ANOTHER_OPPORTUNITY &&
			renewal.stage !== stageStatus.CLOSEDLOST &&
			renewal.stage !== stageStatus.DISQUALIFIED
		) {
			newRenewalsArray.push({
				expirationDays: differenceOfDays,
				...renewal,
			});
		}
	});

	return newRenewalsArray.slice(0, 4);
}
