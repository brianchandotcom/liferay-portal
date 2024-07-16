/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getLicenseKeyPermanentStatus = (startDateSelected, expirationDate) => {
	const YEARS_FOR_PERMANENT_KEYS = 80;

	const hasStartDateSelected = startDateSelected
		? new Date(startDateSelected)
		: new Date();

	const selectedDateFormatted = hasStartDateSelected;

	const unlimitedLicenseDate = selectedDateFormatted.setFullYear(
		selectedDateFormatted.getFullYear() + YEARS_FOR_PERMANENT_KEYS
	);

	const isPermanentLicenseKey =
		new Date(expirationDate) >= new Date(unlimitedLicenseDate);

	return isPermanentLicenseKey;
};

export {getLicenseKeyPermanentStatus};
