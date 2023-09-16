/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import AddJobPage from './pages/AddJobPage/AddJobPage';
import HomePage from './pages/HomePage/HomePage';
import JobPage from './pages/JobPage/JobPage';
import JobsPage from './pages/JobsPage/JobsPage';
import NotFoundPage from './pages/NotFoundPage/NotFoundPage';

import './App.css';

function App() {
	return (
		<HashRouter>
			<Routes>
				<Route element={<AddJobPage />} path="/jobs/add" />
				<Route element={<HomePage />} path="/" />
				<Route element={<JobPage />} path="/jobs/:id" />
				<Route element={<JobsPage />} path="/jobs" />
				<Route element={<NotFoundPage />} path="*" />
			</Routes>
		</HashRouter>
	);
}

export default App;
