/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../liferay/liferay';

export const cloudConsoleURLs = {
	getProjectServices: (cloudConsoleURL: string, projectId: string) =>
		`${cloudConsoleURL}/projects/${projectId}/services`,
};

export function openLink(link: string) {
	if (link.startsWith(window.location.origin) || !link.startsWith('http')) {
		return Liferay.Util.navigate(link);
	}

	window.open(link);
}
