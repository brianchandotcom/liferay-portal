/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
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
	const response = await liferayRequest({urlPath: '/o/c/jobs/' + id});

	const result = JSON.parse(await response.text());

	const job = new Job(result);

	if (job && setJob) {
		setJob(job);
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

	getJobs({
		orderedJobIds: JSON.parse(result.items[0].prioritizedJobIds),
		setJobs,
	});
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
