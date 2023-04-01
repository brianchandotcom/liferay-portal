/* eslint-disable no-console */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import chalk from 'chalk';

const colors = {
    DEBUG: chalk.cyan,
    INFO: chalk.blue,
    WARNING: chalk.yellow,
};

const getLoggerPrefix = (type: keyof typeof colors) =>
    `🦊 ${new Date().toISOString()} - ${colors[type](`${type}:`)}`;

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
