/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const logError = (...args) => {
	console.error(...args); // eslint-disable-line
};

const logInfo = (...args) => {
	console.info(...args); // eslint-disable-line
};

const logWarn = (...args) => {
	console.warn(...args); // eslint-disable-line
};

module.exports = {
	logError,
	logInfo,
	logWarn,
};
