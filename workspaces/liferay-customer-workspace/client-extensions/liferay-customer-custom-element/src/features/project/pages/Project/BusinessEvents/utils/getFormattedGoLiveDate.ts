/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ITimeInput} from '~/utils/types';

function getFormattedGoLiveDateTime(
	targetGoLiveDate: string | undefined,
	targetGoLiveTime: string | ITimeInput | undefined
): string | undefined {
	if (!targetGoLiveDate || !targetGoLiveTime) {
		return undefined;
	}

	const [month, day, year] = targetGoLiveDate.split('-');

	const formattedDate = `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;

	let hours: string;
	let minutes: string;

	if (typeof targetGoLiveTime === 'string') {
		[hours, minutes] = targetGoLiveTime.split(':');
	}
	else {
		hours = targetGoLiveTime.hours.includes('-')
			? '00'
			: targetGoLiveTime.hours;
		minutes = targetGoLiveTime.minutes.includes('-')
			? '00'
			: targetGoLiveTime.minutes;
	}

	const formattedDateTime = `${formattedDate}T${hours.padStart(2, '0')}:${minutes.padStart(2, '0')}:00.000`;

	return formattedDateTime;
}

export {getFormattedGoLiveDateTime};
