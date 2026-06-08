/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {pickBy} from 'lodash';
import {TimeIntervals} from '~/shared/util/constants';
import {buildOrderByFields} from '~/shared/util/pagination';
import sendRequest from '~/shared/util/request';

export const DEFAULT_ACTIVITY_MAX = 30;

export const DEFAULT_ACTIVITY_INTERVAL = TimeIntervals.Day;

export function fetchHistory({
	channelId = '',
	contactsEntityId,
	contactsEntityType,
	groupId,
	interval = DEFAULT_ACTIVITY_INTERVAL,
	max = DEFAULT_ACTIVITY_MAX,
	rangeEnd = null,
	rangeStart = null,
}) {
	return sendRequest({
		data: {
			channelId,
			contactsEntityId,
			contactsEntityType,
			interval,
			...pickBy({
				max,
				rangeEnd,
				rangeStart,
			}),
		},
		method: 'GET',
		path: `contacts/${groupId}/activity/history`,
	});
}

export function fetchGroup({
	channelId = '',
	contactsEntityId,
	contactsEntityType,
	cur,
	delta,
	endDate,
	groupId,
	orderIOMap,
	query,
	startDate,
}) {
	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams);

	return sendRequest({
		data: {
			channelId,
			contactsEntityId,
			contactsEntityType,
			cur,
			delta,
			endDate,
			orderByFields,
			query,
			startDate,
		},
		method: 'GET',
		path: `contacts/${groupId}/activity_group`,
	});
}

export function searchAssets({
	applicationId,
	channelId = '',
	eventId,
	groupId,
	orderIOMap,
	...otherParams
}) {
	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams);

	return sendRequest({
		data: {
			...otherParams,
			applicationId,
			channelId,
			eventId,
			orderByFields,
		},
		method: 'GET',
		path: `contacts/${groupId}/activity/asset`,
	});
}

export function searchCount({action, groupId, ...otherParams}) {
	return sendRequest({
		data: {
			...otherParams,
			action,
		},
		method: 'GET',
		path: `contacts/${groupId}/activity/count`,
	});
}
