/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import Settings from '.';
import Setting from './Setting';
import SettingForm from './SettingForm';
import SettingOutlet from './SettingOutlet';

export default function SettingRouter() {
	return (
		<HashRouter>
			<Routes>
				<Route element={<Settings />} index />
				<Route element={<SettingForm />} path="create" />
				<Route element={<SettingOutlet />} path=":id">
					<Route element={<Setting />} index />
					<Route element={<SettingForm />} path="update" />
				</Route>
			</Routes>
		</HashRouter>
	);
}
