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

import fs from 'fs';
import path from 'path';

import config from '../config';

async function* walk(dir) {
	if (fs.existsSync(dir) === false) {
		return;
	}
	for await (const dirent of await fs.promises.opendir(dir, {
		withFileTypes: true,
	})) {
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
	for await (const configFile of walk(config.configTreePath)) {
		const configFileName = configFile.substring(
			configFile.lastIndexOf('/') + 1
		);
		config[configFileName] = fs.readFileSync(configFile, 'utf-8');
	}

	return config;
};

export default await configTreeMap();
