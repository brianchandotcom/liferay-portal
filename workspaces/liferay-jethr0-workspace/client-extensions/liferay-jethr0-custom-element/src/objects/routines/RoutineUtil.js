/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import GitBranch from '../gitbranches/GitBranch';
import Job from '../jobs/Job';
import Routine from './Routine';

export async function createRoutine({data, redirect}) {
	const headers = {
		'Content-Type': 'application/json',
		'accept': 'application/json',
	};

	const routinesResponse = await liferayRequest({
		body: JSON.stringify(data),
		headers,
		method: 'POST',
		urlPath: '/o/c/routines',
	});

	const routinesResult = JSON.parse(await routinesResponse.text());

	if (routinesResult && redirect) {
		redirect(routinesResult);
	}
}

export async function deleteRoutineById({id, redirect}) {
	const response = await liferayRequest({
		method: 'DELETE',
		urlPath: '/o/c/routines/' + id,
	});

	await response.text();

	if (redirect) {
		redirect(null);
	}
}

export async function getRoutineById({id, setRoutine}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				jobs(filter: \\"r_routineToJobs_c_routineId eq '${id}'\\") {
					items {
						dateCreated
						dateModified
						id
						name
						parameters
						priority
						startDate
						state {
							key
							name
						}
						type {
							key
							name
						}
					}
				}
				routines(filter: \\"id eq '${id}'\\") {
					items {
						cron
						dateCreated
						dateModified
						gitBranchToRoutines
						id
						name
						jobName
						jobParameters
						jobPriority
						jobType {
							key
							name
						}
						type {
							key
							name
						}
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

	const jobs = [];

	for (const jobsJSON of result.data.c.jobs.items) {
		jobs.push(new Job(jobsJSON));
	}

	for (const routineJSON of result.data.c.routines.items) {
		const routine = new Routine(routineJSON);

		routine.jobs = jobs;

		if (routineJSON.gitBranchToRoutines) {
			routine.upstreamGitBranch = new GitBranch(
				routineJSON.gitBranchToRoutines
			);
		}

		if (routine) {
			if (setRoutine) {
				setRoutine(routine);
			}

			return routine;
		}
	}
}

export async function getRoutinesPage({page, pageSize, setRoutinesPage}) {
	if (!page) {
		page = 1;
	}

	if (!pageSize) {
		pageSize = 25;
	}

	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				routines (page: ${page}, pageSize: ${pageSize}) {
					items {
						dateCreated
						dateModified
						gitBranchToRoutines
						id
						name
						jobName
						jobPriority
						jobType {
							key
							name
						}
						type {
							key
							name
						}
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

	const routines = [];

	for (const item of result.data.c.routines.items) {
		const routine = new Routine(item);

		if (item.gitBranchToRoutines) {
			routine.upstreamGitBranch = new GitBranch(item.gitBranchToRoutines);
		}

		routines.push(routine);
	}

	const routinesPage = {
		page: result.data.c.routines.page,
		pageSize: result.data.c.routines.pageSize,
		routines,
		totalCount: result.data.c.routines.totalCount,
	};

	if (setRoutinesPage) {
		setRoutinesPage(routinesPage);
	}
}

export async function getRoutineTypes({setRoutineTypes}) {
	const response = await liferayRequest({
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'GET',
		urlPath:
			'/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/routineType',
	});

	const result = JSON.parse(await response.text());

	const routineTypes = [];

	for (const listTypeEntry of result.listTypeEntries) {
		routineTypes.push(listTypeEntry);
	}

	if (setRoutineTypes) {
		setRoutineTypes(routineTypes);
	}
}
