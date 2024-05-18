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
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0ContainerFluid from '../../components/Jethr0ContainerFluid/Jethr0ContainerFluid';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {getJobsPage} from '../../objects/jobs/JobUtil';
import {toLocaleString} from '../../services/DateUtil';

function JobsPage() {
	const [jobsPage, setJobsPage] = useState(null);

	if (!jobsPage) {
		getJobsPage({setJobsPage});
	}

	if (!jobsPage) {
		return <div>Loading...</div>;
	}

	function setActiveDelta({activeDelta, jobsPage}) {
		getJobsPage({page: jobsPage.page, pageSize: activeDelta, setJobsPage});
	}

	function setActivePage({activePage, jobsPage}) {
		getJobsPage({
			page: activePage,
			pageSize: jobsPage.pageSize,
			setJobsPage,
		});
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: true, link: '/jobs', name: 'Jobs'},
	];

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
				<Jethr0NavigationBar active="Jobs" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							Jobs
						</Heading>
						<Jethr0ButtonsRow
							buttons={[
								{link: '/jobs/create', title: 'Create Job'},
							]}
						/>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<Jethr0Table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Blessed</th>
							<th>Priority</th>
							<th>Create Date</th>
							<th>Modified Date</th>
							<th>Start Date</th>
							<th>State</th>
							<th>Type</th>
						</tr>
					</thead>
					<tbody>
						{jobsPage?.jobs.map((job) => {
							return (
								<tr key={job.id}>
									<th className="font-weight-semi-bold">
										<Link
											title={job.id}
											to={'/jobs/' + job.id}
										>
											{job.id}
										</Link>
									</th>
									<td>{job.name}</td>
									<td>
										{job.blessed && (
											<ClayBadge
												displayType="success"
												label="blessed"
											/>
										)}
									</td>
									<td>{job.priority}</td>
									<td>{toLocaleString(job.dateCreated)}</td>
									<td>{toLocaleString(job.dateModified)}</td>
									<td>{toLocaleString(job.startDate)}</td>
									<td>{job.state.name}</td>
									<td>{job.type.name}</td>
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

export default JobsPage;
