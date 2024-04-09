/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Build from '../builds/Build';
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

	const createJobResponse = await liferayRequest({
		headers,
		method: 'PUT',
		urlPath: `/o/c/jobs/${jobsResult.id}/object-actions/Jethr0EtcSpringBootJobAdd`,
	});

	const createJobResult = JSON.parse(await createJobResponse.text());

	if (createJobResult && redirect) {
		redirect(createJobResult);
	}
}

export async function deleteJobById({id, redirect}) {
	const response = await liferayRequest({
		method: 'DELETE',
		urlPath: '/o/c/jobs/' + id,
	});

	const result = JSON.parse(await response.text());

	if (redirect && result) {
		redirect(result);
	}
}

export async function getJobById({id, setJob}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				builds(filter: \\"r_jobToBuilds_c_jobId eq '${id}'\\") {
					items {
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
				jobs(filter: \\"id eq '${id}'\\") {
					items {
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

		if (job) {
			if (setJob) {
				setJob(job);
			}

			return job;
		}
	}
}

export async function getJobQueueOrderedJobs({setJobs}) {
	const response = await liferayRequest({
		urlPath: '/o/c/jobprioritizers',
		urlSearchParams: new URLSearchParams({
			pageSize: 1,
			sort: 'dateCreated:desc',
		}),
	});

	const result = JSON.parse(await response.text());

	const jobs = [];

	for (const id of JSON.parse(result.items[0].prioritizedJobIds)) {
		const job = await getJobById({id});

		jobs.push(job);
	}

	setJobs(jobs);
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
