/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JobDefinition from './JobDefinition';
import JobDefinitionParameter from './JobDefinitionParameter';

export async function getJobDefinitionById({id, setJobDefinition}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jobDefinitions(filter: \\"id eq '${id}'\\") {
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

	const jobDefinitionJSON = result.data.c.jobDefinitions.items[0];

	if (!jobDefinitionJSON) {
		return;
	}

	const jobDefinition = _getJobDefinition(jobDefinitionJSON);

	if (jobDefinition && setJobDefinition) {
		setJobDefinition(jobDefinition);
	}
}

export async function getJobDefinitionByKey({key, setJobDefinition}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jobDefinitions(filter: \\"key eq '${key}'\\") {
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

	const jobDefinitionJSON = result.data.c.jobDefinitions.items[0];

	if (!jobDefinitionJSON) {
		return;
	}

	const jobDefinition = _getJobDefinition(jobDefinitionJSON);

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
			jobDefinitions.push(_getJobDefinition(item));
		}
	}

	if (jobDefinitions && setJobDefinitions) {
		setJobDefinitions(jobDefinitions);
	}
}

function _getJobDefinition(jobDefinitionJSON) {
	const jobDefinition = new JobDefinition(jobDefinitionJSON);

	jobDefinitionJSON.jobDefinitionsToJobDefinitionParameters.forEach(
		(jobDefinitionParameter) => {
			jobDefinition.jobDefinitionParameters.push(
				new JobDefinitionParameter(jobDefinitionParameter)
			);
		}
	);

	return jobDefinition;
}
