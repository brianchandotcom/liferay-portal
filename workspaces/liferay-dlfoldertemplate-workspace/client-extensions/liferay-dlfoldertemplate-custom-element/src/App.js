/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider} from '@clayui/modal';
import React from 'react';

import TemplateList from './components/template-list/template-list';
import {ApplicationUtil} from './utils/appUtil';

const spritemap = ApplicationUtil.getDefaultSpriteMap();

function App() {
	return (
		<>
			<ClayModalProvider spritemap={spritemap}>
				<TemplateList></TemplateList>
			</ClayModalProvider>
		</>
	);
}

export default App;
