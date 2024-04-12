/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0Input from '../../components/Jethr0Input/Jethr0Input';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0SelectWithOption from '../../components/Jethr0SelectWithOption/Jethr0SelectWithOption';
import {getJobDefinitions} from '../../objects/jobdefinitions/JobDefinitionUtil';
import {createJob} from '../../objects/jobs/JobUtil';
import {getRoutineById} from '../../objects/routines/RoutineUtil';

function CreateJobPage() {
	const [jobDefinitionKey, setJobDefinitionKey] = useState(null);
	const [jobDefinitions, setJobDefinitions] = useState(null);
	const [jobName, setJobName] = useState(null);
	const [jobParameters, setJobParameters] = useState(null);
	const [jobPriority, setJobPriority] = useState(4);
	const [routine, setRoutine] = useState(null);
	const {routineId} = useParams();

	function redirectToJobPage(data) {
		if (data !== null && data.id !== null) {
			window.location.replace('/#/jobs/' + data.id);
		}
	}

	if (!jobDefinitions) {
		getJobDefinitions({setJobDefinitions});

		return;
	}

	if (routineId && !routine) {
		getRoutineById({id: routineId, setRoutine});

		return;
	}

	if (!jobDefinitionKey) {
		if (routine?.jobType.key) {
			setJobDefinitionKey(routine.jobType.key);

			return;
		}

		setJobDefinitionKey('default');

		return;
	}

	if (!jobName && routine?.jobName) {
		setJobName(routine?.jobName);

		return;
	}

	if (!jobPriority && routine?.jobPriority) {
		setJobPriority(routine?.jobPriority);

		return;
	}

	let jobDefinition = null;

	for (const candidateJobDefinition of jobDefinitions) {
		if (candidateJobDefinition.key === jobDefinitionKey) {
			jobDefinition = candidateJobDefinition;
		}
	}

	if (!jobParameters) {
		const defaultJobParameters = {};

		if (jobDefinition?.jobDefinitionParameters) {
			jobDefinition.jobDefinitionParameters.forEach(
				(jobDefinitionParameter) => {
					if (jobDefinitionParameter.valueDefault) {
						defaultJobParameters[jobDefinitionParameter.key] =
							jobDefinitionParameter.valueDefault;
					}
				}
			);
		}

		if (routine?.jobParameters) {
			Object.entries(JSON.parse(routine?.jobParameters)).map(
				([key, value]) => {
					if (value) {
						defaultJobParameters[key] = value;
					}
				}
			);
		}

		setJobParameters(defaultJobParameters);

		return;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/create', name: 'Create Job'},
	];

	const jobTypeOptions = jobDefinitions.map((jobDefinition) => {
		return {
			label: jobDefinition.label,
			value: jobDefinition.key,
		};
	});

	const jobData = {
		name: jobName,
		parameters: JSON.stringify(jobParameters),
		priority: jobPriority,
		r_routineToJobs_c_routineId: routine?.id,
		state: 'queued',
		type: jobDefinitionKey,
	};

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Create Job
				</Heading>

				{routine && (
					<>
						<ClayForm.Group>
							<label htmlFor="routineId">Routine ID</label>

							<Jethr0Input
								disabled={true}
								id="routineId"
								type="text"
								value={routine.id}
							/>
						</ClayForm.Group>
						<ClayForm.Group>
							<label htmlFor="routineName">Routine Name</label>

							<Jethr0Input
								disabled={true}
								id="routineName"
								type="text"
								value={routine.name}
							/>
						</ClayForm.Group>
					</>
				)}

				<ClayForm.Group>
					<label htmlFor="jobPriority">Job Priority</label>

					<Jethr0Input
						disabled={routine ? true : false}
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

					<Jethr0SelectWithOption
						ariaLabel="Job Types"
						disabled={routine ? true : false}
						id="jobType"
						onChange={(event) => {
							setJobDefinitionKey(event.target.value);
						}}
						options={jobTypeOptions}
						value={jobDefinitionKey}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="jobName">Name</label>

					<Jethr0Input
						disabled={routine ? true : false}
						id="jobName"
						onChange={(event) => {
							setJobName(event.target.value);
						}}
						placeholder="Insert your name here"
						type="text"
						value={jobName}
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

									<Jethr0Input
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
							link: '/jobs',
							title: 'Cancel',
						},
						{
							onClick: () => {
								createJob({
									data: jobData,
									redirect: redirectToJobPage,
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

export default CreateJobPage;
