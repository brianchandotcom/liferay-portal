/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getPerpetualValidStartDate = (startDate) => {
	const perpetualStartDateLimit = '1971-01-01T00:00:00Z';

	const perpetualStartDateValidation =
		new Date(perpetualStartDateLimit) > new Date(startDate);

	return perpetualStartDateValidation;
};

export {getPerpetualValidStartDate};
