/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as data from '~/test/data';

export const fetchGroup = jest.fn(() =>
	Promise.resolve({
		items: [data.mockActivity(2)],
		total: 1,
	})
);

export const fetchHistory = jest.fn(() =>
	Promise.resolve(data.mockActivityHistory())
);

export const searchCount = jest.fn(() => Promise.resolve(0));
