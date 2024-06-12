/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import OpportunityPartnerRoleDTO from '../../../common/interfaces/dto/opportunityPartnerRoleDTO';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';
import {getIntlNumberFormatString} from '../../../common/utils/getIntlNumberFormat';

export default function getItemPartnerOpportunity(
	item: OpportunityPartnerRoleDTO
) {
	return {
		[PartnerOpportunitiesColumnKey.ACTIVE]: item.active
			? item.active
			: false,
		[PartnerOpportunitiesColumnKey.CLOSE_DATE]: item.closeDate
			? getDateCustomFormat(
					item.closeDate,
					customFormatDateOptions.SHORT_MONTH
				)
			: '- ',
		[PartnerOpportunitiesColumnKey.CREATED_DATE]: item.dateCreated
			? getDateCustomFormat(
					item.dateCreated,
					customFormatDateOptions.SHORT_MONTH
				)
			: '- ',
		[PartnerOpportunitiesColumnKey.OPPORTUNITY_ACCOUNT_NAME]:
			item.accountName ? item.accountName : ' - ',
		[PartnerOpportunitiesColumnKey.CURRENCY]: item.currency
			? item.currency
			: '- ',
		[PartnerOpportunitiesColumnKey.GROWTH_ARR]: item.growthArr
			? item.growthArr
			: '- ',
		[PartnerOpportunitiesColumnKey.HAS_RENEWAL]: item.hasRenewal
			? item.hasRenewal
			: false,
		[PartnerOpportunitiesColumnKey.LIFERAY_REP]: item.ownerName
			? item.ownerName
			: ' - ',
		[PartnerOpportunitiesColumnKey.OPPORTUNITY]: item.opportunity
			? item.opportunity
			: '',
		[PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME]:
			item.partnerAccountName ? item.partnerAccountName : ' - ',
		[PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL]: item.partnerEmail
			? item.partnerEmail
			: ' - ',
		[PartnerOpportunitiesColumnKey.PARTNER_REP_NAME]: `${
			item.partnerFirstName ? item.partnerFirstName : ''
		}${item.partnerLastName ? ' ' + item.partnerLastName : ''}`,
		[PartnerOpportunitiesColumnKey.STAGE]: item.stage ? item.stage : '- ',
		[PartnerOpportunitiesColumnKey.SUBSCRIPTION_ARR]:
			item.subscriptionArr && item.currency
				? getIntlNumberFormatString(item.currency).format(
						item.subscriptionArr
					)
				: '- ',
		[PartnerOpportunitiesColumnKey.SUBSCRIPTION_TERM]: item.subscriptionTerm
			? item.subscriptionTerm
			: ' - ',
		[PartnerOpportunitiesColumnKey.TYPE]: item.type ? item.type : '- ',
	};
}
