/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const PRODUCTION_VERSION = 7.1;
const PRODUCTION_ENVIRONMENT = 'production';

export const DOWNLOADABLE_LICENSE_KEYS = {
	above71DXPVersion: (firstSelectedKey, selectedKey) =>
		+selectedKey?.productVersion >= PRODUCTION_VERSION &&
		+firstSelectedKey?.productVersion >= PRODUCTION_VERSION,
	below71DXPVersion: (firstSelectedKey, selectedKey) =>
		firstSelectedKey?.licenseEntryType === PRODUCTION_ENVIRONMENT &&
		firstSelectedKey?.sizing === selectedKey?.sizing &&
		firstSelectedKey?.startDate === selectedKey?.startDate &&
		firstSelectedKey?.expirationDate === selectedKey?.expirationDate &&
		firstSelectedKey?.productVersion === selectedKey?.productVersion,
};
