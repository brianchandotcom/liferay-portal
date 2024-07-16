/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FORMAT_DATE_TYPES} from '../../../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../../../common/utils/getDateCustomFormat';

export default function getSLACard(endDate, startDate, title, label) {
	return {
		endDate: getDateCustomFormat(
			endDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		),
		label,
		startDate: getDateCustomFormat(
			startDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		),
		title: title.split(' ')[0],
	};
}
