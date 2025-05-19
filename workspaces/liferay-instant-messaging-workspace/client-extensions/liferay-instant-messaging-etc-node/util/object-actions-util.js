/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getSystemObjectEntryModelData = (entry) => {
	const matchingProperties = Object.keys(entry)
		.filter((key) => key.startsWith('model') && !key.startsWith('modelDTO'))
		.reduce((result, key) => {
			result[key] = entry[key];

			return result;
		}, {});

	if (matchingProperties && !!Object.keys(matchingProperties).length) {
		return entry[Object.keys(matchingProperties)[0]];
	}
};

const getFilteredObject = (objectEntry, configurationFields) => {
	const filteredObject = Object.keys(objectEntry)
		.filter((key) => configurationFields.includes(key))
		.reduce((acc, key) => {
			acc[key] = objectEntry[key];

			return acc;
		}, {});

	return filteredObject;
};

module.exports = {
	getFilteredObject,
	getSystemObjectEntryModelData,
};
