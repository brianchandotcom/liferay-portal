/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class BuildRun {
	constructor({
		dateCreated,
		dateModified,
		duration,
		id,
		jenkinsBuildURL,
		result,
		state,
	}) {
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.duration = duration;
		this.id = id;
		this.jenkinsBuildURL = jenkinsBuildURL;
		this.result = result;
		this.state = state;
	}
}
