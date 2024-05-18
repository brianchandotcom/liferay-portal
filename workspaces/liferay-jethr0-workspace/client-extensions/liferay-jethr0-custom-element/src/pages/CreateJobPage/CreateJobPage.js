/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0Input from '../../components/Jethr0Input/Jethr0Input';
import Jethr0JobFieldLabel from '../../components/Jethr0JobFieldLabel/Jethr0JobFieldLabel';
import Jethr0JobParameterFields from '../../components/Jethr0JobParameterFields/Jethr0JobParameterFields';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0SelectWithOption from '../../components/Jethr0SelectWithOption/Jethr0SelectWithOption';
import {getJobDefinitions} from '../../objects/jobdefinitions/JobDefinitionUtil';
import {createJob, getJobParameter} from '../../objects/jobs/JobUtil';
import {getRoutineById} from '../../objects/routines/RoutineUtil';

function CreateJobPage() {
	const [jobBlessed, setJobBlessed] = useState(null);
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
		const defaultJobParameters = [];

		if (jobDefinition?.jobDefinitionParameters) {
			jobDefinition.jobDefinitionParameters.forEach(
				(jobDefinitionParameter) => {
					let defaultJobParameter;

					if (jobDefinitionParameter.valueDefault) {
						defaultJobParameter = {
							key: jobDefinitionParameter.key,
							value: jobDefinitionParameter.valueDefault,
						};
					}

					if (routine?.jobParameters) {
						const routineJobParameters = JSON.parse(
							routine?.jobParameters
						);

						const routineJobParameter = getJobParameter({
							jobParameters: routineJobParameters,
							key: jobDefinitionParameter.key,
						});

						if (
							routineJobParameter &&
							routineJobParameter.value !== ''
						) {
							defaultJobParameter = {
								fromRoutine: true,
								key: jobDefinitionParameter.key,
								value: routineJobParameter.value,
							};
						}
					}

					if (defaultJobParameter) {
						defaultJobParameters.push(defaultJobParameter);
					}
				}
			);
		}

		setJobParameters(defaultJobParameters);

		return;
	}

	let breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/create', name: 'Create Job'},
	];

	if (routine) {
		breadcrumbs = [
			{active: false, link: '/', name: 'Home'},
			{active: false, link: '/routines', name: 'Routines'},
			{
				active: false,
				link: '/routines/' + routine.id,
				name: routine.name,
			},
			{active: true, link: '/jobs/create', name: 'Create Job'},
		];
	}

	const jobTypeOptions = jobDefinitions.map((jobDefinition) => {
		return {
			label: jobDefinition.label,
			value: jobDefinition.key,
		};
	});

	const jobData = {
		blessed: jobBlessed,
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
				<Jethr0NavigationBar active={routine ? 'Routines' : 'Jobs'} />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Create Job
				</Heading>

				<ClayForm.Group>
					<Jethr0JobFieldLabel
						labelKey="jobBlessed"
						labelName="Job Blessed"
					/>

					<ClayCheckbox
						id="jobBlessed"
						onChange={(event) => {
							setJobBlessed(event.target.checked);
						}}
						value={jobBlessed}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<Jethr0JobFieldLabel
						fromRoutine={routine?.jobPriority ? true : false}
						labelKey="jobPriority"
						labelName="Job Priority"
						routine={routine}
					/>

					<Jethr0Input
						disabled={routine?.jobPriority ? true : false}
						id="jobPriority"
						onChange={(event) => {
							setJobPriority(event.target.value);
						}}
						type="text"
						value={jobPriority}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<Jethr0JobFieldLabel
						fromRoutine={routine?.jobType ? true : false}
						labelKey="jobType"
						labelName="Job Type"
						routine={routine}
					/>

					<Jethr0SelectWithOption
						ariaLabel="Job Types"
						disabled={routine?.jobType ? true : false}
						id="jobType"
						onChange={(event) => {
							setJobDefinitionKey(event.target.value);
						}}
						options={jobTypeOptions}
						value={jobDefinitionKey}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<Jethr0JobFieldLabel
						fromRoutine={routine?.jobName ? true : false}
						labelKey="jobName"
						labelName="Job Name"
						routine={routine}
					/>

					<Jethr0Input
						disabled={routine?.jobName ? true : false}
						id="jobName"
						onChange={(event) => {
							setJobName(event.target.value);
						}}
						placeholder="Insert your name here"
						type="text"
						value={jobName}
					/>
				</ClayForm.Group>

				<Jethr0JobParameterFields
					jobDefinitionParameters={
						jobDefinition.jobDefinitionParameters
					}
					jobParameters={jobParameters}
					routine={routine}
					setJobParameters={setJobParameters}
				/>

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
