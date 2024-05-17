/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class Job {
	constructor({
		blessed,
		dateCreated,
		dateModified,
		id,
		name,
		parameters,
		priority,
		startDate,
		state,
		type,
	}) {
		this.blessed = blessed;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.name = name;
		this.parameters = parameters;
		this.priority = priority;
		this.startDate = startDate;
		this.state = state;
		this.type = type;
	}

	builds = [];
	routine;
}
