/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0Input from '../../components/Jethr0Input/Jethr0Input';
import Jethr0JobParameterFields from '../../components/Jethr0JobParameterFields/Jethr0JobParameterFields';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0SelectWithOption from '../../components/Jethr0SelectWithOption/Jethr0SelectWithOption';
import {getUpstreamGitBranches} from '../../objects/gitbranches/GitBranchUtil';
import {getJobDefinitions} from '../../objects/jobdefinitions/JobDefinitionUtil';
import {
	createRoutine,
	getRoutineTypes,
} from '../../objects/routines/RoutineUtil';

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
	const [upstreamGitBranchURL, setUpstreamGitBranchURL] = useState(
		'https://github.com/liferay/liferay-portal/tree/master'
	);
	const [upstreamGitBranches, setUpstreamGitBranches] = useState(null);

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
		getRoutineTypes({setRoutineTypes});

		return;
	}

	if (!upstreamGitBranches) {
		getUpstreamGitBranches({setUpstreamGitBranches});

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

					if (defaultJobParameter) {
						defaultJobParameters.push(defaultJobParameter);
					}
				}
			);
		}

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

	let upstreamGitBranch = null;

	for (const candidateUpstreamGitBranch of upstreamGitBranches) {
		if (candidateUpstreamGitBranch.url === upstreamGitBranchURL) {
			upstreamGitBranch = candidateUpstreamGitBranch;
		}
	}

	let upstreamGitBranchOptions = [];

	if (upstreamGitBranches) {
		upstreamGitBranchOptions = upstreamGitBranches.map(
			(upstreamGitBranch) => {
				return {
					label: upstreamGitBranch.url,
					value: upstreamGitBranch.url,
				};
			}
		);
	}

	const routineData = {
		cron: routineCron,
		jobName,
		jobParameters: JSON.stringify(jobParameters),
		jobPriority,
		jobType: jobDefinition.key,
		name: routineName,
		r_gitBranchToRoutines_c_gitBranchId: upstreamGitBranch.id,
		type: routineTypeKey,
		upstreamGitBranch,
	};

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Routines" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Create Routine
				</Heading>

				<ClayForm.Group>
					<label htmlFor="routineName">Routine Name</label>

					<Jethr0Input
						id="routineName"
						onChange={(event) => {
							setRoutineName(event.target.value);
						}}
						placeholder="Insert your name here"
						type="text"
						value={routineName}
					/>
				</ClayForm.Group>

				{(routineTypeKey === 'cron' ||
					routineTypeKey === 'upstreamBranchCron') && (
					<ClayForm.Group>
						<label htmlFor="routineCron">Routine Cron</label>

						<Jethr0Input
							id="routineCron"
							onChange={(event) => {
								setRoutineCron(event.target.value);
							}}
							placeholder="Insert your cron here (e.g. 5 4 * * *)"
							type="text"
							value={routineCron}
						/>
					</ClayForm.Group>
				)}

				{routineTypeKey === 'upstreamBranchCron' && (
					<ClayForm.Group>
						<label htmlFor="upstreamGitBranchURL">
							Upstream Branch
						</label>

						<Jethr0SelectWithOption
							aria-label="Upstream Git Branch"
							id="upstreamGitBranchURL"
							onChange={(event) => {
								setUpstreamGitBranchURL(event.target.value);
							}}
							options={upstreamGitBranchOptions}
							value={upstreamGitBranchURL}
						/>
					</ClayForm.Group>
				)}

				<ClayForm.Group>
					<label htmlFor="routineType">Routine Type</label>

					<Jethr0SelectWithOption
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

					<Jethr0Input
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

					<Jethr0Input
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
						aria-label="Job Types"
						id="jobType"
						onChange={(event) => {
							setJobDefinitionKey(event.target.value);
						}}
						options={jobTypeOptions}
						value={jobDefinition.key}
					/>
				</ClayForm.Group>

				<Jethr0JobParameterFields
					jobDefinitionParameters={
						jobDefinition.jobDefinitionParameters
					}
					jobParameters={jobParameters}
					setJobParameters={setJobParameters}
				/>

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
