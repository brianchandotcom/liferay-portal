/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs';
import path from 'path';
import properties from 'properties';

import LanguageIgnoreList from './language-ignore-list';

const AC_LANG_PATH = path.resolve(
	'src',
	'main',
	'resources',
	'content',
	'Language.properties'
);

const DXP_LANG_PATH = path.resolve(
	'../../../../',
	'modules',
	'apps',
	'portal-language',
	'portal-language-lang',
	'src',
	'main',
	'resources',
	'content',
	'Language.properties'
);

function getKeys(langPath) {
	let keys = {};

	try {
		const buffer = fs.readFileSync(langPath);

		keys = properties.parse(buffer.toString('utf8'));
	}
	catch (error) {

		// eslint-disable-next-line no-console
		console.error(`Failed to read lang key file: ${langPath}`);
	}

	return keys;
}

/**
 * Returns the value for a given language key. Throws an error if
 * no value is found for the key.
 * @param {string} key - The language key
 * @returns {string} The language key's value
 */
export default function lang(key) {
	const value = getKeys(AC_LANG_PATH)[key] || getKeys(DXP_LANG_PATH)[key];

	if (!value) {
		if (LanguageIgnoreList.includes(key)) {
			return key;
		}

		throw new Error(`Language key not found: ${key}`);
	}

	return value;
}
