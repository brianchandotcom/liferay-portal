/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import BuildRun from './BuildRun';

export function getBuildRunById({id, setBuildRun}) {
	liferayRequest({urlPath: '/o/c/buildruns/' + id})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const buildRun = new BuildRun(resultJSON);

			if (buildRun && setBuildRun) {
				setBuildRun(buildRun);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getBuildRunsByBuildId({buildId, setBuildRuns}) {
	const urlSearchParams = new URLSearchParams({
		filter: "r_buildToBuildRuns_c_buildId eq '" + buildId + "'",
	});

	liferayRequest({urlPath: '/o/c/buildruns', urlSearchParams})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const buildRuns = [];

			resultJSON.items.forEach((item) => {
				buildRuns.push(new BuildRun(item));
			});

			if (buildRuns && setBuildRuns) {
				setBuildRuns(buildRuns);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}
