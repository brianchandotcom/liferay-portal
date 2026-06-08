/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {v4 as uuidv4} from 'uuid';

const getRndInteger = (min, max) =>
	Math.floor(Math.random() * (max - min + 1)) + min;

const metrics = {
	downloadsMetric: {
		__typename: 'AssetMetric',
		name: 'downloadsMetric',
	},
	impressionMadeMetric: {
		__typename: 'AssetMetric',
		name: 'impressionMadeMetric',
	},
	submissionsMetric: {
		__typename: 'AssetMetric',
		name: 'submissionsMetric',
	},
	viewsMetric: {
		__typename: 'AssetMetric',
		name: 'viewsMetric',
	},
};

function generateItems({selectedMetrics, size}) {
	const array = new Array(size);

	for (let i = 0; i < array.length; i++) {
		const assetTitle = uuidv4();
		const assetId = `http://liferay.com/web/test/abc/123/${assetTitle}`;

		array[i] = {
			__typename: 'BlogMetric',
			assetId,
			assetTitle,
			selectedMetrics: selectedMetrics.map((selectedMetric) => ({
				...metrics[selectedMetric],
				value: getRndInteger(0, 1000000),
			})),
		};
	}

	return array;
}

const AssetAppearsOnResolver = function AssetAppearsOnResolver(_, variables) {
	return {
		__typename: 'AssetPages',
		assetMetrics: generateItems(variables),
		total: 1000,
	};
};

export default AssetAppearsOnResolver;
