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
	const [jenkinsGitHubURL, setJenkinsGitHubURL] = useState(null);
	const [jobName, setJobName] = useState(null);
	const [jobPriority, setJobPriority] = useState(4);
	const [jobTypeKey, setJobTypeKey] = useState('portalPullRequestSF');
	const [jobTypes, setJobTypes] = useState(null);
	const [pullRequestURL, setPullRequestURL] = useState(null);

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

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/create', name: 'Create Job'},
	];

	useSpringBootData({
		setData: setJobTypes,
		urlPath: '/jobs/types',
	});

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
	}

	const jobData = {
		jenkinsGitHubURL,
		name: jobName,
		priority: jobPriority,
		pullRequestURL,
		state: 'opened',
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

				<ClayForm.Group>
					<label htmlFor="jenkinsGitHubURL">Jenkins GitHub URL</label>

					<ClayInput
						id="jenkinsGitHubURL"
						onChange={(event) => {
							setJenkinsGitHubURL(event.target.value);
						}}
						placeholder="Insert your Jenkins GitHub URL here"
						type="text"
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor="pullRequestURL">Pull Request URL</label>

					<ClayInput
						id="pullRequestURL"
						onChange={(event) => {
							setPullRequestURL(event.target.value);
						}}
						placeholder="Insert your Pull Request URL here"
						type="text"
					/>
				</ClayForm.Group>

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
						{
							onClick: () => {
								jobData.state = 'queued';

								postSpringBootData({
									data: jobData,
									redirect: redirectToJobPage,
									urlPath: '/jobs/create',
								});
							},
							title: 'Save & Start',
						},
					]}
				/>
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default CreateJobPage;
