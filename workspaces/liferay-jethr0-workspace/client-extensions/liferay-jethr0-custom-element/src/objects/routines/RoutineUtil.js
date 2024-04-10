/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Routine from './Routine';

export async function createRoutine({data, redirect}) {
	const routinesResponse = await liferayRequest({
		body: JSON.stringify(data),
		headers: {
			'accept': 'application/json',
			'Content-Type': 'application/json',
		},
		method: 'POST',
		urlPath: '/o/c/routines',
	});

	const routinesResult = JSON.parse(await routinesResponse.text());

	if (routinesResult && redirect) {
		redirect(routinesResult);
	}
}

export async function getRoutines({setRoutines}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				routines {
					items {
						dateCreated
						dateModified
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
		routines.push(new Routine(item));
	}

	if (setRoutines) {
		setRoutines(routines);
	}
}


export async function getRoutineTypes({setRoutineTypes}) {
	const response = await liferayRequest({
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'GET',
		urlPath: '/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/routineType',
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
