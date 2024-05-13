/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JenkinsCohort from './JenkinsCohort';
import JenkinsNode from './JenkinsNode';
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

export async function createJenkinsServer({data, redirect}) {
	const response = await liferayRequest({
		body: JSON.stringify(data),
		headers: {
			'Content-Type': 'application/json',
			'accept': 'application/json',
		},
		method: 'POST',
		urlPath: '/o/c/jenkinsservers',
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

export async function deleteJenkinsServerById({id, redirect}) {
	const response = await liferayRequest({
		method: 'DELETE',
		urlPath: '/o/c/jenkinsservers/' + id,
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
						jenkinsServerCount
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
						jenkinsNodeCount
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

export async function getJenkinsServerById({id, setJenkinsServer}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jenkinsNodes (filter: \\"r_jenkinsServerToJenkinsNodes_c_jenkinsServerId eq '${id}'\\") {
					items {
						dateCreated
						dateModified
						goodBattery
						id
						name
						nodeCount
						nodeRAM
						primaryLabel
						type {
							key
							name
						}
						url
					}
				}
				jenkinsServers (filter: \\"id eq '${id}'\\") {
					items {
						dateCreated
						dateModified
						id
						jenkinsCohortToJenkinsServers
						jenkinsNodeCount
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

	const jenkinsNodes = [];

	for (const jenkinsNodesJSON of result.data.c.jenkinsNodes.items) {
		jenkinsNodes.push(new JenkinsNode(jenkinsNodesJSON));
	}

	for (const jenkinsServerJSON of result.data.c.jenkinsServers.items) {
		const jenkinsServer = new JenkinsServer(jenkinsServerJSON);

		jenkinsServer.jenkinsCohort = new JenkinsCohort(
			jenkinsServerJSON.jenkinsCohortToJenkinsServers
		);

		jenkinsServer.jenkinsNodes = jenkinsNodes;

		if (jenkinsServer) {
			if (setJenkinsServer) {
				setJenkinsServer(jenkinsServer);
			}

			return jenkinsServer;
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
						jenkinsServerCount
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

export async function getJenkinsNodesPage({
	jenkinsServer,
	page,
	pageSize,
	setJenkinsNodesPage,
}) {
	if (!page) {
		page = 1;
	}

	if (!pageSize) {
		pageSize = 25;
	}

	let filter = '';

	if (jenkinsServer) {
		filter = `r_jenkinsServerToJenkinsNodes_c_jenkinsServerId eq '${jenkinsServer.id}'`;
	}

	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jenkinsNodes (filter: \\"${filter}\\", page: ${page}, pageSize: ${pageSize}) {
					items {
						dateCreated
						dateModified
						goodBattery
						id
						name
						nodeCount
						nodeRAM
						primaryLabel
						type {
							key
							name
						}
						url
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

	const jenkinsNodes = [];

	for (const item of result.data.c.jenkinsNodes.items) {
		jenkinsNodes.push(new JenkinsNode(item));
	}

	const jenkinsNodesPage = {
		jenkinsNodes,
		page: result.data.c.jenkinsNodes.page,
		pageSize: result.data.c.jenkinsNodes.pageSize,
		totalCount: result.data.c.jenkinsNodes.totalCount,
	};

	if (setJenkinsNodesPage) {
		setJenkinsNodesPage(jenkinsNodesPage);
	}
}

export async function getJenkinsServersPage({
	jenkinsCohort,
	page,
	pageSize,
	setJenkinsServersPage,
}) {
	if (!page) {
		page = 1;
	}

	if (!pageSize) {
		pageSize = 25;
	}

	let filter = '';

	if (jenkinsCohort) {
		filter = `r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId eq '${jenkinsCohort.id}'`;
	}

	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jenkinsServers (filter: \\"${filter}\\", sort: \\"name\\", page: ${page}, pageSize: ${pageSize}) {
					items {
						dateCreated
						dateModified
						id
						jenkinsCohortToJenkinsServers
						jenkinsNodeCount
						name
						url
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

	const jenkinsServers = [];

	for (const jenkinsServersJSON of result.data.c.jenkinsServers.items) {
		const jenkinsServer = new JenkinsServer(jenkinsServersJSON);

		jenkinsServer.jenkinsCohort = new JenkinsCohort(
			jenkinsServersJSON.jenkinsCohortToJenkinsServers
		);

		jenkinsServers.push(jenkinsServer);
	}

	const jenkinsServersPage = {
		jenkinsServers,
		page: result.data.c.jenkinsServers.page,
		pageSize: result.data.c.jenkinsServers.pageSize,
		totalCount: result.data.c.jenkinsServers.totalCount,
	};

	if (setJenkinsServersPage) {
		setJenkinsServersPage(jenkinsServersPage);
	}
}
