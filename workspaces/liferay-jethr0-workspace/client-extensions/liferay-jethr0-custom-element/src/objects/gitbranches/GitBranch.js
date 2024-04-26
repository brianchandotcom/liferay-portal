/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const gitHubURLRegExp = new RegExp(
	'https://github.com/([^/]+)/([^/]+)/tree/([^/]+)'
);

export default class GitBranch {
	constructor({dateCreated, dateModified, id, latestSHA, type, url}) {
		this.latestSHA = latestSHA;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.type = type;
		this.url = url;

		const gitHubURLMatch = this.url.match(gitHubURLRegExp);

		this.name = gitHubURLMatch[3];
		this.repositoryName = gitHubURLMatch[2];
		this.userName = gitHubURLMatch[1];
	}
}
