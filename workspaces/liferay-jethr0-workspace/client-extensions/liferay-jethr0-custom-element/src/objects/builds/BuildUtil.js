/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Job from '../jobs/Job';
import Build from './Build';

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
