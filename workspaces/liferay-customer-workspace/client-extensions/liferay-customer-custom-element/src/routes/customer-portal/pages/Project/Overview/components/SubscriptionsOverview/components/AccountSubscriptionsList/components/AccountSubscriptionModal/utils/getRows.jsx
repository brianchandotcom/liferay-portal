/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';
import {getLicenseKeyPermanentStatus} from '~/routes/customer-portal/containers/GenerateNewKey/utils/licenseKeyPermanentStatus';
import {getPerpetualValidStartDate} from '~/routes/customer-portal/containers/GenerateNewKey/utils/perpetualValidStartDate';
import {StatusTag} from '../../../../../../../../../../../../common/components';
import {
	FORMAT_DATE_TYPES,
	SLA_STATUS_TYPES,
} from '../../../../../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../../../../../common/utils/getDateCustomFormat';
import {getSubscriptionStatus} from '~/routes/customer-portal/utils/getSubscriptionStatus'

export default function getRows(orderItems) {
	return orderItems?.map(({options, quantity, reducedCustomFields}) => {
		const isPermanentLicenseKey = getLicenseKeyPermanentStatus(
			options?.startDate,
			options?.endDate
		);

		const isValidPerpetualStartDate = getPerpetualValidStartDate(
			options?.startDate
		);

		const datesDisplay =
			isPermanentLicenseKey && isValidPerpetualStartDate
				? i18n.translate('not-applicable')
				: `${getDateCustomFormat(
						options?.startDate,
						FORMAT_DATE_TYPES.day2DMonth2DYearN
				  )} - ${getDateCustomFormat(
						options?.endDate,
						FORMAT_DATE_TYPES.day2DMonth2DYearN
				  )}`;

		return {
			'instance-size': options?.instanceSize,
			'provisioned': reducedCustomFields?.provisionedCount,
			quantity,
			'start-end-date': datesDisplay,
			'subscription-term-status': reducedCustomFields?.status && (
				<StatusTag
					currentStatus={
						getSubscriptionStatus(new Date(options?.startDate), new Date(options?.endDate))
					}
				/>
			),
		};
	});
}
