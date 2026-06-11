/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function timeOfDay() {
	const hour = new Date().getHours();

	if (hour < 5) {
		return 'night';
	}
	else if (hour < 12) {
		return 'morning';
	}
	else if (hour < 18) {
		return 'afternoon';
	}
	else if (hour < 22) {
		return 'evening';
	}

	return 'night';
}

export function touchDevice() {
	return navigator.maxTouchPoints > 0;
}
