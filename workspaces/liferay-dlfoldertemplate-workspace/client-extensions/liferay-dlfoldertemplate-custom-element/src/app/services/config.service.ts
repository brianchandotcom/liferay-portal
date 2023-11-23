/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Injectable} from '@angular/core';

@Injectable({
	providedIn: 'root',
})
export class ConfigService {
	constructor() {}
	public configObject = {
		'backend.project.id': 'liferay-data-import-export-services',
		'dataimporter.service.end.point': '/o/c/dataimporters/',
		'folder.generate.service.url': '/jobs/create/folder/direct',
		'folder.template.service.end.point': '/o/c/foldertemplates/',
		'jobs.attach.service.url': '/jobs/upload/',
		'jobs.status.service.url': '/jobs/status/',
	};
}
