/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import GitBranch from './GitBranch';

export async function getUpstreamGitBranch({id, setUpstreamGitBranch}) {
	const response = await liferayRequest({urlPath: '/o/c/gitbranches/' + id});

	const result = JSON.parse(await response.text());

	const gitBranch = new GitBranch(result);

	if (gitBranch && setUpstreamGitBranch) {
		setUpstreamGitBranch(gitBranch);
	}
}

export async function getUpstreamGitBranches({setUpstreamGitBranches}) {
	const urlSearchParams = new URLSearchParams({
		filter: "type eq 'upstream'",
	});

	const response = await liferayRequest({
		urlPath: '/o/c/gitbranches',
		urlSearchParams,
	});

	const result = JSON.parse(await response.text());

	const upstreamGitBranches = [];

	result.items.forEach((item) => {
		upstreamGitBranches.push(new GitBranch(item));
	});

	if (upstreamGitBranches && setUpstreamGitBranches) {
		setUpstreamGitBranches(upstreamGitBranches);
	}
}
