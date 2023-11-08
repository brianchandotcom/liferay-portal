/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import DealRegistrationDTO from '../../../common/interfaces/dto/dealRegistrationDTO';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../common/services/liferay/common/interfaces/liferayItems';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import useGet from '../../../common/services/liferay/object/useGet';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import getOpportunityAmount from '../utils/getOpportunityAmount';
import getOpportunityDates from '../utils/getOpportunityDates';

export default function useGetListItemsFromPartnerOpportunities(
	page: number,
	pageSize: number,
	filtersTerm: string,
	sort: string
) {
	const swrResponse = useGet<LiferayItems<DealRegistrationDTO[]>>(
		filtersTerm &&
			`/o/${LiferayAPIs.OBJECT}/${ResourceName.OPPORTUNITIES_SALESFORCE}?&filter=${filtersTerm}&page=${page}&pageSize=${pageSize}&sort=${sort}`
	);

	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) => ({
				[PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME]: item.partnerAccountName
					? item.partnerAccountName
					: ' - ',
				...(item.projectSubscriptionStartDate
					? getOpportunityDates(
							item.projectSubscriptionStartDate,
							item.projectSubscriptionEndDate
					  )
					: {
							[PartnerOpportunitiesColumnKey.START_DATE]: ' - ',
							[PartnerOpportunitiesColumnKey.END_DATE]: ' - ',
					  }),
				[PartnerOpportunitiesColumnKey.ACCOUNT_NAME]: item.accountName
					? item.accountName
					: ' - ',
				...(item.amount
					? getOpportunityAmount(item.amount, item.currency)
					: {[PartnerOpportunitiesColumnKey.DEAL_AMOUNT]: ' - '}),

				[PartnerOpportunitiesColumnKey.CLOSE_DATE]: item.closeDate
					? item.closeDate
					: '- ',
				[PartnerOpportunitiesColumnKey.PARTNER_REP_NAME]: `${
					item.partnerFirstName ? item.partnerFirstName : ''
				}${item.partnerLastName ? ' ' + item.partnerLastName : ''}`,
				[PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL]: item.partnerEmail
					? item.partnerEmail
					: ' - ',
				[PartnerOpportunitiesColumnKey.LIFERAY_REP]: item.ownerName
					? item.ownerName
					: ' - ',
				[PartnerOpportunitiesColumnKey.STAGE]: item.stage
					? item.stage
					: '- ',
				[PartnerOpportunitiesColumnKey.TYPE]: item.type
					? item.type
					: '- ',
				[PartnerOpportunitiesColumnKey.CURRENCY]:
					item.currency && item.currency.name
						? item.currency.name
						: '- ',
				[PartnerOpportunitiesColumnKey.SUBSCRIPTION_ARR]:
					item.subscriptionArr && item.currency && item.currency.key
						? getIntlNumberFormat(item.currency).format(
								item.subscriptionArr
						  )
						: '- ',
				[PartnerOpportunitiesColumnKey.GROWTH_ARR]: item.growthArr
					? item.growthArr
					: '- ',
				[PartnerOpportunitiesColumnKey.HAS_RENEWAL]: item.hasRenewal
					? item.hasRenewal
					: false,
				[PartnerOpportunitiesColumnKey.FISCAL_PERIOD]: item.fiscalPeriod
					? item.fiscalPeriod
					: '- ',
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
