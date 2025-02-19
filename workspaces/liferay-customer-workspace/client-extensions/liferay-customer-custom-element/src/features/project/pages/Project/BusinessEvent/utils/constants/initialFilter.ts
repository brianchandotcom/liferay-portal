/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFilterOption} from '~/components/Filter/Filter';

export const INITIAL_FILTER: IFilterOption[] = [
	{
		name: 'Event Status',
		value: ['Open', 'Cancelled', 'Completed', 'Overdue'],
	},
	{
		name: 'Event Type',
		value: ['Golive', 'Upgrade', 'Migration', 'OtherEvent'],
	},
];
