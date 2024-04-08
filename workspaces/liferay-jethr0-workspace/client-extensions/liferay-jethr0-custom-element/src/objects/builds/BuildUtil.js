/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Build from './Build';

export async function getBuildById({id, setBuild}) {
	const response = await liferayRequest({urlPath: '/o/c/builds/' + id});

	const results = JSON.stringify(await response.text());

	const build = new Build(results);

	if (build && setBuild) {
		setBuild(build);
	}
}

export async function getBuildsByJob({job, setBuilds}) {
	const urlSearchParams = new URLSearchParams({
		filter: "r_jobToBuilds_c_jobId eq '" + job.id + "'",
	});

	const response = await liferayRequest({urlPath: '/o/c/builds', urlSearchParams});

	const result = JSON.parse(await response.text());

	const builds = [];

	result.items.forEach((item) => {
		builds.push(new Build(item));
	});

	if (builds && setBuilds) {
		setBuilds(builds);
	}
}
