/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as data from '~/test/data';

export const fetch = jest.fn(() => Promise.resolve(data.mockIndividual()));

export const fetchDetails = jest.fn(() =>
	Promise.resolve(data.mockIndividualDetails())
);

export const fetchEnrichedProfilesCount = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividual))
);

export const fetchMembership = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividual))
);

export const fetchFieldValues = jest.fn(() =>
	Promise.resolve(data.mockSearch(String))
);

export const search = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividual))
);
