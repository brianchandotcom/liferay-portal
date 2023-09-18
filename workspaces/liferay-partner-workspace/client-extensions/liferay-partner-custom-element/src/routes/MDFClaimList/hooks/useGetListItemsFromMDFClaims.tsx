/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {MDFClaimColumnKey} from '../../../common/enums/mdfClaimColumnKey';
import useGetMDFClaim from '../../../common/services/liferay/object/mdf-claim/useGetMDFClaim';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import getMDFClaimAmountClaimedInfo from '../utils/getMDFBudgetInfos';

export default function useGetListItemsFromMDFClaims(
	page: number,
	pageSize: number,
	filtersTerm: string
) {
	const swrResponse = useGetMDFClaim(page, pageSize, filtersTerm);

	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) => ({
				[MDFClaimColumnKey.REQUEST_ID]: String(
					item.r_mdfReqToMDFClms_c_mdfRequestId
				),
				[MDFClaimColumnKey.CLAIM_ID]: String(item.id),
				[MDFClaimColumnKey.PARTNER]: item.companyName,
				[MDFClaimColumnKey.STATUS]: item.mdfClaimStatus.name,
				[MDFClaimColumnKey.TYPE]: item.partial ? 'Partial' : 'Full',
				...getMDFClaimAmountClaimedInfo(
					item.totalClaimAmount,
					item.currency
				),
				[MDFClaimColumnKey.DATE_SUBMITTED]: getDateCustomFormat(
					item.dateCreated as string,
					customFormatDateOptions.SHORT_MONTH
				),
				[MDFClaimColumnKey.PAID]: !item.claimPaid
					? '-'
					: getIntlNumberFormat(item.currency).format(item.claimPaid),
			})),
		[swrResponse.data?.items]
	);

	return {
		...swrResponse,
		data: {
			...swrResponse.data,
			items: listItems,
		},
	};
}
