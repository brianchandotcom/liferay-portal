/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLicenseKeyPermanentStatus} from '~/routes/customer-portal/containers/GenerateNewKey/utils/licenseKeyPermanentStatus';
import i18n from '../../../../../../../common/I18n';
import {FORMAT_DATE_TYPES} from '../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../common/utils/getDateCustomFormat';

const ExpirationDateColumn = ({activationKey}) => {
	const isPermanentLicenseKey = getLicenseKeyPermanentStatus(
		activationKey?.startDate,
		activationKey?.expirationDate
	);

	if (isPermanentLicenseKey) {
		return (
			<p
				className="cp-activation-key-cell-small font-weight-bold m-0 text-neutral-10"
				title={[i18n.translate('this-key-does-not-expire')]}
			>
				{getDateCustomFormat(
					activationKey.startDate,
					FORMAT_DATE_TYPES.day2DMonthSYearN
				)}

				<br></br>

				{i18n.translate('dne')}
			</p>
		);
	}

	return (
		<p className="font-weight-bold m-0 text-neutral-10">
			{getDateCustomFormat(
				activationKey.startDate,
				FORMAT_DATE_TYPES.day2DMonthSYearN
			)}

			<br></br>

			{getDateCustomFormat(
				activationKey.expirationDate,
				FORMAT_DATE_TYPES.day2DMonthSYearN
			)}
		</p>
	);
};

export {ExpirationDateColumn};
