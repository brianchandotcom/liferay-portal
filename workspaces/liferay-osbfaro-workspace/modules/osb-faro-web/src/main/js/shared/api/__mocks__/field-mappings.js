/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mockFieldMapping, mockSearch} from '~/test/data';

export const fetch = jest.fn(() => Promise.resolve(mockFieldMapping()));

export const fetchDefault = jest.fn(() =>
	Promise.resolve(mockSearch(mockFieldMapping))
);

export const fetchSuggestions = jest.fn(() =>
	Promise.resolve(mockSearch(mockFieldMapping))
);

export const create = jest.fn(() => Promise.resolve(mockFieldMapping()));

export const search = jest.fn(() =>
	Promise.resolve(mockSearch(mockFieldMapping))
);
