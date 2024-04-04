/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Job from './Job';

export function createJob({data, redirect}) {
	const headers = {
		'Content-Type': 'application/json',
		'accept': 'application/json',
	};

	liferayRequest({
		body: JSON.stringify(data),
		headers,
		method: 'POST',
		urlPath: '/o/c/jobs',
	})
		.then((parentRequest) => parentRequest.text())
		.then((parentResult) => {
			const parentResultJSON = JSON.parse(parentResult);

			liferayRequest({
				headers,
				method: 'PUT',
				urlPath: `/o/c/jobs/${parentResultJSON.id}/object-actions/Jethr0EtcSpringBootJobAdd`,
			})
				.then((request) => request.text())
				.then(() => {
					if (redirect !== null) {
						redirect(parentResult);
					}
				})
				.catch((error) => {
					// eslint-disable-next-line no-console
					console.log(error);
				});
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function deleteJobById({id, redirect}) {
	liferayRequest({method: 'DELETE', urlPath: '/o/c/jobs/' + id})
		.then((request) => request.text())
		.then((result) => {
			if (redirect !== null) {
				redirect(result);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getJobById({id, setJob}) {
	liferayRequest({urlPath: '/o/c/jobs/' + id})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const job = new Job(resultJSON);

			if (job && setJob) {
				setJob(job);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getJobQueueOrderedJobs({setJobs}) {
	liferayRequest({
		urlPath: '/o/c/jobprioritizers',
		urlSearchParams: new URLSearchParams({
			pageSize: 1,
			sort: 'dateCreated:desc',
		}),
	})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			getJobs({
				orderedJobIds: JSON.parse(
					resultJSON.items[0].prioritizedJobIds
				),
				setJobs,
			});
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getJobs({orderedJobIds, setJobs}) {
	let filter = '';

	if (orderedJobIds) {
		for (let i = 0; i < orderedJobIds.length; i++) {
			if (i > 0) {
				filter += ' or ';
			}

			filter += `id eq '${orderedJobIds[i]}'`;
		}
	}

	liferayRequest({
		urlPath: '/o/c/jobs',
		urlSearchParams: new URLSearchParams({filter}),
	})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const jobsMap = new Map();

			let jobs = [];

			resultJSON.items.forEach((item) => {
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
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}
