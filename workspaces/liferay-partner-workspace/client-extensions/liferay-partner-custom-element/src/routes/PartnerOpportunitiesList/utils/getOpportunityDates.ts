/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getOpportunityDates(
	startDate?: string,
	endDate?: string
) {
	if (startDate && endDate) {
		const startDateCustomFormat = getDateCustomFormat(
			startDate,
			customFormatDateOptions.SHORT_MONTH
		);

		const endDateCustomFormat = getDateCustomFormat(
			endDate,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[PartnerOpportunitiesColumnKey.START_DATE]: startDateCustomFormat,
			[PartnerOpportunitiesColumnKey.END_DATE]: endDateCustomFormat,
		};
	}
}
