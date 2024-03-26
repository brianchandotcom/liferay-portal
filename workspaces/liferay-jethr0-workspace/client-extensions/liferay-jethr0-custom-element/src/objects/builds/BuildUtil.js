/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import Build from './Build';

export function getBuildById({id, setBuild}) {
	liferayRequest({urlPath: '/o/c/builds/' + id})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const build = new Build(resultJSON);

			if (build && setBuild) {
				setBuild(build);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getBuildsByJob({jobId, setBuilds}) {
	const urlSearchParams = new URLSearchParams({
		filter: "r_jobToBuilds_c_jobId eq '" + jobId + "'",
	});

	liferayRequest({urlPath: '/o/c/builds', urlSearchParams})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const builds = [];

			resultJSON.items.forEach((item) => {
				builds.push(new Build(item));
			});

			if (builds && setBuilds) {
				setBuilds(builds);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}
