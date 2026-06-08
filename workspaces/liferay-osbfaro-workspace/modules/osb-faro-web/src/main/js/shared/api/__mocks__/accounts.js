/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as data from '~/test/data';

export const fetch = jest.fn(() => Promise.resolve(data.mockAccount()));

export const fetchDetails = jest.fn(() =>
	Promise.resolve(data.mockAccountDetails())
);

export const search = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockAccount))
);
