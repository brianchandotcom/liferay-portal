/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {StoreEnhancer, compose, createStore} from 'redux';

import reducers from '../reducers';
import middleware from './configure-middleware';

export default function configureStore(initialState: any) {
	return createStore(
		reducers,
		initialState,
		compose(
			middleware,
			window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ // eslint-disable-line no-underscore-dangle
				? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__() // eslint-disable-line no-underscore-dangle
				: (f: StoreEnhancer<any>) => f
		)
	);
}
