/**
 * Parses the language files once per run instead of once per test file.
 * Jest resets the module registry between files, so a cache inside lang.js
 * would not survive; workers read the merged keys from this file instead.
 */

const fs = require('fs');
const os = require('os');
const path = require('path');
const properties = require('properties');

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

const parse = (langPath) => properties.parse(fs.readFileSync(langPath, 'utf8'));

const SRC_DIR = path.resolve('src', 'main', 'js');

/**
 * Returns the keys passed to `Liferay.Language.get` anywhere in our source
 * and tests, so each test file parses a few thousand keys instead of all of
 * the DXP ones.
 */
const getUsedKeys = () => {
	const usedKeys = new Set();

	const files = fs.readdirSync(SRC_DIR, {recursive: true});

	for (const file of files) {
		if (!/\.(js|jsx|ts|tsx)$/.test(file)) {
			continue;
		}

		const source = fs.readFileSync(path.join(SRC_DIR, file), 'utf8');

		for (const [, key] of source.matchAll(
			/Language\.get\(\s*['"]([^'"]+)['"]/g
		)) {
			usedKeys.add(key);
		}
	}

	return usedKeys;
};

module.exports = () => {
	const allKeys = parse(DXP_LANG_PATH);

	for (const [key, value] of Object.entries(parse(AC_LANG_PATH))) {
		if (value) {
			allKeys[key] = value;
		}
	}

	const keys = {};

	for (const key of getUsedKeys()) {
		if (allKeys[key]) {
			keys[key] = allKeys[key];
		}
	}

	const keysFile = path.join(
		fs.mkdtempSync(path.join(os.tmpdir(), 'faro-test-lang-')),
		'keys.json'
	);

	fs.writeFileSync(keysFile, JSON.stringify(keys));

	process.env.FARO_TEST_LANG_KEYS_FILE = keysFile;
};
