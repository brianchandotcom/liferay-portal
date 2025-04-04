/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFilterOption} from '~/components/Filter/Filter';

export const INITIAL_FILTER: IFilterOption[] = [
	{
		key: 'eventStatus',
		name: 'event-status',
		values: [
			{
				key: 'open',
				name: 'Open',
			},
			{
				key: 'canceled',
				name: 'Canceled',
			},
			{
				key: 'completed',
				name: 'Completed',
			},
			{
				key: 'overdue',
				name: 'Overdue',
			},
		],
	},
	{
		key: 'eventType',
		name: 'event-type',
		values: [
			{
				key: 'goLive',
				name: 'Go Live',
			},
			{
				key: 'upgrade',
				name: 'Upgrade',
			},
			{
				key: 'migration',
				name: 'Migration',
			},
			{
				key: 'otherEvent',
				name: 'Other Event',
			},
		],
	},
];
