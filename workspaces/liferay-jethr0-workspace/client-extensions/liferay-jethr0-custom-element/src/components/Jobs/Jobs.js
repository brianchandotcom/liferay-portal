/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {Link} from 'react-router-dom';

import {toLocaleString} from '../../services/DateUtil';
import useSpringBootData from '../../services/useSpringBootData';
import Jethr0Table from '../Jethr0Table/Jethr0Table';

function Jobs() {
	const [jobs, setJobs] = useState(null);

	useSpringBootData({
		setData: setJobs,
		urlPath: '/jobs',
	});

	if (!jobs) {
		return <div>Loading...</div>;
	}

	return (
		<Jethr0Table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Priority</th>
					<th>Create Date</th>
					<th>Modified Date</th>
					<th>Start Date</th>
					<th>State</th>
					<th>Type</th>
				</tr>
			</thead>
			<tbody>
				{jobs &&
					jobs.map((job) => {
						return (
							<tr key={job.id}>
								<th className="font-weight-semi-bold">
									<Link title={job.id} to={'/jobs/' + job.id}>
										{job.id}
									</Link>
								</th>
								<td>{job.name}</td>
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
	);
}

export default Jobs;
