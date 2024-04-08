/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import BuildRun from './BuildRun';

export async function getBuildRunById({id, setBuildRun}) {
	const response = await liferayRequest({urlPath: '/o/c/buildruns/' + id});

	const result = JSON.parse(await response.text());

	const buildRun = new BuildRun(result);

	if (buildRun && setBuildRun) {
		setBuildRun(buildRun);
	}
}

export async function getBuildRunsByBuildId({buildId, setBuildRuns}) {
	const urlSearchParams = new URLSearchParams({
		filter: "r_buildToBuildRuns_c_buildId eq '" + buildId + "'",
	});

	const response = await liferayRequest({
		urlPath: '/o/c/buildruns',
		urlSearchParams,
	});

	const result = JSON.parse(await response.text());

	const buildRuns = [];

	result.items.forEach((item) => {
		buildRuns.push(new BuildRun(item));
	});

	if (buildRuns && setBuildRuns) {
		setBuildRuns(buildRuns);
	}
}
