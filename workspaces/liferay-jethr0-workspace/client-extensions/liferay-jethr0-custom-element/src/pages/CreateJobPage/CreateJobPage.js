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
import postSpringBootData from '../../services/postSpringBootData';
import useSpringBootData from '../../services/useSpringBootData';

function CreateJobPage() {
	const [jobName, setJobName] = useState(null);
	const [jobParameters, setJobParameters] = useState(null);
	const [jobPriority, setJobPriority] = useState(4);
	const [jobTypeKey, setJobTypeKey] = useState('portalPullRequestSF');
	const [jobTypes, setJobTypes] = useState(null);

	function redirectToJobPage(data) {
		const json = JSON.parse(data);

		if (json !== null && json.id !== null) {
			window.location.replace('/#/jobs/' + json.id);
		}
	}

	function setJobNameFromJobTypeKey(jobTypeKey) {
		for (const jobType of jobTypes) {
			if (jobType.key === jobTypeKey) {
				setJobName(jobType.name);

				break;
			}
		}
	}

	function setJobParametersFromJobTypeKey(jobTypeKey) {
		for (const jobType of jobTypes) {
			if (jobType.key === jobTypeKey) {
				let jobParameters = {};

				jobType.parameterDefinitions.forEach((parameterDefinition) => {
					jobParameters[parameterDefinition.key] = parameterDefinition.valueDefault;
				});

				setJobParameters(jobParameters);

				break;
			}
		}
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/create', name: 'Create Job'},
	];

	useSpringBootData({
		setData: setJobTypes,
		urlPath: '/jobs/types',
	});

	let jobParameterDefinitions = null;
	let jobTypesOptions = [];

	if (jobTypes !== null) {
		jobTypesOptions = jobTypes.map((jobType) => {
			return {
				label: jobType.name,
				value: jobType.key,
			};
		});

		if (jobName === null && jobTypeKey !== null) {
			setJobNameFromJobTypeKey(jobTypeKey);
		}

		if (jobParameters === null && jobTypeKey !== null) {
			setJobParametersFromJobTypeKey(jobTypeKey);
		}

		let jobType = jobTypes.find((jobType) => {
			return jobType.key === jobTypeKey;
		});

		jobParameterDefinitions = jobType.parameterDefinitions;
	}

	const jobData = {
		name: jobName,
		parameters: jobParameters,
		priority: jobPriority,
		state: 'queued',
		type: jobTypeKey,
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
						disabled="true"
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
							setJobNameFromJobTypeKey(event.target.value);
							setJobParametersFromJobTypeKey(event.target.value);
							setJobTypeKey(event.target.value);
						}}
						options={jobTypesOptions}
						value={jobTypeKey}
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

				{
					jobParameters && jobParameterDefinitions &&
					jobParameterDefinitions.map((jobParameterDefinition) => {
						return (
							<ClayForm.Group key={jobParameterDefinition.key}>
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
									value={jobParameters[jobParameterDefinition.key] || ''}
								/>
							</ClayForm.Group>
						);
					})
				}

				<Jethr0ButtonsRow
					buttons={[
						{
							displayType: 'secondary',
							link: '/jobs',
							title: 'Cancel',
						},
						{
							onClick: () => {
								postSpringBootData({
									data: jobData,
									redirect: redirectToJobPage,
									urlPath: '/jobs/create',
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
