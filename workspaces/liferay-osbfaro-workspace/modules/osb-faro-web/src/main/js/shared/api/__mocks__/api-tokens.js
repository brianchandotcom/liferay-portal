/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as data from '~/test/data';

export const generate = jest.fn(() => Promise.resolve(data.mockApiToken()));

export const search = jest.fn(() => Promise.resolve([data.mockApiToken()]));
