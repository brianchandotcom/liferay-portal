/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import {MDFRequestListItem} from '../../../common/interfaces/mdfRequestListItem';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../../common/services/liferay/object/useGet';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import getMDFActivityPeriod from '../utils/getMDFActivityPeriod';
import getMDFBudgetInfos from '../utils/getMDFBudgetInfos';
import getMDFDates from '../utils/getMDFDates';

export default function useGetListItemsFromMDFRequests(
	isCSV: boolean,
	page: number,
	pageSize: number,
	urlParams: URLSearchParams
) {
	const swrResponse = useGet<LiferayItems<MDFRequestDTO[]>>(
		urlParams &&
			`/o/${
				LiferayAPIs.OBJECT
			}/mdfrequests?${urlParams.toString()}&page=${page}&pageSize=${pageSize}`
	);

	const listItems = useMemo(() => {
		return swrResponse.data?.items?.map((item) => {
			const baseItem: MDFRequestListItem = {
				[MDFColumnKey.ID]: String(item.id),
				[MDFColumnKey.CAMPAIGN_NAME]: item.overallCampaignName,
				...getMDFActivityPeriod(
					item.minDateActivity,
					item.maxDateActivity
				),
				[MDFColumnKey.REQUEST_STATUS]: item.mdfRequestStatus?.name,
				[MDFColumnKey.PARTNER]: item.companyName,
				[MDFColumnKey.AMOUNT_PAID]: !Number(item.totalPaidAmount)
					? '-'
					: getIntlNumberFormat(item.currency).format(
							Number(item.totalPaidAmount)
						),
				[MDFColumnKey.AMOUNT_CLAIMED]: !Number(item.totalClaimedRequest)
					? '-'
					: getIntlNumberFormat(item.currency).format(
							Number(item.totalClaimedRequest)
						),
				...getMDFDates(item.submitDate, item.dateModified),
				...getMDFBudgetInfos(item.totalMDFRequestAmount, item.currency),
			};

			isCSV
				? delete baseItem[MDFColumnKey.ACCOUNT_ENTRY_ID]
				: (baseItem[MDFColumnKey.ACCOUNT_ENTRY_ID] =
						item.r_accToMDFReqs_accountEntryId);

			return baseItem;
		});
	}, [isCSV, swrResponse.data?.items]);

	return {
		...swrResponse,
		data: {
			...swrResponse.data,
			items: listItems,
		},
	};
}
