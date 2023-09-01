/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import HomePage from './pages/HomePage/HomePage';
import JobPage from './pages/JobPage/JobPage';
import NotFoundPage from './pages/NotFoundPage/NotFoundPage';

import {HashRouter, Route, Routes} from 'react-router-dom';

import './App.css';

function App() {
	return (
		<HashRouter>
			<Routes>
				<Route path="/" element={<HomePage />} />
				<Route path="/jobs">
					<Route path=":id" element={<JobPage />} />
				</Route>
				<Route path="*" element={<NotFoundPage />} />
			</Routes>
		</HashRouter>
	);
};

export default App;