/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default interface IProject {
	acWorkspaceGroupId?: string;
	accountKey?: string;
	code?: string;
	dxpVersion?: string;
	id?: number;
	maxRequestors?: number;
	name: string;
	partner?: any;
	slaCurrent?: string;
}
