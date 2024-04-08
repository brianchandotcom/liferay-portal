/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JobDefinition from './JobDefinition';

export async function getJobDefinitionById({id, setJobDefinition}) {
	const response = await liferayRequest({
		urlPath: '/o/c/jobdefinitions/' + id,
	});

	const result = JSON.parse(await response.text());

	const jobDefinition = new JobDefinition(result);

	if (jobDefinition && setJobDefinition) {
		setJobDefinition(jobDefinition);
	}
}

export async function getJobDefinitionByKey({key, setJobDefinition}) {
	const urlSearchParams = new URLSearchParams({
		filter: "key eq '" + key + "'",
	});

	const response = await liferayRequest({
		urlPath: '/o/c/jobdefinitions',
		urlSearchParams,
	});

	const result = JSON.parse(await response.text());

	if (!result.items || !result.items.length) {
		setJobDefinition(null);

		return;
	}

	const jobDefinition = new JobDefinition(result.items[0]);

	if (jobDefinition && setJobDefinition) {
		setJobDefinition(jobDefinition);
	}
}

export async function getJobDefinitions({setJobDefinitions}) {
	const response = await liferayRequest({urlPath: '/o/c/jobdefinitions'});

	const result = JSON.parse(await response.text());

	const jobDefinitions = [];

	result.items.forEach((item) => {
		jobDefinitions.push(new JobDefinition(item));
	});

	if (jobDefinitions && setJobDefinitions) {
		setJobDefinitions(jobDefinitions);
	}
}
