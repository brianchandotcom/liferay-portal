/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JobDefinitionParameter from './JobDefinitionParameter';
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
	const pagesResponse = await liferayRequest({
		graphqlQuery: `{
			c {
			  jobDefinitions {
				page
				pageSize
				lastPage
			  }
			}
		  }`,
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
		urlPath: '/o/graphql',
	});

	const pagesResult = JSON.parse(await pagesResponse.text());

	const jobDefinitions = [];

	for (let i = 1; i <= pagesResult.data.c.jobDefinitions.lastPage; i++) {
		const response = await liferayRequest({
			graphqlQuery: `{
				c {
					jobDefinitions (page: ${i}) {
						items {
							dateCreated
							dateModified
							id
							jobDefinitionsToJobDefinitionParameters
							key
							label
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

		for (const item of result.data.c.jobDefinitions.items) {
			const jobDefinition = new JobDefinition(item);

			item.jobDefinitionsToJobDefinitionParameters.forEach(jobDefinitionParameter => {
				jobDefinition.jobDefinitionParameters.push(new JobDefinitionParameter(jobDefinitionParameter));
			});

			jobDefinitions.push(jobDefinition);
		}
	}

	if (jobDefinitions && setJobDefinitions) {
		setJobDefinitions(jobDefinitions);
	}
}
