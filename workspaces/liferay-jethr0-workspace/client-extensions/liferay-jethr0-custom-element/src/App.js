/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import CreateJobPage from './pages/CreateJobPage/CreateJobPage';
import JobBuildPage from './pages/JobBuildPage/JobBuildPage';
import JobPage from './pages/JobPage/JobPage';
import JobQueuePage from './pages/JobQueuePage/JobQueuePage';
import JobsPage from './pages/JobsPage/JobsPage';
import NotFoundPage from './pages/NotFoundPage/NotFoundPage';
import UpstreamBranchPage from './pages/UpstreamBranchPage/UpstreamBranchPage';
import UpstreamBranchesPage from './pages/UpstreamBranchesPage/UpstreamBranchesPage';

import './App.css';

function App() {
	return (
		<HashRouter>
			<Routes>
				<Route element={<CreateJobPage />} path="/jobs/create" />
				<Route element={<JobBuildPage />} path="/jobs/builds/:id" />
				<Route element={<JobPage />} path="/jobs/:id" />
				<Route element={<JobQueuePage />} path="/" />
				<Route element={<JobsPage />} path="/jobs" />
				<Route element={<NotFoundPage />} path="*" />
				<Route
					element={<UpstreamBranchesPage />}
					path="/upstream-branches"
				/>
				<Route
					element={<UpstreamBranchPage />}
					path="/upstream-branches/:id"
				/>
			</Routes>
		</HashRouter>
	);
}

export default App;
