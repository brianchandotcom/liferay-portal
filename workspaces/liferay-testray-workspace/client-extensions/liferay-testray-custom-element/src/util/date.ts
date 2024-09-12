/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration';
import localizedFormat from 'dayjs/plugin/localizedFormat';
import relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(duration);
dayjs.extend(localizedFormat);
dayjs.extend(relativeTime);

export function getTimeFromNow(date: string): string {
	return dayjs(date).fromNow();
}

export function formatUTCDate(date: string): string {
	if (date.includes('Z')) {
		return dayjs(date.replace('Z', '')).format('lll');
	}

	return dayjs(date).format('lll');
}

export function getDurationTime(milliseconds: number): string {
	const duration = dayjs.duration(milliseconds);

	return (
		(duration.hours() > 0 && duration.hours() < 10
			? '0' + duration.hours() + ':'
			: '') +
		(duration.minutes() > 9
			? duration.minutes()
			: '0' + duration.minutes()) +
		':' +
		(duration.seconds() > 9
			? duration.seconds()
			: '0' + duration.seconds()) +
		'.' +
		duration.milliseconds()
	);
}

export default dayjs;
