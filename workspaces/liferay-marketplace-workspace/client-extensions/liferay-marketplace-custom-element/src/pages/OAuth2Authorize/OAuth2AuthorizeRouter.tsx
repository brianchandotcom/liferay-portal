/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import withProviders from '../../hoc/withProviders';
import OAuth2AuthorizeOutlet from './OAuth2AuthorizeOutlet';
import AccountSelection from './pages/AccountSelection';
import Congratulations from './pages/Congratulations';
import EnvironmentSelectionStep from './pages/EnvironmentSelection';
import ProjectSelectionStep from './pages/ProjectSelection';

const OAuth2AuthorizeRouter = () => (
	<HashRouter>
		<Routes>
			<Route element={<OAuth2AuthorizeOutlet />} path="/">
				<Route element={<AccountSelection />} index />

				<Route element={<Congratulations />} path="congratulations" />

				<Route
					element={<EnvironmentSelectionStep />}
					path="environment-selection"
				/>

				<Route
					element={<ProjectSelectionStep />}
					path="project-selection"
				/>
			</Route>
		</Routes>
	</HashRouter>
);

export default withProviders(OAuth2AuthorizeRouter);
