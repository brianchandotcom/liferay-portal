/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Colors} from '~/shared/util/charts';

export const Icons = {
	negative: 'caret-bottom-l',
	neutral: undefined,
	positive: 'caret-top-l',
};

/**
 * Return the icon name
 * @param {number} number
 */
export const getIcon = function getIcon(number) {
	if (number > 0) {
		return Icons.positive;
	}
	else if (number < 0) {
		return Icons.negative;
	}
	else {
		return Icons.neutral;
	}
};

/**
 * Return the current color
 * @param {string} str
 */
export const getStatsColor = function getStatsColor(str) {
	if (str) {
		if (str.toLowerCase() === 'positive') {
			return Colors.positive;
		}
		else if (str.toLowerCase() === 'negative') {
			return Colors.negative;
		}
		else {
			return Colors.neutral;
		}
	}
	else {
		return Colors.neutral;
	}
};

/**
 * Return the current number sign
 * @param {number} number
 */

export function getTrendSign(number) {
	if (number > 0) {
		return '+';
	}
	if (number < 0) {
		return '-';
	}

	return '';
}
