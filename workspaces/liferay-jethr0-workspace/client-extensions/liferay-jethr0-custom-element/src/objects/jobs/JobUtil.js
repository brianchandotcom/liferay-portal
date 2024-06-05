/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Build from '../builds/Build';
import Routine from '../routines/Routine';
import Job from './Job';

export async function createJob({data, redirect}) {
	const headers = {
		'Content-Type': 'application/json',
		'accept': 'application/json',
	};

	const jobsResponse = await liferayRequest({
		body: JSON.stringify(data),
		headers,
		method: 'POST',
		urlPath: '/o/c/jobs',
	});

	const jobsResult = JSON.parse(await jobsResponse.text());

	if (jobsResult && redirect) {
		redirect(jobsResult);
	}
}

export async function deleteJobById({id, redirect}) {
	const response = await liferayRequest({
		method: 'DELETE',
		urlPath: '/o/c/jobs/' + id,
	});

	await response.text();

	if (redirect) {
		redirect(null);
	}
}

export async function getJobById({id, setJob}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				builds(filter: \\"r_jobToBuilds_c_jobId eq '${id}'\\") {
					items {
						dateCreated
						dateModified
						id
						initialBuild
						name
						parameters
						state {
							key
							name
						}
					}
				}
				jobs (filter: \\"id eq '${id}'\\") {
					items {
						blessed
						dateCreated
						dateModified
						id
						name
						parameters
						priority
						routineToJobs
						startDate
						state {
							key
							name
						}
						type {
							key
							name
						}
					}
				}
			}
		}`,
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
		urlPath: '/o/graphql',
	});

	const result = JSON.parse(await response.text());

	const builds = [];

	for (const buildJSON of result.data.c.builds.items) {
		builds.push(new Build(buildJSON));
	}

	for (const jobJSON of result.data.c.jobs.items) {
		const job = new Job(jobJSON);

		job.builds = builds;

		if (jobJSON.routineToJobs) {
			job.routine = new Routine(jobJSON.routineToJobs);
		}

		if (job) {
			if (setJob) {
				setJob(job);
			}

			return job;
		}
	}
}

export function getJobParameter({jobParameters, key}) {
	for (const jobParameter of jobParameters) {
		if (jobParameter.key === key) {
			return jobParameter;
		}
	}
}

export async function getJobQueueOrderedJobsPage({
	page,
	pageSize,
	setJobsPage,
}) {
	const response = await liferayRequest({
		urlPath: '/o/c/jobprioritizers',
		urlSearchParams: new URLSearchParams({
			pageSize: 1,
			sort: 'dateCreated:desc',
		}),
	});

	const result = JSON.parse(await response.text());

	const jobPrioritizer = result.items[0];

	if (jobPrioritizer?.prioritizedJobIds) {
		getJobsPage({
			orderedJobIds: JSON.parse(jobPrioritizer.prioritizedJobIds),
			page,
			pageSize,
			setJobsPage,
		});
	}
}

export async function getJobsPage({
	orderedJobIds,
	page,
	pageSize,
	setJobs,
	setJobsPage,
}) {
	let filter = '';

	if (orderedJobIds) {
		for (let i = 0; i < orderedJobIds.length; i++) {
			if (i > 0) {
				filter += ' or ';
			}

			filter += `id eq '${orderedJobIds[i]}'`;
		}
	}

	if (!page) {
		page = 1;
	}

	if (!pageSize) {
		pageSize = 25;
	}

	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jobs (filter: \\"${filter}\\", page: ${page}, pageSize: ${pageSize}) {
					items {
						blessed
						dateCreated
						dateModified
						id
						name
						parameters
						priority
						startDate
						state {
							key
							name
						}
						type {
							key
							name
						}
					}
					page
					pageSize
					totalCount
				}
			}
		}`,
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
		urlPath: '/o/graphql',
	});

	const result = JSON.parse(await response.text());

	const jobsMap = new Map();

	let jobs = [];

	result.data.c.jobs.items.forEach((item) => {
		const job = new Job(item);

		jobs.push(job);

		jobsMap.set(job.id, job);
	});

	if (orderedJobIds) {
		jobs = [];

		for (const jobId of orderedJobIds) {
			const job = jobsMap.get(jobId);

			if (job) {
				jobs.push(jobsMap.get(jobId));
			}
		}
	}

	if (setJobs) {
		setJobs(jobs);
	}

	const jobsPage = {
		jobs,
		page: result.data.c.jobs.page,
		pageSize: result.data.c.jobs.pageSize,
		totalCount: result.data.c.jobs.totalCount,
	};

	if (setJobsPage) {
		setJobsPage(jobsPage);
	}
}

export async function getJobs({orderedJobIds, setJobs}) {
	let filter = '';

	if (orderedJobIds) {
		for (let i = 0; i < orderedJobIds.length; i++) {
			if (i > 0) {
				filter += ' or ';
			}

			filter += `id eq '${orderedJobIds[i]}'`;
		}
	}

	const response = await liferayRequest({
		urlPath: '/o/c/jobs',
		urlSearchParams: new URLSearchParams({filter}),
	});

	const result = JSON.parse(await response.text());

	const jobsMap = new Map();

	let jobs = [];

	result.items.forEach((item) => {
		const job = new Job(item);

		jobs.push(job);

		jobsMap.set(job.id, job);
	});

	if (orderedJobIds) {
		jobs = [];

		for (const jobId of orderedJobIds) {
			const job = jobsMap.get(jobId);

			if (job) {
				jobs.push(jobsMap.get(jobId));
			}
		}
	}

	if (setJobs) {
		setJobs(jobs);
	}
}

export function getUpdatedJobParameters({jobParameters, key, value}) {
	const updatedJobParameters = [];

	let updated = false;

	for (const jobParameter of jobParameters) {
		const jobParameterKey = jobParameter.key;
		let jobParameterValue = jobParameter.value;

		if (jobParameter.key === key) {
			jobParameterValue = value;

			updated = true;
		}

		updatedJobParameters.push({
			key: jobParameterKey,
			value: jobParameterValue,
		});
	}

	if (!updated) {
		updatedJobParameters.push({key, value});
	}

	return updatedJobParameters;
}

export async function updateJob({data, id, redirect}) {
	const headers = {
		'Content-Type': 'application/json',
		'accept': 'application/json',
	};

	const jobsResponse = await liferayRequest({
		body: JSON.stringify(data),
		headers,
		method: 'PUT',
		urlPath: '/o/c/jobs/' + id,
	});

	const jobsResult = JSON.parse(await jobsResponse.text());

	if (jobsResult && redirect) {
		redirect(jobsResult);
	}
}
