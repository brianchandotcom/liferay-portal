/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {existsSync, readFileSync} from 'fs';
import {join} from 'path';

import config from './configTreePath.js';
import log from './log.js';

export function getExtInitMetadata(property, defaultValue) {
	const configPath = join('/etc/liferay/lxc/ext-init-metadata', property);
	let extInitMetadata;
	if (existsSync(configPath)) {
		extInitMetadata = readFileSync(configPath, 'utf-8');
	}
	else {
		extInitMetadata = defaultValue;
	}
	log.info('getExtInitMetadata: ' + property + ' = ' + extInitMetadata);

	return extInitMetadata;
}

export function getDXPMetadata(property) {
	const configPath = join('/etc/liferay/lxc/dxp-metadata', property);
	let dxpMetadata;
	if (existsSync(configPath)) {
		dxpMetadata = readFileSync(configPath, 'utf-8');
	}
	else {
		dxpMetadata = config[property];
	}
	log.info('getDXPMetadata: ' + property + ' = ' + dxpMetadata);

	return dxpMetadata;
}
