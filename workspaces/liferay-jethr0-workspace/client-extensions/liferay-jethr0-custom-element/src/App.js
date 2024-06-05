/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import BuildPage from './pages/BuildPage/BuildPage';
import CreateJenkinsCohortPage from './pages/CreateJenkinsCohortPage/CreateJenkinsCohortPage';
import CreateJenkinsServerPage from './pages/CreateJenkinsServerPage/CreateJenkinsServerPage';
import CreateJobPage from './pages/CreateJobPage/CreateJobPage';
import CreateRoutinePage from './pages/CreateRoutinePage/CreateRoutinePage';
import JenkinsCohortPage from './pages/JenkinsCohortPage/JenkinsCohortPage';
import JenkinsCohortsPage from './pages/JenkinsCohortsPage/JenkinsCohortsPage';
import JenkinsServerPage from './pages/JenkinsServerPage/JenkinsServerPage';
import JenkinsServersPage from './pages/JenkinsServersPage/JenkinsServersPage';
import JobPage from './pages/JobPage/JobPage';
import JobQueuePage from './pages/JobQueuePage/JobQueuePage';
import JobsPage from './pages/JobsPage/JobsPage';
import NotFoundPage from './pages/NotFoundPage/NotFoundPage';
import RoutinePage from './pages/RoutinePage/RoutinePage';
import RoutinesPage from './pages/RoutinesPage/RoutinesPage';
import UpdateJobPage from './pages/UpdateJobPage/UpdateJobPage';
import UpstreamBranchPage from './pages/UpstreamBranchPage/UpstreamBranchPage';
import UpstreamBranchesPage from './pages/UpstreamBranchesPage/UpstreamBranchesPage';

import './App.css';

function App() {
	return (
		<div>
			<HashRouter>
				<Routes>
					<Route element={<BuildPage />} path="/builds/:id" />
					<Route
						element={<CreateJenkinsCohortPage />}
						path="/jenkins-cohorts/create"
					/>
					<Route
						element={<CreateJenkinsServerPage />}
						path="/jenkins-servers/create"
					/>
					<Route element={<CreateJobPage />} path="/jobs/create" />
					<Route
						element={<CreateRoutinePage />}
						path="/routines/create"
					/>
					<Route
						element={<JenkinsCohortPage />}
						path="/jenkins-cohorts/:id"
					/>
					<Route
						element={<CreateJenkinsServerPage />}
						path="/jenkins-cohorts/:jenkinsCohortId/create-server"
					/>
					<Route
						element={<JenkinsCohortsPage />}
						path="/jenkins-cohorts"
					/>
					<Route
						element={<JenkinsServerPage />}
						path="/jenkins-servers/:id"
					/>
					<Route
						element={<JenkinsServersPage />}
						path="/jenkins-servers"
					/>
					<Route element={<JobPage />} path="/jobs/:id" />
					<Route element={<JobQueuePage />} path="/" />
					<Route element={<JobsPage />} path="/jobs" />
					<Route element={<NotFoundPage />} path="*" />
					<Route element={<RoutinePage />} path="/routines/:id" />
					<Route
						element={<CreateJobPage />}
						path="/routines/:routineId/create-job"
					/>
					<Route element={<RoutinesPage />} path="/routines" />
					<Route
						element={<UpdateJobPage />}
						path="/jobs/:id/update"
					/>
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
		</div>
	);
}

export default App;
