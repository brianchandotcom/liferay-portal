/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DealRegistrationColumnKey} from '../../../common/enums/dealRegistrationColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getDealDates(startDate?: string) {
	if (startDate) {
		const startDateCustomFormat = getDateCustomFormat(
			startDate,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[DealRegistrationColumnKey.DATE_SUBMITTED]: startDateCustomFormat,
		};
	}
}
