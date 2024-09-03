/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet, Route} from 'react-router-dom';

import NewAppContextProvider from '../../../../context/NewAppContext';
import {Create} from '../Solutions/NewSolutionFlow/pages';
import PublishNewAppOutlet from './PublishNewAppOutlet';
import {NewAppBuild, NewAppProfile} from './pages';

const NewAppRouter = ({catalogId}: {catalogId: number}) => {
	return (
		<Route path="newapp">
			<Route
				element={
					<NewAppContextProvider catalogId={catalogId}>
						<Outlet />
					</NewAppContextProvider>
				}
				path=":productId?"
			>
				<Route element={<PublishNewAppOutlet />} path="publisher">
					<Route element={<Create />} path="" />

					<Route element={<NewAppProfile />} path="profile" />

					<Route element={<NewAppBuild />} path="build" />
				</Route>
			</Route>
		</Route>
	);
};

export default NewAppRouter;
