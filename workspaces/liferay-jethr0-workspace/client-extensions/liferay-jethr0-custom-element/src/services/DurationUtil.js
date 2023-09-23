/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function toDurationString(duration) {
	if (!duration) {
		return '-';
	}

	const milliseconds = Math.trunc(duration % 1000);

	duration = Math.trunc(duration / 1000);

	const seconds = Math.trunc(duration % 60);

	duration = Math.trunc(duration / 60);

	const minutes = Math.trunc(duration % 60);

	duration = Math.trunc(duration / 60);

	const hours = Math.trunc(duration % 60);

	let durationString = '';

	if (hours > 0) {
		durationString += hours + 'hrs ';
	}

	if (minutes > 0) {
		durationString += minutes + 'mins ';
	}

	if (seconds > 0) {
		durationString += seconds + 'secs ';
	}

	durationString += milliseconds + 'ms';

	return durationString;
}
