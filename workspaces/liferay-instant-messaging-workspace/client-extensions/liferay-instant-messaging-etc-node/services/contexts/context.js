/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const fs = require('fs');
const path = require('path');

const rootFolder = path.resolve(__dirname, '../', '../');

const get = (contextObjectDefinitionID) => {
	const folderPath = path.join(
		rootFolder,
		'contexts',
		contextObjectDefinitionID
	);

	try {
		const files = fs.readdirSync(folderPath);
		const contexts = [];

		files.forEach((file) => {
			const filePath = path.join(folderPath, file);
			const stats = fs.statSync(filePath);

			if (stats.isFile() && path.extname(file).toLowerCase() === '.txt') {
				const fileContent = fs.readFileSync(filePath, 'utf8');
				contexts.push(fileContent);
			}
		});

		return contexts;
	}
	catch (error) {
		console.error('Error reading files:', error);

		return '';
	}
};

module.exports = {
	get,
};
