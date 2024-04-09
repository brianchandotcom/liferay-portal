/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class Build {
	constructor({
		dateCreated,
		dateModified,
		id,
		initialBuild,
		jenkinsJobName,
		name,
		parameters,
		r_jobToBuilds_c_jobId: jobId,
		state,
	}) {
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.initialBuild = initialBuild;
		this.jenkinsJobName = jenkinsJobName;
		this.jobId = jobId;
		this.name = name;
		this.parameters = parameters;
		this.state = state;
	}

	job;
}
