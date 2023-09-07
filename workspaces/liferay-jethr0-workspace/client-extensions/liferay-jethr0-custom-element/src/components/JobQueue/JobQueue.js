/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Link} from 'react-router-dom';
import {useState} from 'react';

import setSpringBootData from '../../services/setSpringBootData';

function JobQueue() {
	let [jobQueue, setJobQueue] = useState(null);

	setSpringBootData({
		setData: setJobQueue,
		timeout: 1000,
		urlPath: '/jobs/queue'
	});

	if (!jobQueue) {
		return (
			<div>Loading...</div>
		);
	}

	return (<table class="table table-bordered table-hover table-sm">
		<thead>
			<tr>
				<th>Position</th>
				<th>ID</th>
				<th>Name</th>
				<th>Priority</th>
				<th>Create Date</th>
				<th>Start Date</th>
				<th>State</th>
				<th class="table-cell-expanded">
					<span class="text-muted">Opened</span>
					<span> / </span>
					<span class="text-warning">Running</span>
					<span> / </span>
					<span class="text-success">Completed</span>
					<span> / </span>
					<span>Total Builds</span>
				</th>
			</tr>
		</thead>
		<tbody>
			{jobQueue && jobQueue.map((job) => {
				let jobStartDate = "";

				if (job.startDate !== undefined) {
					jobStartDate = job.startDate;
				}

				return (
					<tr>
						<td>{job.position}</td>
						<th class="font-weight-semi-bold">
							<Link
								title={job.id}
								to={'/jobs/' + job.id}
							>
								{job.id}
							</Link>
						</th>
						<td>{job.name}</td>
						<td>{job.priority}</td>
						<td>{job.dateCreated}</td>
						<td>{jobStartDate}</td>
						<td>{job.state.name}</td>
						<td>
							<span className="text-muted">{job.queuedBuilds}</span>
							<span> / </span>
							<span className="text-warning">{job.runningBuilds}</span>
							<span> / </span>
							<span className="text-success">{job.completedBuilds}</span>
							<span> / </span>
							<span>{job.totalBuilds}</span>
						</td>
					</tr>
				);
			})}
		</tbody>
	</table>);
}

export default JobQueue;
