/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class JenkinsCohort {
	constructor({dateCreated, dateModified, id, jenkinsServerCount, name}) {
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.jenkinsServerCount = jenkinsServerCount;
		this.name = name;
	}

	jenkinsServers = [];
}
