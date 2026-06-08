/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEVELOPER_MODE} from '~/shared/util/constants';

import {loadState} from './local-storage';
let {default: configureStore} = require('./store-prod');

if (DEVELOPER_MODE) {
	configureStore = require('./store-dev').default;
}

const store = configureStore(loadState());

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
