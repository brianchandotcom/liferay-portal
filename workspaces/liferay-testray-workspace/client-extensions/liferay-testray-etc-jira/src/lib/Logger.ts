/* eslint-disable no-console */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import chalk from 'chalk';

const colors = {
	DEBUG: chalk.cyan,
	INFO: chalk.blue,
	WARNING: chalk.yellow,
};

const getLoggerPrefix = (type: keyof typeof colors) =>
	`🔹 ${new Date().toISOString()} - ${colors[type](`${type}:`)}`;

const logger = {
	...console,
	debug(...log: any[]) {
		console.log(getLoggerPrefix('DEBUG'), ...log);
	},
	info(...log: any[]) {
		console.log(getLoggerPrefix('INFO'), ...log);
	},
	warning(...log: any[]) {
		console.log(getLoggerPrefix('WARNING'), ...log);
	},
};

export default logger;
