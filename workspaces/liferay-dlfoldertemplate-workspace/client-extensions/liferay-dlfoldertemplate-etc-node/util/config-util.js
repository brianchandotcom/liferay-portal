/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import config from './configTreePath.js';

export function getConfigByKey(configKey) {
	configKey = toConstantCase(configKey);

	if (process.env[configKey] && process.env[configKey].length > 1) {
		return process.env[configKey.toString()];
	}
	else {
		configKey = toDotCase(configKey);

		if (config[configKey]) {
			return config[configKey];
		}
		else {
			throw new Error(
				`"Provided key '${configKey}' has not been found!"`
			);
		}
	}
}

export function getOAuthConfigByKey(externalReferenceCode, configKey) {
	configKey = `${externalReferenceCode}${configKey}`;

	if (process.env[configKey] && process.env[configKey].length > 1) {
		return process.env[toConstantCase(configKey)];
	}
	else {
		configKey = toDotCase(configKey);

		if (config[configKey]) {
			return config[configKey];
		}
		else {
			throw new Error(
				`"Provided key '${configKey}' has not been found!"`
			);
		}
	}
}

function toConstantCase(key) {
	return key
		.toString()
		.toUpperCase()
		.replaceAll('-', '_')
		.replaceAll('.', '_');
}

function toDotCase(key) {
	return key
		.toString()
		.toLowerCase()
		.replaceAll('_', '.')
		.replaceAll('-', '.');
}




