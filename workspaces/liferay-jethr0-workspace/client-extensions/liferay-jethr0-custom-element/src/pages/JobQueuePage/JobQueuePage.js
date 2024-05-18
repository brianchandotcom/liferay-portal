/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {Link} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {getJobQueueOrderedJobsPage} from '../../objects/jobs/JobUtil';
import {toLocaleString} from '../../services/DateUtil';

function JobQueuePage() {
	const [jobsPage, setJobsPage] = useState(null);

	if (!jobsPage) {
		getJobQueueOrderedJobsPage({setJobsPage});
	}

	if (!jobsPage) {
		return <div>...</div>;
	}

	function setActiveDelta({activeDelta, jobsPage}) {
		getJobQueueOrderedJobsPage({
			page: jobsPage.page,
			pageSize: activeDelta,
			setJobsPage,
		});
	}

	function setActivePage({activePage, jobsPage}) {
		getJobQueueOrderedJobsPage({
			page: activePage,
			pageSize: jobsPage.pageSize,
			setJobsPage,
		});
	}

	const breadcrumbs = [{active: true, link: '/', name: 'Home'}];

	const deltas = [
		{
			label: 25,
		},
		{
			label: 50,
		},
		{
			label: 100,
		},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Home" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Job Queue
				</Heading>

				<Jethr0Table>
					<thead>
						<tr>
							<th>Position</th>
							<th>Blessed</th>
							<th>ID</th>
							<th>Name</th>
							<th>Priority</th>
							<th>Create Date</th>
							<th>Start Date</th>
							<th>State</th>
							<th className="table-cell-expanded">
								<span className="text-muted">Opened</span>
								<span> / </span>
								<span className="text-warning">Running</span>
								<span> / </span>
								<span className="text-success">Completed</span>
								<span> / </span>
								<span>Total Builds</span>
							</th>
						</tr>
					</thead>
					<tbody>
						{jobsPage?.jobs?.map((job, index) => {
							let completedBuilds = 0;
							let openedBuilds = 0;
							let runningBuilds = 0;
							let totalBuilds = 0;

							for (const build of job.builds) {
								if (build.state.key === 'completed') {
									completedBuilds++;
								}
								else if (build.state.key === 'opened') {
									openedBuilds++;
								}
								else if (build.state.key === 'running') {
									runningBuilds++;
								}

								totalBuilds++;
							}

							return (
								<tr key={job.id}>
									<td>{index + 1}</td>
									<td>
										{job.blessed && (
											<ClayBadge
												displayType="success"
												label="blessed"
											/>
										)}
									</td>
									<th className="font-weight-semi-bold">
										<Link
											title={job.id}
											to={'/jobs/' + job.id}
										>
											{job.id}
										</Link>
									</th>
									<td>{job.name}</td>
									<td>{job.priority}</td>
									<td>{toLocaleString(job.dateCreated)}</td>
									<td>{toLocaleString(job.startDate)}</td>
									<td>{job.state.name}</td>
									<td>
										<span className="text-muted">
											{openedBuilds}
										</span>
										<span> / </span>
										<span className="text-warning">
											{runningBuilds}
										</span>
										<span> / </span>
										<span className="text-success">
											{completedBuilds}
										</span>
										<span> / </span>
										<span>{totalBuilds}</span>
									</td>
								</tr>
							);
						})}
					</tbody>
				</Jethr0Table>
			</Jethr0Card>

			{jobsPage && (
				<ClayPaginationBarWithBasicItems
					activeDelta={jobsPage.pageSize}
					defaultActive={jobsPage.page}
					deltas={deltas}
					ellipsisBuffer={3}
					onActiveChange={(activePage) => {
						setActivePage({activePage, jobsPage});
					}}
					onDeltaChange={(activeDelta) => {
						setActiveDelta({activeDelta, jobsPage});
					}}
					showDeltasDropDown={true}
					totalItems={jobsPage.totalCount}
				/>
			)}
		</ClayLayout.Container>
	);
}

export default JobQueuePage;
