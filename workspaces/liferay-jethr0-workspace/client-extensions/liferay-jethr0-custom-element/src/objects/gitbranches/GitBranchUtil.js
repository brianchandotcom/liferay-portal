/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import GitBranch from './GitBranch';

export function getUpstreamGitBranch({id, setUpstreamGitBranch}) {
	liferayRequest({urlPath: '/o/c/gitbranches/' + id})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const gitBranch = new GitBranch(resultJSON);

			if (gitBranch && setUpstreamGitBranch) {
				setUpstreamGitBranch(gitBranch);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export function getUpstreamGitBranches({setUpstreamGitBranches}) {
	const urlSearchParams = new URLSearchParams({
		filter: "type eq 'upstream'",
	});

	liferayRequest({urlPath: '/o/c/gitbranches', urlSearchParams})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const upstreamGitBranches = [];

			resultJSON.items.forEach((item) => {
				upstreamGitBranches.push(new GitBranch(item));
			});

			if (upstreamGitBranches && setUpstreamGitBranches) {
				setUpstreamGitBranches(upstreamGitBranches);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}
