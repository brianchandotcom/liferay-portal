/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

/**
 * We use @liferay/npm-scripts to perform linting in a controlled way, but we
 * also try to expose its configuration here so it can be picked up by editors.
 */
let config = {};

try {
	config = require('@liferay/npm-scripts/src/config/eslint.config');
}
catch (error) {
	throw new Error('@liferay/npm-scripts is not installed; please run "yarn"');
}

config = {
	...config,
	env: {
		browser: true,
		es6: true,
		node: true,
	},
	globals: {
		...config.globals,
		MODULE_PATH: true,
	},
	overrides: [
		{
			files: [
				'liferay-sample-workspace/client-extensions/liferay-sample-custom-element-*/**',
			],
			rules: {
				'@liferay/no-abbreviations': 'off',
				'@liferay/no-it-should': 'off',
				'sort-keys': 'off',
			},
		},
	],
	rules: {
		'@liferay/empty-line-between-elements': 'off',
		'@liferay/import-extensions': 'off',
		'@liferay/portal/deprecation': 'off',
		'@liferay/portal/no-document-cookie': 'off',
		'@liferay/portal/no-explicit-extend': 'off',
		'@liferay/portal/no-global-fetch': 'off',
		'@liferay/portal/no-global-storage': 'off',
		'@liferay/portal/no-loader-import-specifier': 'off',
		'@liferay/portal/no-localhost-reference': 'off',
		'@liferay/portal/no-metal-plugins': 'off',
		'@liferay/portal/no-react-dom-create-portal': 'off',
		'@liferay/portal/no-react-dom-render': 'off',
		'@liferay/portal/no-side-navigation': 'off',
		'@liferay/portal/unexecuted-ismounted': 'off',
		'no-empty': ['error', {allowEmptyCatch: true}],
		'notice/notice': [
			'error',
			{
				nonMatchingTolerance: 0.7,
				onNonMatchingHeader: 'replace',
				templateFile: path.join(__dirname, 'copyright.js'),
			},
		],
	},
};

module.exports = config;
