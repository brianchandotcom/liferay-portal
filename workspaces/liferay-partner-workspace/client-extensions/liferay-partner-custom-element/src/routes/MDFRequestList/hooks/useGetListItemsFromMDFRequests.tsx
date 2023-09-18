/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import getMDFActivityPeriod from '../utils/getMDFActivityPeriod';
import getMDFBudgetInfos from '../utils/getMDFBudgetInfos';
import getMDFDates from '../utils/getMDFDates';

export default function useGetListItemsFromMDFRequests(
	items?: MDFRequestDTO[]
) {
	return useMemo(
		() =>
			items?.map((item) => ({
				[MDFColumnKey.ID]: String(item.id),
				[MDFColumnKey.NAME]: item.overallCampaignName,
				...getMDFActivityPeriod(
					item.minDateActivity,
					item.maxDateActivity
				),
				[MDFColumnKey.STATUS]: item.mdfRequestStatus?.name,
				[MDFColumnKey.PARTNER]: item.companyName,
				[MDFColumnKey.PAID]: !Number(item.totalPaidAmount)
					? '-'
					: getIntlNumberFormat(item.currency).format(
							Number(item.totalPaidAmount)
					  ),
				[MDFColumnKey.AMOUNT_CLAIMED]: !Number(item.totalClaimedRequest)
					? '-'
					: getIntlNumberFormat(item.currency).format(
							Number(item.totalClaimedRequest)
					  ),
				...getMDFDates(item.dateCreated, item.dateModified),
				...getMDFBudgetInfos(
					item.totalCostOfExpense,
					item.totalMDFRequestAmount,
					item.currency,
					item.mdfRequestStatus.key
				),
			})),
		[items]
	);
}
