/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {useState} from 'react';
import {useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {getBuildRunsByBuildId} from '../../objects/buildruns/BuildRunUtil';
import {getBuildById} from '../../objects/builds/BuildUtil';
import {toLocaleString} from '../../services/DateUtil';
import {toDurationString} from '../../services/DurationUtil';

function BuildInformation({build}) {
	if (!build) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Build Information"
				displayType="secondary"
			>
				<ClayPanel.Body>Loading...</ClayPanel.Body>
			</ClayPanel>
		);
	}

	const parameters = build.parameters;

	return (
		<>
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Build Information"
				displayType="secondary"
			>
				<ClayPanel.Body>
					Build Name: {build.name}
					<br />
					Build ID: {build.id}
					<br />
					Build State: {build.state.name}
					<br />
					Create Date: {toLocaleString(build.dateCreated)}
					<br />
					Modified Date: {toLocaleString(build.dateModified)}
					<br />
					Jenkins Job Name: {build.jenkinsJobName}
				</ClayPanel.Body>
			</ClayPanel>

			{parameters && (
				<ClayPanel
					collapsable
					defaultExpanded
					displayTitle="Build Parameters"
					displayType="secondary"
					showCollapseIcon={true}
				>
					<ClayPanel.Body>
						<Jethr0Table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Value</th>
								</tr>
							</thead>
							<tbody>
								{parameters.map((parameter) => {
									return (
										<tr key={parameter.name}>
											<td title={parameter.name}>
												{parameter.name}
											</td>
											<td>{parameter.value}</td>
										</tr>
									);
								})}
							</tbody>
						</Jethr0Table>
					</ClayPanel.Body>
				</ClayPanel>
			)}
		</>
	);
}

function BuildPage() {
	const {id} = useParams();
	const [build, setBuild] = useState(null);

	if (!build) {
		getBuildById({id, setBuild});
	}

	if (!build) {
		return (
			<ClayLayout.Container>
				<Jethr0Card>
					<Jethr0NavigationBar active="Jobs" />

					<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

					<Heading level={3} weight="lighter">
						{'Build #' + id}
					</Heading>
				</Jethr0Card>
			</ClayLayout.Container>
		);
	}

	let buildName = 'Build #' + id;
	let jobId = 0;
	let jobName = 'Unknown Job';

	if (build) {
		buildName = build.name;

		if (build.job) {
			jobId = build.job.id;
			jobName = build.job.name;
		}
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: false, link: '/jobs/' + jobId, name: jobName},
		{active: true, link: '/jobs/' + jobId + '/' + id, name: buildName},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					{buildName}
				</Heading>

				<BuildInformation build={build} />

				<BuildRuns buildId={id} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

function BuildRuns({buildId}) {
	const [buildRuns, setBuildRuns] = useState(null);

	if (!buildRuns) {
		getBuildRunsByBuildId({buildId, setBuildRuns});
	}

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
						{buildRuns?.map((buildRun) => {
							return (
								<tr key={buildRun.id}>
									<th
										className="font-weight-semi-bold"
										title={buildRun.id}
									>
										{buildRun.id}
									</th>
									<td>
										{toLocaleString(buildRun.dateCreated)}
									</td>
									<td>{buildRun.state.name}</td>
									<td>
										{buildRun.result
											? buildRun.result.name
											: '-'}
									</td>
									<td>
										{toDurationString(buildRun.duration)}
									</td>
									<td>
										{buildRun.jenkinsBuildURL ? (
											<a href={buildRun.jenkinsBuildURL}>
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

export default BuildPage;
