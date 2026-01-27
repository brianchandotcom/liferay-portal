/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const MINIMUM_LICENSE_VERSION = 4;
const PRODUCTION_VERSION = 7.1;
const PRODUCTION_ENVIRONMENT = 'production';

const isQuarterlyVersion = (version) => /^\d{4}\.Q[1-4]$/.test(version ?? '');

const getVersionNumber = (version) => {
	if (isQuarterlyVersion(version)) {
		return NaN;
	}

	const match = String(version ?? '').match(/^(\d+(\.\d+)?)/);

	return match ? Number(match[1]) : NaN;
};

const isQuarterlyOrAbove71DXPVersion = (version) =>
	isQuarterlyVersion(version) ||
	getVersionNumber(version) >= PRODUCTION_VERSION;

export const DOWNLOADABLE_LICENSE_KEYS = {
	above71DXPVersion: (firstSelectedKey, selectedKey) =>
		isQuarterlyOrAbove71DXPVersion(firstSelectedKey?.productVersion) &&
		isQuarterlyOrAbove71DXPVersion(selectedKey?.productVersion),
	below71DXPVersion: (firstSelectedKey, selectedKey) =>
		getVersionNumber(firstSelectedKey?.productVersion) <
			PRODUCTION_VERSION &&
		firstSelectedKey?.expirationDate === selectedKey?.expirationDate &&
		firstSelectedKey?.licenseEntryType === PRODUCTION_ENVIRONMENT &&
		firstSelectedKey?.licenseVersion >= MINIMUM_LICENSE_VERSION &&
		firstSelectedKey?.productVersion === selectedKey?.productVersion &&
		firstSelectedKey?.sizing === selectedKey?.sizing &&
		firstSelectedKey?.startDate === selectedKey?.startDate &&
		selectedKey?.licenseVersion >= MINIMUM_LICENSE_VERSION,
};
