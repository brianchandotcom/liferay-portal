/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import useGetDealRegistration from '../../../common/services/liferay/object/deal-registration/useGetDealRegistration';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import getOpportunityAmount from '../utils/getOpportunityAmount';
import getOpportunityDates from '../utils/getOpportunityDates';

export default function useGetListItemsFromPartnerOpportunities(
	page: number,
	pageSize: number,
	filtersTerm: string,
	sort: string,
	opportunityFilter?: string
) {
	const swrResponse = useGetDealRegistration(
		ResourceName.OPPORTUNITIES_SALESFORCE,
		page,
		pageSize,
		filtersTerm,
		opportunityFilter ? `&filter=${opportunityFilter}` : '',
		sort
	);

	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) => ({
				[PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME]: item.partnerAccountName
					? item.partnerAccountName
					: ' - ',
				[PartnerOpportunitiesColumnKey.PARTNER_NAME]: `${
					item.partnerFirstName ? item.partnerFirstName : ''
				}${item.partnerLastName ? ' ' + item.partnerLastName : ''}`,
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
					? getOpportunityAmount(item.amount)
					: {[PartnerOpportunitiesColumnKey.DEAL_AMOUNT]: ' - '}),

				[PartnerOpportunitiesColumnKey.CLOSE_DATE]: item.closeDate
					? item.closeDate
					: '- ',
				[PartnerOpportunitiesColumnKey.PARTNER_REP_NAME]: `${
					item.primaryContactFirstName
						? item.primaryContactFirstName
						: ' - '
				}${
					item.primaryContactLastName
						? ' ' + item.primaryContactLastName
						: ' '
				}`,
				[PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL]: item.primaryContactEmail
					? item.primaryContactEmail
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
				[PartnerOpportunitiesColumnKey.CURRENCY]: item.currency.name
					? item.currency.name
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
