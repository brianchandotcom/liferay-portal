/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mockSearch, mockUser} from '~/test/data';

const delete$ = jest.fn(() => Promise.resolve(''));

export {delete$ as delete};

export const fetchCurrentUser = jest.fn(() => Promise.resolve(mockUser('23')));

export const fetchCount = jest.fn(() => Promise.resolve(mockSearch(mockUser)));

export const inviteMany = jest.fn(() => Promise.resolve([mockUser()]));

export const fetchMany = jest.fn(() => Promise.resolve(mockSearch(mockUser)));

export const updateMany = jest.fn(() => Promise.resolve([mockUser()]));
