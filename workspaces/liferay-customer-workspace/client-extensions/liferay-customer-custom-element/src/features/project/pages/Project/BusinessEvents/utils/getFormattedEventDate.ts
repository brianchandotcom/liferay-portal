/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ITimeInput} from '~/utils/types';

function getFormattedEventDateTime(
	plannedEventDate: string | undefined,
	plannedEventTime: string | ITimeInput | undefined
): string | undefined {
	if (!plannedEventDate || !plannedEventTime) {
		return undefined;
	}

	const [month, day, year] = plannedEventDate.split('-');

	if (!year || !month || !day) {
		return undefined;
	}

	const formattedDate = `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;

	let hours: string;
	let minutes: string;

	if (typeof plannedEventTime === 'string') {
		const splittedTime = plannedEventTime.split(':');

		if (splittedTime.length !== 2) {
			return undefined;
		}

		[hours, minutes] = splittedTime;
	}
	else {
		hours = plannedEventTime?.hours?.includes('-')
			? '00'
			: plannedEventTime?.hours ?? '00';

		minutes = plannedEventTime?.minutes?.includes('-')
			? '00'
			: plannedEventTime?.minutes ?? '00';
	}

	const formattedDateTime = `${formattedDate}T${hours.padStart(2, '0')}:${minutes.padStart(2, '0')}:00.000Z`;

	return formattedDateTime;
}

export {getFormattedEventDateTime};
