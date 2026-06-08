/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	mockMembershipChange,
	mockMembershipChangeAggregation,
	mockSearch,
	mockSegment,
} from '~/test/data';

export const addIndividuals = jest.fn(() => Promise.resolve(mockSegment()));

const delete$ = jest.fn(() => Promise.resolve(''));

export {delete$ as delete};

export const fetch = jest.fn(() => Promise.resolve(mockSegment()));

export const create = jest.fn(() => Promise.resolve(mockSegment()));

export const update = jest.fn(() => Promise.resolve(mockSegment()));

export const updateChannel = jest.fn(() => Promise.resolve(mockSegment()));

export const addMemberships = jest.fn(() => Promise.resolve(mockSegment()));

export const removeMemberships = jest.fn(() => Promise.resolve(mockSegment()));

export const fetchMembershipChanges = jest.fn(() =>
	Promise.resolve(mockSearch(mockMembershipChange))
);

export const fetchMembershipChangesAggregations = jest.fn(() =>
	Promise.resolve([mockMembershipChangeAggregation()])
);

export const search = jest.fn(() => Promise.resolve(mockSearch(mockSegment)));

export const searchUnassigned = jest.fn(() =>
	Promise.resolve(mockSearch(mockSegment))
);
