/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {useState} from 'react';

import {toLocaleString} from '../../services/DateUtil';
import {toDurationString} from '../../services/DurationUtil';
import useSpringBootData from '../../services/useSpringBootData';
import Jethr0Table from '../Jethr0Table/Jethr0Table';

function BuildRuns({buildId}) {
	const [buildRuns, setBuildRuns] = useState(null);

	useSpringBootData({
		setData: setBuildRuns,
		timeout: 2500,
		urlPath: '/builds/runs/' + buildId,
	});

	if (!buildRuns) {
		return <div>Loading...</div>;
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Build Runs"
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<Jethr0Table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Create Date</th>
							<th>State</th>
							<th>Result</th>
							<th>Duration</th>
							<th>Jenkins Build</th>
						</tr>
					</thead>
					<tbody>
						{buildRuns &&
							buildRuns.map((buildRun) => {
								return (
									<tr key={buildRun.id}>
										<th
											className="font-weight-semi-bold"
											title={buildRun.id}
										>
											{buildRun.id}
										</th>
										<td>
											{toLocaleString(
												buildRun.dateCreated
											)}
										</td>
										<td>{buildRun.state.name}</td>
										<td>
											{buildRun.result ? buildRun.result.name : '-'}
										</td>
										<td>
											{toDurationString(buildRun.duration)}
										</td>
										<td>
											{buildRun.jenkinsBuildURL ? (
												<a
													href={
														buildRun.jenkinsBuildURL
													}
												>
													Jenkins Build
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

export default BuildRuns;
