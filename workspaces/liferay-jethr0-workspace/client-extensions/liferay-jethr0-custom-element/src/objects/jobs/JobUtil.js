/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Job from './Job';

export function getJobById({id, setJob}) {
	liferayRequest('/o/c/jobs/' + id)
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
	liferayRequest('/o/c/jobs')
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
