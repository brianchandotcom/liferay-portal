/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class JenkinsServer {
	constructor({dateCreated, dateModified, id, jenkinsNodeCount, name, url}) {
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.jenkinsNodeCount = jenkinsNodeCount;
		this.name = name;
		this.url = url;
	}

	jenkinsCohort;
}
