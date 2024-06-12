/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {MDFClaimColumnKey} from '../../../common/enums/mdfClaimColumnKey';
import MDFClaimDTO from '../../../common/interfaces/dto/mdfClaimDTO';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../../common/services/liferay/object/useGet';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import getMDFClaimAmountClaimedInfo from '../utils/getMDFBudgetInfos';

export default function useGetListItemsFromMDFClaims(
	page: number,
	pageSize: number,
	urlParams: URLSearchParams
) {
	const swrResponse = useGet<LiferayItems<MDFClaimDTO[]>>(
		urlParams &&
			`/o/${
				LiferayAPIs.OBJECT
			}/mdfclaims?${urlParams.toString()}&page=${page}&pageSize=${pageSize}`
	);

	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) => ({
				[MDFClaimColumnKey.REQUEST_ID]: String(
					item.r_mdfReqToMDFClms_c_mdfRequestId
				),
				[MDFClaimColumnKey.CLAIM_ID]: String(item.id),
				[MDFClaimColumnKey.PARTNER]: item.companyName,
				[MDFClaimColumnKey.CLAIM_STATUS]: item.mdfClaimStatus.name,
				[MDFClaimColumnKey.TYPE]: item.partial ? 'Partial' : 'Full',
				...getMDFClaimAmountClaimedInfo(
					item.totalClaimAmount,
					item.currency
				),
				[MDFClaimColumnKey.DATE_SUBMITTED]: item.submitDate
					? getDateCustomFormat(
							item.submitDate,
							customFormatDateOptions.SHORT_MONTH
						)
					: '-',
				[MDFClaimColumnKey.AMOUNT_PAID]: !item.claimPaid
					? '-'
					: getIntlNumberFormat(item.currency).format(item.claimPaid),
				[MDFClaimColumnKey.PAYMENT_DATE]: item.paymentDate
					? getDateCustomFormat(
							item.paymentDate,
							customFormatDateOptions.SHORT_MONTH
						)
					: '-',
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
