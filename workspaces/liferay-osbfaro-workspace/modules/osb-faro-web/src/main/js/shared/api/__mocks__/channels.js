/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mockChannel, mockSearch, mockUser} from '~/test/data';

export const create = jest.fn(() => Promise.resolve([mockChannel()]));

const delete$ = jest.fn(() => Promise.resolve(''));
export {delete$ as delete};

export const fetch = jest.fn(() => Promise.resolve(mockChannel()));

export const fetchAll = jest.fn(() =>
	Promise.resolve({items: [mockChannel()]})
);

export const fetchUsers = jest.fn(() => Promise.resolve(mockSearch(mockUser)));

export const search = jest.fn(() => Promise.resolve(mockSearch(mockChannel)));
