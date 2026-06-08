/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {escapeSingleQuotes} from '~/segment/segment-editor/dynamic/utils/odata';
import FaroConstants from '~/shared/util/constants';
import {buildOrderByFields} from '~/shared/util/pagination';
import sendRequest from '~/shared/util/request';
import {ACCOUNTS} from '~/shared/util/router';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA},
} = FaroConstants;

export function fetch({accountId, channelId, groupId}) {
	return sendRequest({
		data: {channelId},
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}`,
	});
}

export function fetchDetails({accountId, channelId, groupId}) {
	return sendRequest({
		data: {channelId},
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}/details`,
	});
}

export function fetchFieldValues({
	channelId,
	fieldMappingFieldName,
	groupId,
	query,
}) {
	return sendRequest({
		data: {
			channelId,
			delta: DEFAULT_DELTA,
			fieldMappingFieldName,
			query: escapeSingleQuotes(query),
		},
		method: 'GET',
		path: `contacts/${groupId}/account/field_values`,
	});
}

export async function fetchLifecycleStageFieldValues({
	accountLifecycleId,
	channelId,
	groupId,
}) {
	return sendRequest({
		data: {
			accountLifecycleId,
			channelId,
			fieldMappingFieldName: 'lifecycleStatus',
		},
		method: 'GET',
		path: `contacts/${groupId}/account/fds_field_values`,
	});
}

export async function fetchLifecycleStatus({
	accountId,
	accountLifecycleId,
	groupId,
}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}/account-lifecycles/${accountLifecycleId}`,
	});
}

export function fetchMetrics({channelId, groupId}) {
	return sendRequest({
		data: {channelId},
		method: 'GET',
		path: `contacts/${groupId}/account/metrics`,
	});
}

export function search({
	channelId = '',
	delta = DEFAULT_DELTA,
	groupId,
	orderIOMap,
	page = DEFAULT_PAGE,
	query = '',
	...otherParams
}) {
	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams, ACCOUNTS);

	return sendRequest({
		data: {
			channelId,
			cur: page,
			delta,
			orderByFields,
			query,
			...otherParams,
		},
		method: 'POST',
		path: `contacts/${groupId}/account/search`,
	});
}
