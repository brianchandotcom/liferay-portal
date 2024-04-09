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
import {createJob} from '../../objects/jobs/JobUtil';

function CreateJobPage() {
	const [jobDefinitionKey, setJobDefinitionKey] = useState('default');
	const [jobDefinitions, setJobDefinitions] = useState(null);
	const [jobName, setJobName] = useState(null);
	const [jobParameters, setJobParameters] = useState(null);
	const [jobPriority, setJobPriority] = useState(4);

	function redirectToJobPage(data) {
		if (data !== null && data.id !== null) {
			window.location.replace('/#/jobs/' + data.id);
		}
	}

	if (!jobDefinitions) {
		getJobDefinitions({setJobDefinitions});

		return;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/create', name: 'Create Job'},
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

	const jobData = {
		name: jobName,
		parameters: JSON.stringify(jobParameters),
		priority: jobPriority,
		state: 'queued',
		type: jobDefinition.key,
	};

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Create Job
				</Heading>

				<ClayForm.Group>
					<label htmlFor="buildPriority">Build Priority</label>

					<ClayInput
						id="buildPriority"
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

				<ClayForm.Group>
					<label htmlFor="jobName">Name</label>

					<ClayInput
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
