/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import DealRegistrationDTO from '../../../common/interfaces/dto/dealRegistrationDTO';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import getOpportunityAmount from './getOpportunityAmount';
import getOpportunityDates from './getOpportunityDates';

export default function getItemPartnerOpportunity(item: DealRegistrationDTO) {
	return {
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
			? getDateCustomFormat(
					item.closeDate,
					customFormatDateOptions.SHORT_MONTH
			  )
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
		[PartnerOpportunitiesColumnKey.STAGE]: item.stage ? item.stage : '- ',
		[PartnerOpportunitiesColumnKey.TYPE]: item.type ? item.type : '- ',
		[PartnerOpportunitiesColumnKey.CURRENCY]:
			item.currency && item.currency.name ? item.currency.name : '- ',
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
		[PartnerOpportunitiesColumnKey.CREATED_DATE]: item.dateCreated
			? getDateCustomFormat(
					item.dateCreated,
					customFormatDateOptions.SHORT_MONTH
			  )
			: '- ',
		[PartnerOpportunitiesColumnKey.OPPORTUNITY]: item.opportunity
			? item.opportunity
			: '',
		[PartnerOpportunitiesColumnKey.ACTIVE]: item.active
			? item.active
			: false,
	};
}
