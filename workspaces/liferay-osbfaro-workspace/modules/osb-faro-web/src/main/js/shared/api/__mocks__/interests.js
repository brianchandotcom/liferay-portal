/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mockInterestData, mockSearch} from '~/test/data';

export const fetch = jest.fn(() => Promise.resolve(mockInterestData()));

export const search = jest.fn(() =>
	Promise.resolve(mockSearch(mockInterestData))
);

export const searchKeywords = jest.fn(() =>
	Promise.resolve(mockSearch((i, name) => `${name}-${i}`, 5, ['test']))
);
