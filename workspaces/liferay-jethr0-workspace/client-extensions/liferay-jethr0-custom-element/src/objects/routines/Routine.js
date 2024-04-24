/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class Routine {
	constructor({
		cron,
		dateCreated,
		dateModified,
		id,
		jobName,
		jobParameters,
		jobPriority,
		jobType,
		name,
		type,
	}) {
		this.cron = cron;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.name = name;
		this.jobName = jobName;
		this.jobParameters = jobParameters;
		this.jobPriority = jobPriority;
		this.jobType = jobType;
		this.type = type;
	}

	upstreamGitBranch;
	jobs = [];
}
