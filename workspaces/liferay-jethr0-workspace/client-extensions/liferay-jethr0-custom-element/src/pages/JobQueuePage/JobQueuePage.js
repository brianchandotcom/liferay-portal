/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {Link} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {toLocaleString} from '../../services/DateUtil';
import useSpringBootData from '../../services/useSpringBootData';

function JobQueue() {
	const [jobQueue, setJobQueue] = useState(null);

	useSpringBootData({
		setData: setJobQueue,
		urlPath: '/jobs/queue',
	});

	if (!jobQueue) {
		return <div>Loading...</div>;
	}

	return (
		<Jethr0Table>
			<thead>
				<tr>
					<th>Position</th>
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
				{jobQueue &&
					jobQueue.map((job) => {
						return (
							<tr key={job.id}>
								<td>{job.position}</td>
								<th className="font-weight-semi-bold">
									<Link title={job.id} to={'/jobs/' + job.id}>
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
										{job.queuedBuilds}
									</span>
									<span> / </span>
									<span className="text-warning">
										{job.runningBuilds}
									</span>
									<span> / </span>
									<span className="text-success">
										{job.completedBuilds}
									</span>
									<span> / </span>
									<span>{job.totalBuilds}</span>
								</td>
							</tr>
						);
					})}
			</tbody>
		</Jethr0Table>
	);
}

function JobQueuePage() {
	const breadcrumbs = [{active: true, link: '/', name: 'Home'}];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Home" />
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">
					Job Queue
				</Heading>
				<JobQueue />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JobQueuePage;
