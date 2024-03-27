/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JobDefinition from './JobDefinition';

export function getJobDefinitionById({id, setJobDefinition}) {
	liferayRequest({urlPath: '/o/c/jobdefinitions/' + id})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const jobDefinition = new JobDefinition(resultJSON);

			if (jobDefinition && setJobDefinition) {
				setJobDefinition(jobDefinition);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getJobDefinitionByKey({key, setJobDefinition}) {
	const urlSearchParams = new URLSearchParams({
		filter: "key eq '" + key + "'",
	});

	liferayRequest({urlPath: '/o/c/jobdefinitions', urlSearchParams})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			if (!resultJSON.items || !resultJSON.items.length) {
				setJobDefinition(null);

				return;
			}

			const jobDefinition = new JobDefinition(resultJSON.items[0]);

			if (jobDefinition && setJobDefinition) {
				setJobDefinition(jobDefinition);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getJobDefinitions({setJobDefinitions}) {
	liferayRequest({urlPath: '/o/c/jobdefinitions'})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const jobDefinitions = [];

			resultJSON.items.forEach((item) => {
				jobDefinitions.push(new JobDefinition(item));
			});

			if (jobDefinitions && setJobDefinitions) {
				setJobDefinitions(jobDefinitions);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}
