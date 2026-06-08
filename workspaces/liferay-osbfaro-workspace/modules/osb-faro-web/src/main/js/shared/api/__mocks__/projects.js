/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {range} from 'lodash';
import {TimeZone} from '~/shared/util/records';
import * as data from '~/test/data';

export const create = jest.fn(() => Promise.resolve(data.mockProject()));

export const fetch = jest.fn(() => Promise.resolve(data.mockProject()));

export const fetchAvailableTimeZones = jest.fn(() =>
	Promise.resolve([
		new TimeZone({
			country: 'UTC',
			displayTimeZone: '(UTC) UTC',
			timezoneValue: 'UTC',
		}),

		new TimeZone({
			country: 'Brazil',
			displayTimeZone: 'UTC -03:00 Brasilia Time (America/Recife)',
			timezoneValue: 'America/Recife',
		}),

		new TimeZone({
			country: 'Chile',
			displayTimeZone: 'UTC -03:00 Chile Time (America/Santiago)',
			timezoneValue: 'America/Santiago',
		}),
	])
);

export const fetchProjectViaCorpProjectUuid = jest.fn(() =>
	Promise.resolve(data.mockProject(0))
);

export const fetchMany = jest.fn(() =>
	Promise.resolve(range(3).map((i) => data.mockProject(i)))
);

export const search = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockProject))
);

export const fetchFeatureUsages = jest.fn(() => Promise.resolve({}));
