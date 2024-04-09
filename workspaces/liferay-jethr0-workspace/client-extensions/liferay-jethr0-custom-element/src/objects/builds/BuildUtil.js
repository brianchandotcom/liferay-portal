/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Build from './Build';
import Job from '../jobs/Job';

export async function getBuildById({id, setBuild}) {
	const response = await liferayRequest({
		graphqlQuery: `{
			c {
				builds(filter: \\"id eq '${id}'\\") {
					items {
						dateCreated
						dateModified
						id
						initialBuild
						jenkinsJobName
						jobToBuilds
						name
						parameters
						state {
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

	for (const buildJSON of result.data.c.builds.items) {
		const build = new Build(buildJSON);

		build.job = new Job(buildJSON.jobToBuilds);

		if (build) {
			if (setBuild) {
				setBuild(build);
			}

			return build;
		}
	}
}

export async function getBuildsByJob({job, setBuilds}) {
	const urlSearchParams = new URLSearchParams({
		filter: "r_jobToBuilds_c_jobId eq '" + job.id + "'",
	});

	const response = await liferayRequest({
		urlPath: '/o/c/builds',
		urlSearchParams,
	});

	const result = JSON.parse(await response.text());

	const builds = [];

	result.items.forEach((item) => {
		builds.push(new Build(item));
	});

	if (builds && setBuilds) {
		setBuilds(builds);
	}
}
