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

	if (!year || !month || !day) {
		return undefined;
	}

	const formattedDate = `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;

	let hours: string;
	let minutes: string;

	if (typeof targetGoLiveTime === 'string') {
		const splittedTime = targetGoLiveTime.split(':');

		if (splittedTime.length !== 2) {
			return undefined;
		}

		[hours, minutes] = splittedTime;
	}
	else {
		hours = targetGoLiveTime?.hours?.includes('-')
			? '00'
			: targetGoLiveTime?.hours ?? '00';

		minutes = targetGoLiveTime?.minutes?.includes('-')
			? '00'
			: targetGoLiveTime?.minutes ?? '00';
	}

	const formattedDateTime = `${formattedDate}T${hours.padStart(2, '0')}:${minutes.padStart(2, '0')}:00.000`;

	return formattedDateTime;
}

export {getFormattedGoLiveDateTime};
