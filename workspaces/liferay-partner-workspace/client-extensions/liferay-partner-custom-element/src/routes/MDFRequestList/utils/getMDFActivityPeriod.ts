/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getMDFActivityPeriod(
	minDateActivity?: string,
	maxDateActivity?: string
) {
	if (minDateActivity && maxDateActivity) {
		const startDate = getDateCustomFormat(
			minDateActivity,
			customFormatDateOptions.SHORT_MONTH_YEAR
		);

		const endDate = getDateCustomFormat(
			maxDateActivity,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[MDFColumnKey.ACTIVITY_PERIOD]: `${startDate} - ${endDate}`,
		};
	}
}
