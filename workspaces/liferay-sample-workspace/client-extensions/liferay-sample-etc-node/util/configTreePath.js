/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs';
import path from 'path';

import config from '../config.js';

async function* walk(dir) {
	if (fs.existsSync(dir) === false) {
		return;
	}

	const dirents = await fs.promises.opendir(dir, {
		withFileTypes: true,
	});

	for await (const dirent of dirents) {
		if (dirent.name.startsWith('..')) {
			continue;
		}

		const entryPath = path.join(dir, dirent.name);

		if (dirent.isDirectory()) {
			yield* walk(entryPath);
		}
		else {
			yield entryPath;
		}
	}
}

const configTreeMap = async () => {
	for (const configTreePath of config.configTreePaths) {
		for await (const configFile of walk(configTreePath)) {
			const configFileName = configFile.substring(
				configFile.lastIndexOf('/') + 1
			);

			config[configFileName] = fs.readFileSync(configFile, 'utf-8');
		}
	}

	return config;
};

export default await configTreeMap();
