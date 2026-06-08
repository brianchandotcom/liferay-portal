/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as data from '~/test/data';

export const searchIndividualAttributes = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividualAttributes, 5))
);
