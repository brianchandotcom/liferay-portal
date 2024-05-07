/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
import {
	deleteRoutineById,
	getRoutineById,
} from '../../objects/routines/RoutineUtil';
import {toLocaleString} from '../../services/DateUtil';

function RoutineJobs({routine}) {
	if (!routine?.jobs) {
		return <div>Loading...</div>;
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Jobs"
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<Jethr0Table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Type</th>
							<th>Create Date</th>
							<th>State</th>
						</tr>
					</thead>
					<tbody>
						{routine.jobs?.map((routineJob) => {
							return (
								<tr key={routineJob.id}>
									<th className="font-weight-semi-bold">
										<Link
											title={routineJob.id}
											to={'/jobs/' + routineJob.id}
										>
											{routineJob.id}
										</Link>
									</th>
									<td>{routineJob.name}</td>
									<td>{routineJob.type.name}</td>
									<td>
										{toLocaleString(routineJob.dateCreated)}
									</td>
									<td>{routineJob.state.name}</td>
								</tr>
							);
						})}
					</tbody>
				</Jethr0Table>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function RoutineInformation({routine}) {
	const [jobDefinition, setJobDefinition] = useState(null);

	if (!routine) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Routine Information"
				displayType="secondary"
			>
				<ClayPanel.Body>Loading...</ClayPanel.Body>
			</ClayPanel>
		);
	}

	if (!jobDefinition) {
		getJobDefinitionByKey({key: routine.jobType.key, setJobDefinition});

		return;
	}

	let jobParameters = [];

	if (routine?.jobParameters) {
		jobParameters = JSON.parse(routine.jobParameters);
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Routine Information"
			displayType="secondary"
		>
			<ClayPanel.Body>
				<Jethr0InformationField
					fieldLabel="Routine Name"
					fieldType="STRING"
					fieldValue={routine.name}
				/>

				<Jethr0InformationField
					fieldLabel="Routine ID"
					fieldType="STRING"
					fieldValue={routine.id}
				/>

				<Jethr0InformationField
					fieldLabel="Routine Cron"
					fieldType="STRING"
					fieldValue={routine.cron}
				/>

				<Jethr0InformationField
					fieldLabel="Job Name"
					fieldType="STRING"
					fieldValue={routine.jobName}
				/>

				<Jethr0InformationField
					fieldLabel="Job Type"
					fieldType="STRING"
					fieldValue={routine.type.name}
				/>

				<Jethr0InformationField
					fieldLabel="Create Date"
					fieldType="DATE"
					fieldValue={routine.dateCreated}
				/>

				<Jethr0InformationField
					fieldLabel="Modified Date"
					fieldType="DATE"
					fieldValue={routine.dateModified}
				/>

				{routine?.upstreamGitBranch && (
					<Jethr0InformationField
						fieldLabel="Upstream Branch"
						fieldType="URL"
						fieldURLValue={
							'/#/upstream-branches/' +
							routine.upstreamGitBranch.id
						}
						fieldValue={
							routine.upstreamGitBranch.userName +
							'/' +
							routine.upstreamGitBranch.repositoryName +
							'/' +
							routine.upstreamGitBranch.name
						}
					/>
				)}

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

function RoutinePage() {
	const {id} = useParams();
	const [routine, setRoutine] = useState(null);

	if (!routine) {
		getRoutineById({id, setRoutine});
	}

	if (!routine) {
		return (
			<ClayLayout.Container>
				<Jethr0Card>
					<Jethr0NavigationBar active="Routines" />

					<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

					<Jethr0ContainerFluid>
						<ClayLayout.Row justify="between">
							<Heading level={3} weight="lighter">
								{'Routine #' + id}
							</Heading>
						</ClayLayout.Row>
					</Jethr0ContainerFluid>
				</Jethr0Card>
			</ClayLayout.Container>
		);
	}

	function redirectToRoutinesPage() {
		window.location.replace('/#/routines');
	}

	let routineName = 'Routine #' + id;

	if (routine) {
		routineName = routine.name;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/routines', name: 'Routines'},
		{active: true, link: '/routines/' + id, name: routineName},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Routines" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							{routineName}
						</Heading>

						<Jethr0ButtonsRow
							buttons={[
								{
									link: `/routines/${id}/create-job`,
									title: 'Create Job',
								},
								{
									onClick: () => {
										deleteRoutineById({
											id,
											redirect: redirectToRoutinesPage,
										});
									},
									title: 'Delete',
								},
							]}
						/>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<RoutineInformation routine={routine} />

				<RoutineJobs routine={routine} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default RoutinePage;
