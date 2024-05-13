/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class JenkinsNode {
	constructor({
		dateCreated,
		dateModified,
		goodBattery,
		id,
		name,
		nodeCount,
		nodeRAM,
		primaryLabel,
		type,
		url,
	}) {
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.goodBattery = goodBattery;
		this.id = id;
		this.name = name;
		this.nodeCount = nodeCount;
		this.nodeRAM = nodeRAM;
		this.primaryLabel = primaryLabel;
		this.type = type;
		this.url = url;
	}

	jenkinsServer;
}
