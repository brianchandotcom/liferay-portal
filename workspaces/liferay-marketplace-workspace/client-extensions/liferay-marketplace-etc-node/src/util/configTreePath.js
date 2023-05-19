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
