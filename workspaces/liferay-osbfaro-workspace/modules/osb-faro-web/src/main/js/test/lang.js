/**
 * Returns the values of our language keys for any calls to
 * `Liferay.Language.get`. The keys are parsed once per run by
 * `global-setup.js`, which merges ours over the DXP ones.
 */

import fs from 'fs';
import LanguageIgnoreList from './language-ignore-list';

let keys;

/**
 * Returns the value for a given language key. Throws an error if
 * no value is found for the key.
 * @param {string} key - The language key
 * @returns {string} The language key's value
 */
export default function lang(key) {
	keys ??= JSON.parse(
		fs.readFileSync(process.env.FARO_TEST_LANG_KEYS_FILE, 'utf8')
	);

	const value = keys[key];

	if (!value) {
		if (LanguageIgnoreList.includes(key)) {
			return key;
		}

		throw new Error(
			[
				`Language key not found: "${key}".`,
				"Tests only load the keys that src/main/js/test/global-setup.js finds in literal `Liferay.Language.get('...')` calls under src/main/js.",
				'If the key exists in a Language.properties file, it is probably built dynamically or passed through a variable or wrapper; call `Liferay.Language.get` with the literal key instead.',
				'If it does not exist, add it to src/main/resources/content/Language.properties, or to the portal one at modules/apps/portal-language/portal-language-lang/src/main/resources/content/Language.properties.',
			].join('\n')
		);
	}

	return value;
}
