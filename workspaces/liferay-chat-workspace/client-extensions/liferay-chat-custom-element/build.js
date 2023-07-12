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

const concat = require('concat');
const fs = require('fs-extra');
const path = require('path');
const buildFolder = './dist/liferay-chat';
const componentBuildFolder = 'componentLibrary';
const componentBuiltFile = 'components';
function fromDir(startPath, filter) {
	const _files = [];

	if (!fs.existsSync(startPath)) {
		// eslint-disable-next-line no-console
		console.log('Wrong Folder Path!', startPath);

		return;
	}
	const files = fs.readdirSync(startPath);
	for (let i = 0; i < files.length; i++) {
		const filename = path.join(startPath, files[i]);
		if (filename.endsWith(filter)) {
			_files.push(filename);
			// eslint-disable-next-line no-console
			console.log('-- found: ', filename);
		}
	}

	return _files;
}

(async function build() {
	const js_files = fromDir(buildFolder, '.js');

	const css_files = fromDir(buildFolder, '.css');

	await fs.ensureDir(componentBuildFolder);

	await fs.removeSync(`${componentBuildFolder}/${componentBuiltFile}.js`);
	await fs.removeSync(`${componentBuildFolder}/${componentBuiltFile}.css`);

	await concat(js_files, `${componentBuildFolder}/${componentBuiltFile}.js`);
	await concat(
		css_files,
		`${componentBuildFolder}/${componentBuiltFile}.css`
	);
})();
