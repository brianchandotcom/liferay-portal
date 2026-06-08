/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';
import Constants, {TimeIntervals} from '~/shared/util/constants';
import {
	TITLE,
	buildOrderByFields,
	createOrderIOMap,
} from '~/shared/util/pagination';
import sendRequest from '~/shared/util/request';
import {PAGES} from '~/shared/util/router';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA},
} = Constants;

export const INTERVALS_MAP = {
	[TimeIntervals.Day]: 1,
	[TimeIntervals.Month]: 30,
	[TimeIntervals.Week]: 7,
};

export function search(params) {
	const {
		active = true,
		channelId,
		contactsEntityId,
		contactsEntityType,
		delta = DEFAULT_DELTA,
		groupId,
		interestName,
		interval,
		intervalInitDate,
		orderIOMap = createOrderIOMap(TITLE),
		page = DEFAULT_PAGE,
		query = '',
	} = params;

	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams, PAGES);

	return sendRequest({
		data: {
			active,
			channelId,
			contactsEntityId,
			contactsEntityType,
			cur: page,
			delta,
			endDate: moment(intervalInitDate)
				.add(INTERVALS_MAP[interval] - 1, 'days')
				.format('x'),
			interestName,
			orderByFields,
			query,
			startDate: intervalInitDate,
		},
		method: 'GET',
		path: `contacts/${groupId}/pages_visited`,
	});
}
