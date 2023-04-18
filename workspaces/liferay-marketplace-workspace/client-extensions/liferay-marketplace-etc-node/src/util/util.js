'use strict';

import config from './configTreePath.js';
import {existsSync, readFileSync} from 'fs';
import log from './log.js';
import {join} from 'path';

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