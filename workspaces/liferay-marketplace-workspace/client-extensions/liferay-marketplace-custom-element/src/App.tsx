/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';

import AppRoutes, {RouteType} from './Routes';
import {getIconSpriteMap} from './liferay/constants';

import './App.scss';

interface AppProps {
	route: string;
}

function App({route}: AppProps) {
	return (
		<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
			<AppRoutes route={route as RouteType} />
		</ClayIconSpriteContext.Provider>
	);
}

export default App;
