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
	rules: {
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
