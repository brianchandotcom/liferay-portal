/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * We use @liferay/npm-scripts to perform linting in a controlled way, but we
 * also try to expose its configuration here so it can be picked up by editors.
 */
let config = {};

try {
	config = require('@liferay/npm-scripts/src/config/stylelint.json');
}
catch (error) {
	throw new Error('@liferay/npm-scripts is not installed; please run "yarn"');
}

module.exports = {
	...config,
	rules: {
		...config.rules,
		'liferay/no-block-comments': false,
		'selector-type-no-unknown': [
			true,
			{
				ignore: 'custom-elements',
			},
		],
	},
};
