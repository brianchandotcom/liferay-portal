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
				urlPath: `/o/c/jobs/${parentResultJSON.id}/object-actions/jethr0EtcSpringBootJobAdd`,
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

export function getJobs({setJobs}) {
	liferayRequest({urlPath: '/o/c/jobs'})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const jobs = [];

			resultJSON.items.forEach((item) => {
				jobs.push(new Job(item));
			});

			if (jobs && setJobs) {
				setJobs(jobs);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}
