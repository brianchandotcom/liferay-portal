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

import {existsSync, readFileSync} from 'fs';
import {join} from 'path';

import config from './configTreePath';
import log from './log';

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
