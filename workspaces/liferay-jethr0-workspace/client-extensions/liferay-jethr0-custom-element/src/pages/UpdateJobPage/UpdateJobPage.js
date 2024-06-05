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
import {getJobById, updateJob} from '../../objects/jobs/JobUtil';

function UpdateJobPage() {
	const {id} = useParams();
	const [job, setJob] = useState(null);
	const [jobBlessed, setJobBlessed] = useState(null);
	const [jobDefinitionKey, setJobDefinitionKey] = useState(null);
	const [jobDefinitions, setJobDefinitions] = useState(null);
	const [jobName, setJobName] = useState(null);
	const [jobParameters, setJobParameters] = useState(null);
	const [jobPriority, setJobPriority] = useState(4);

	async function updateJobData({id}) {
		const job = await getJobById({id});

		setJobBlessed(job.blessed);
		setJobDefinitionKey(job.type.key);
		setJobName(job.name);
		setJobParameters(JSON.parse(job.parameters));
		setJobPriority(job.priority);

		setJob(job);
	}

	if (!job) {
		updateJobData({id, setJob});
	}

	function redirectToJobPage(data) {
		if (data !== null && data.id !== null) {
			window.location.replace('/#/jobs/' + data.id);
		}
	}

	if (!jobDefinitions) {
		getJobDefinitions({setJobDefinitions});

		return;
	}

	if (!jobDefinitionKey) {
		setJobDefinitionKey('default');

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

					if (defaultJobParameter) {
						defaultJobParameters.push(defaultJobParameter);
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
		blessed: jobBlessed,
		name: jobName,
		parameters: JSON.stringify(jobParameters),
		priority: jobPriority,
		r_routineToJobs_c_routineId: job.routine?.id,
		state: 'queued',
		type: jobDefinitionKey,
	};

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Edit Job
				</Heading>

				<ClayForm.Group>
					<Jethr0JobFieldLabel labelKey="jobId" labelName="Job ID" />

					<Jethr0Input
						disabled={true}
						id="jobId"
						type="text"
						value={id}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<Jethr0JobFieldLabel
						labelKey="jobBlessed"
						labelName="Job Blessed"
					/>

					<ClayCheckbox
						checked={jobBlessed}
						id="jobBlessed"
						onChange={(event) => {
							setJobBlessed(event.target.checked);
						}}
						value={jobBlessed}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<Jethr0JobFieldLabel
						labelKey="jobPriority"
						labelName="Job Priority"
					/>

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
					<Jethr0JobFieldLabel
						labelKey="jobType"
						labelName="Job Type"
					/>

					<Jethr0SelectWithOption
						ariaLabel="Job Types"
						disabled={true}
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
						labelKey="jobName"
						labelName="Job Name"
					/>

					<Jethr0Input
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
					disabled={true}
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
							link: '/jobs',
							title: 'Cancel',
						},
						{
							onClick: () => {
								updateJob({
									data: jobData,
									id,
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

export default UpdateJobPage;
