/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {useState} from 'react';

import {toLocaleString} from '../../services/DateUtil';
import useSpringBootData from '../../services/useSpringBootData';
import Jethr0Table from '../Jethr0Table/Jethr0Table';

function JobBuilds({jobId}) {
	const [jobBuilds, setJobBuilds] = useState(null);

	useSpringBootData({
		setData: setJobBuilds,
		timeout: 2500,
		urlPath: '/jobs/builds/' + jobId,
	});

	if (!jobBuilds) {
		return <div>Loading...</div>;
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Builds"
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<Jethr0Table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Create Date</th>
							<th>State</th>
							<th>Initial Build</th>
							<th>Jenkins Build</th>
						</tr>
					</thead>
					<tbody>
						{jobBuilds &&
							jobBuilds.map((jobBuild) => {
								return (
									<tr key={jobBuild.id}>
										<th
											className="font-weight-semi-bold"
											title={jobBuild.id}
										>
											{jobBuild.id}
										</th>
										<td>{jobBuild.name}</td>
										<td>
											{toLocaleString(
												jobBuild.dateCreated
											)}
										</td>
										<td>{jobBuild.state.name}</td>
										<td>
											{jobBuild.initialBuild.toString()}
										</td>
										<td>
											{jobBuild.latestJenkinsBuildURL ? (
												<a
													href={
														jobBuild.latestJenkinsBuildURL
													}
												>
													Latest Jenkins Build
												</a>
											) : (
												<div>-</div>
											)}
										</td>
									</tr>
								);
							})}
					</tbody>
				</Jethr0Table>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

export default JobBuilds;
