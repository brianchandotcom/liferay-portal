/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const config = {
	env: {
		browser: true,
		es2021: true,
		node: true,
	},
	extends: ['plugin:@liferay/portal'],
	globals: {
		FARO_DEV_MODE: true,
		FARO_ENV: true,
		FARO_PENDO_API_KEY: true,
		IncrementalDOM: true,
		Liferay: true,
		analytics: true,
		configuration: true,
		fragmentElement: true,
		fragmentNamespace: true,
		layoutMode: true,
		pendo: true,
		themeDisplay: true,
	},
	ignorePatterns: ['!*'],
	overrides: [
		{
			env: {
				jest: true,
				node: true,
			},
			files: [
				'**/__mocks__/**/*.{js,jsx,ts,tsx}',
				'**/test/**/*.{js,jsx,ts,tsx}',
			],
		},

		// @liferay/eslint-plugin pins @typescript-eslint/parser@4.30.0, whose
		// typescript-estree (supports TS <4.5) reads decorators from the legacy
		// node.decorators property. Against the resolved TypeScript 5.9.3 that
		// property is always undefined (decorators live on node.modifiers), so
		// the v4 parser silently drops every decorator and the decorator-only
		// imports (@autobind etc.) look unused. Point ts/tsx at the v5.62.0
		// parser this module declares (osb-faro-web/package.json), which parses
		// decorators correctly against TS5. No rule or option changes.

		{
			files: ['*.{ts,tsx}'],
			parser: require.resolve('@typescript-eslint/parser', {
				paths: [path.join(__dirname, 'modules/osb-faro-web')],
			}),
		},
	],
	parserOptions: {
		ecmaFeatures: {
			jsx: true,
			legacyDecorators: true,
		},
		ecmaVersion: 2023,
	},
	plugins: ['@liferay'],
	root: true,
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
