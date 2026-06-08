/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {escapeSingleQuotes} from '~/segment/segment-editor/dynamic/utils/odata';
import {RESTParams} from '~/shared/types';
import FaroConstants from '~/shared/util/constants';
import sendRequest from '~/shared/util/request';

const {cur: defaultCur, delta: defaultDelta} = FaroConstants.pagination;

interface IFetchFieldValues extends RESTParams {
	channelId?: string;
	fieldName?: string;
	filter?: string;
}

export const fetchFieldValues = function fetchFieldValues({
	channelId = '',
	delta = defaultDelta,
	fieldName,
	filter,
	groupId,
	page = defaultCur,
	query,
}: IFetchFieldValues): Promise<{
	disableSearch: boolean;
	items: string[];
	total: number;
}> {
	return sendRequest({
		data: {
			channelId,
			cur: page,
			delta,
			fieldName,
			filter,
			query: query ? escapeSingleQuotes(query) : query,
		},
		method: 'GET',
		path: `contacts/${groupId}/session/values`,
	});
};
