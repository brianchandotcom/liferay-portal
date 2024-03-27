/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default class JobDefinitionParameter {
	constructor({
		dateCreated,
		dateModified,
		id,
		key,
		label,
		type,
		valueDefault,
		valueDescription,
		valueRegex,
	}) {
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.id = id;
		this.key = key;
		this.label = label;
		this.valueDefault = valueDefault;
		this.valueDescription = valueDescription;
		this.valueRegex = valueRegex;
		this.type = type;
	}
}
