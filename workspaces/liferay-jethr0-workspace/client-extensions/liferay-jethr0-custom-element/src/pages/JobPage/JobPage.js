/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {useState} from 'react';
import {Link, useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0ContainerFluid from '../../components/Jethr0ContainerFluid/Jethr0ContainerFluid';
import Jethr0InformationField from '../../components/Jethr0InformationField/Jethr0InformationField';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {getJobDefinitionByKey} from '../../objects/jobdefinitions/JobDefinitionUtil';
import {deleteJobById, getJobById} from '../../objects/jobs/JobUtil';
import {toLocaleString} from '../../services/DateUtil';
import {toDurationString} from '../../services/DurationUtil';

function JobBuilds({job}) {
	if (!job?.builds) {
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
							<th>Jenkins Duration</th>
							<th>Jenkins Build</th>
						</tr>
					</thead>
					<tbody>
						{job.builds?.map((jobBuild) => {
							return (
								<tr key={jobBuild.id}>
									<th className="font-weight-semi-bold">
										<Link
											title={jobBuild.id}
											to={'/builds/' + jobBuild.id}
										>
											{jobBuild.id}
										</Link>
									</th>
									<td>{jobBuild.name}</td>
									<td>
										{toLocaleString(jobBuild.dateCreated)}
									</td>
									<td>{jobBuild.state.name}</td>
									<td>{jobBuild.initialBuild.toString()}</td>
									<td>
										{toDurationString(
											jobBuild.latestDuration
										)}
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

function JobInformation({job}) {
	const [jobDefinition, setJobDefinition] = useState(null);

	if (!jobDefinition) {
		getJobDefinitionByKey({key: job.type.key, setJobDefinition});

		return;
	}

	if (!job) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Job Information"
				displayType="secondary"
			>
				<ClayPanel.Body>Loading...</ClayPanel.Body>
			</ClayPanel>
		);
	}

	let jobParameters = [];

	if (job.parameters) {
		jobParameters = JSON.parse(job.parameters);
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Job Information"
			displayType="secondary"
		>
			<ClayPanel.Body>
				<Jethr0InformationField
					fieldLabel="Job Name"
					fieldType="STRING"
					fieldValue={job.name}
				/>

				<Jethr0InformationField
					fieldLabel="Job ID"
					fieldType="STRING"
					fieldValue={job.id}
				/>

				<strong>{'Job Blessed: '}</strong>
				{job.blessed ? (
					<ClayBadge displayType="success" label="blessed" />
				) : (
					<ClayBadge displayType="secondary" label="normal" />
				)}
				<br />

				<Jethr0InformationField
					fieldLabel="Job State"
					fieldType="STRING"
					fieldValue={job.state.name}
				/>

				<Jethr0InformationField
					fieldLabel="Job Type"
					fieldType="STRING"
					fieldValue={job.type.name}
				/>

				{job.routine && (
					<Jethr0InformationField
						fieldLabel="Routine"
						fieldType="URL"
						fieldURLValue={'/#/routines/' + job.routine.id}
						fieldValue={job.routine.name}
					/>
				)}

				<Jethr0InformationField
					fieldLabel="Create Date"
					fieldType="DATE"
					fieldValue={job.dateCreated}
				/>

				<Jethr0InformationField
					fieldLabel="Modified Date"
					fieldType="DATE"
					fieldValue={job.dateModified}
				/>

				<Jethr0InformationField
					fieldLabel="Start Date"
					fieldType="DATE"
					fieldValue={job.startDate}
				/>

				{jobDefinition.jobDefinitionParameters &&
					jobParameters?.map((jobParameter) => {
						let parameter;

						for (const jobDefinitionParameter of jobDefinition.jobDefinitionParameters) {
							if (
								jobDefinitionParameter.key === jobParameter.key
							) {
								parameter = jobDefinitionParameter;

								break;
							}
						}

						if (parameter) {
							return (
								<Jethr0InformationField
									fieldLabel={parameter.label}
									fieldType={parameter.type.name}
									fieldValue={jobParameter.value}
									key={jobParameter.key}
								/>
							);
						}
					})}
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function JobPage() {
	const {id} = useParams();
	const [job, setJob] = useState(null);

	if (!job) {
		getJobById({id, setJob});
	}

	if (!job) {
		return (
			<ClayLayout.Container>
				<Jethr0Card>
					<Jethr0NavigationBar active="Jobs" />
					<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
					<Jethr0ContainerFluid>
						<ClayLayout.Row justify="between">
							<Heading level={3} weight="lighter">
								{'Job #' + id}
							</Heading>
						</ClayLayout.Row>
					</Jethr0ContainerFluid>
				</Jethr0Card>
			</ClayLayout.Container>
		);
	}

	function redirectToJobsPage() {
		if (job.routine) {
			window.location.replace('/#/routines/' + job.routine.id);
		}
		else {
			window.location.replace('/#/jobs');
		}
	}

	let jobName = 'Job #' + id;

	if (job) {
		jobName = job.name;
	}

	let breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/' + id, name: jobName},
	];

	if (job.routine) {
		breadcrumbs = [
			{active: false, link: '/', name: 'Home'},
			{active: false, link: '/routines', name: 'Routines'},
			{
				active: false,
				link: '/routines/' + job.routine.id,
				name: job.routine.name,
			},
			{active: true, link: '/jobs/' + id, name: jobName},
		];
	}

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar
					active={job.routine ? 'Routines' : 'Jobs'}
				/>

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							{jobName}
						</Heading>

						<Jethr0ButtonsRow
							buttons={[
								{
									link: '/jobs/' + id + '/update',
									title: 'Update',
								},
								{
									onClick: () => {
										deleteJobById({
											id,
											redirect: redirectToJobsPage,
										});
									},
									title: 'Delete',
								},
							]}
						/>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<JobInformation job={job} />

				<JobBuilds job={job} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JobPage;
