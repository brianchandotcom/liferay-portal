/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {
	setUriQueryValue,
	setUriQueryValues,
	toRoute,
} from '~/shared/util/router';

const setUriQueryInRoute = (path, query, routeParams) => {
	const {rangeKey} = query;

	if (rangeKey === RangeKeyTimeRanges.CustomRange) {
		return setUriQueryValues(query, toRoute(path, routeParams));
	}
	else {
		return setUriQueryValue(
			toRoute(path, routeParams),
			'rangeKey',
			rangeKey
		);
	}
};

/**
 * Get URL
 * @param {string} path
 * @param {object} router
 */
export const getUrl = function getUrl(path, {params, query}) {
	const {rangeKey} = query;
	const {assetId, channelId, groupId, id, title, touchpoint} = params;

	const routeParams = {
		assetId,
		channelId,
		groupId,
		id,
		title,
		touchpoint,
	};

	return rangeKey
		? setUriQueryInRoute(path, query, routeParams)
		: toRoute(path, routeParams);
};
