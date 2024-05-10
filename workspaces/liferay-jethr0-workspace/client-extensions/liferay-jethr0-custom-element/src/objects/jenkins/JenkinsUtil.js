/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JenkinsCohort from './JenkinsCohort';
import JenkinsServer from './JenkinsServer';

export async function createJenkinsCohort({data, redirect}) {
	const response = await liferayRequest({
		body: JSON.stringify(data),
		headers: {
			'Content-Type': 'application/json',
			'accept': 'application/json',
		},
		method: 'POST',
		urlPath: '/o/c/jenkinscohorts',
	});

	const result = JSON.parse(await response.text());

	if (result && redirect) {
		redirect(result);
	}
}

export async function deleteJenkinsCohortById({id, redirect}) {
	const response = await liferayRequest({
		method: 'DELETE',
		urlPath: '/o/c/jenkinscohorts/' + id,
	});

	await response.text();

	if (redirect) {
		redirect(null);
	}
}

export async function getJenkinsCohortById({id, setJenkinsCohort}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jenkinsCohorts(filter: \\"id eq '${id}'\\") {
					items {
						dateCreated
						dateModified
						id
						name
					}
				}
				jenkinsServers(filter: \\"r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId eq '${id}'\\") {
					items {
						dateCreated
						dateModified
						id
						jenkinsCohortToJenkinsServers
						name
						url
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

	const jenkinsServers = [];

	for (const jenkinsServersJSON of result.data.c.jenkinsServers.items) {
		jenkinsServers.push(new JenkinsServer(jenkinsServersJSON));
	}

	for (const jenkinsCohortJSON of result.data.c.jenkinsCohorts.items) {
		const jenkinsCohort = new JenkinsCohort(jenkinsCohortJSON);

		jenkinsCohort.jenkinsServers = jenkinsServers;

		if (jenkinsCohort) {
			if (setJenkinsCohort) {
				setJenkinsCohort(jenkinsCohort);
			}

			return jenkinsCohort;
		}
	}
}

export async function getJenkinsCohortsPage({
	page,
	pageSize,
	setJenkinsCohortsPage,
}) {
	if (!page) {
		page = 1;
	}

	if (!pageSize) {
		pageSize = 25;
	}

	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jenkinsCohorts (page: ${page}, pageSize: ${pageSize}) {
					items {
						id
						dateCreated
						dateModified
						name
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

	const jenkinsCohorts = [];

	for (const item of result.data.c.jenkinsCohorts.items) {
		jenkinsCohorts.push(new JenkinsCohort(item));
	}

	const jenkinsCohortsPage = {
		jenkinsCohorts,
		page: result.data.c.jenkinsCohorts.page,
		pageSize: result.data.c.jenkinsCohorts.pageSize,
		totalCount: result.data.c.jenkinsCohorts.totalCount,
	};

	if (setJenkinsCohortsPage) {
		setJenkinsCohortsPage(jenkinsCohortsPage);
	}
}
