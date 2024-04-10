/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import {getJobDefinitions} from '../../objects/jobdefinitions/JobDefinitionUtil';
import {createRoutine, getRoutineTypes} from '../../objects/routines/RoutineUtil';

function CreateRoutinePage() {
	const [jobDefinitionKey, setJobDefinitionKey] = useState('default');
	const [jobDefinitions, setJobDefinitions] = useState(null);
	const [jobName, setJobName] = useState(null);
	const [jobParameters, setJobParameters] = useState(null);
	const [jobPriority, setJobPriority] = useState(4);
	const [routineCron, setRoutineCron] = useState(null);
	const [routineName, setRoutineName] = useState(null);
	const [routineTypeKey, setRoutineTypeKey] = useState('manual');
	const [routineTypes, setRoutineTypes] = useState(null);

	function redirectToRoutinePage(data) {
		if (data !== null && data.id !== null) {
			window.location.replace('/#/routines/' + data.id);
		}
	}

	if (!jobDefinitions) {
		getJobDefinitions({setJobDefinitions});

		return;
	}

	if (!routineTypes) {
		getRoutineTypes({setRoutineTypes})

		return;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/routines', name: 'Routines'},
		{active: true, link: '/routines/create', name: 'Create Routine'},
	];

	let jobTypeOptions = [];

	if (jobDefinitions) {
		jobTypeOptions = jobDefinitions.map((jobDefinition) => {
			return {
				label: jobDefinition.label,
				value: jobDefinition.key,
			};
		});
	}

	let jobDefinition = null;

	for (const candidateJobDefinition of jobDefinitions) {
		if (candidateJobDefinition.key === jobDefinitionKey) {
			jobDefinition = candidateJobDefinition;
		}
	}

	if (!jobParameters && jobDefinition?.jobDefinitionParameters) {
		const defaultJobParameters = {};

		jobDefinition.jobDefinitionParameters.forEach(
			(jobDefinitionParameter) => {
				if (jobDefinitionParameter.valueDefault) {
					defaultJobParameters[jobDefinitionParameter.key] =
						jobDefinitionParameter.valueDefault;
				}
			}
		);

		setJobParameters(defaultJobParameters);

		return;
	}

	let routineTypeOptions = [];

	if (routineTypes) {
		routineTypeOptions = routineTypes.map((routineType) => {
			return {
				label: routineType.name,
				value: routineType.key,
			};
		});
	}

	const routineData = {
		name: routineName,
		cron: routineCron,
		jobName: jobName,
		jobParameters: JSON.stringify(jobParameters),
		jobPriority: jobPriority,
		jobType: jobDefinition.key,
		type: routineTypeKey,
	};

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Create Routine
				</Heading>

				<ClayForm.Group>
					<label htmlFor="routineName">Routine Name</label>

					<ClayInput
						id="routineName"
						onChange={(event) => {
							setRoutineName(event.target.value);
						}}
						placeholder="Insert your name here"
						type="text"
						value={routineName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="routineCron">Routine Cron</label>

					<ClayInput
						id="routineCron"
						onChange={(event) => {
							setRoutineCron(event.target.value);
						}}
						placeholder="Insert your cron here (e.g. 5 4 * * *)"
						type="text"
						value={routineCron}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="routineType">Routine Type</label>

					<ClaySelectWithOption
						aria-label="Routine Types"
						id="routineType"
						onChange={(event) => {
							setRoutineTypeKey(event.target.value);
						}}
						options={routineTypeOptions}
						value={routineTypeKey}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="jobName">Job Name</label>

					<ClayInput
						id="jobName"
						onChange={(event) => {
							setJobName(event.target.value);
						}}
						placeholder="Insert your name here (e.g. 'Job $(current.date)'"
						type="text"
						value={jobName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="jobPriority">Job Priority</label>

					<ClayInput
						id="jobPriority"
						onChange={(event) => {
							setJobPriority(event.target.value);
						}}
						type="text"
						value={jobPriority}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="jobType">Job Type</label>

					<ClaySelectWithOption
						aria-label="Job Types"
						id="jobType"
						onChange={(event) => {
							setJobDefinitionKey(event.target.value);
						}}
						options={jobTypeOptions}
						value={jobDefinition.key}
					/>
				</ClayForm.Group>

				{jobParameters &&
					jobDefinition.jobDefinitionParameters?.map(
						(jobParameterDefinition) => {
							return (
								<ClayForm.Group
									key={jobParameterDefinition.key}
								>
									<label htmlFor={jobParameterDefinition.key}>
										{jobParameterDefinition.label}
									</label>

									<ClayInput
										id={jobParameterDefinition.key}
										onChange={(event) => {
											setJobParameters({
												...jobParameters,
												[jobParameterDefinition.key]:
													event.target.value,
											});
										}}
										placeholder={
											jobParameterDefinition.valueDescription
										}
										type="text"
										value={
											jobParameters[
												jobParameterDefinition.key
											] || ''
										}
									/>
								</ClayForm.Group>
							);
						}
					)}

				<Jethr0ButtonsRow
					buttons={[
						{
							displayType: 'secondary',
							link: '/routines',
							title: 'Cancel',
						},
						{
							onClick: () => {
								createRoutine({
									data: routineData,
									redirect: redirectToRoutinePage,
								});
							},
							title: 'Save',
						},
					]}
				/>
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default CreateRoutinePage;
