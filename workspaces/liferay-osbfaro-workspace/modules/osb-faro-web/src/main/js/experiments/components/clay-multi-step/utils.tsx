/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const getStatus = function getStatus(
	stepNumber: number,
	current: number
) {
	let status = 'wait';

	if (stepNumber === current) {
		status = 'active';
	}
	else if (stepNumber < current) {
		status = 'complete';
	}

	return status;
};
