/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from "@clayui/panel";
import { useState } from "react";

import setSpringBootData from '../../services/setSpringBootData';

function JobBuilds({jobId}) {
	let [jobBuilds, setJobBuilds] = useState(null);

	setSpringBootData({
		setData: setJobBuilds,
		timeout: 2500,
		urlPath: '/jobs/builds/' + jobId
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
				<table class="table table-bordered table-hover table-sm">
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Create Date</th>
							<th>State</th>
							<th>Jenkins Build</th>
						</tr>
					</thead>
					<tbody>
						{jobBuilds && jobBuilds.map((jobBuild) => {
							return (
								<tr>
									<th class="font-weight-semi-bold" title={jobBuild.id}>{jobBuild.id}</th>
									<td>{jobBuild.name}</td>
									<td>{jobBuild.dateCreated}</td>
									<td>{jobBuild.state.name}</td>
									<td>{jobBuild.latestJenkinsBuildURL ? <a href={jobBuild.latestJenkinsBuildURL}>Latest Jenkins Build</a> : <div>-</div>}</td>
								</tr>
							);
						})}
					</tbody>
				</table>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

export default JobBuilds;