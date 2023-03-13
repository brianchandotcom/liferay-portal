/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const path = require('path');

let config = {};

try {
	config = require('@liferay/npm-scripts/src/config/eslint.config');
}
catch (error) {
	throw new Error(
		'@liferay/npm-scripts is not installed.'
	);
}

module.exports = {
	...config,
	globals: {
		...config.globals,
		MODULE_PATH: true,
	},
	rules: {
		'@liferay/portal/no-global-fetch': 'off',
		'@liferay/portal/no-localhost-reference': 'off',
		'notice/notice': [
			'error',
			{
				templateFile: path.join(__dirname, 'copyright.js'),
			},
		],
	},
};
