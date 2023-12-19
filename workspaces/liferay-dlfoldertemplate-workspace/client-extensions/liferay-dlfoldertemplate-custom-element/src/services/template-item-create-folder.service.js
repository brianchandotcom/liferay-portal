/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../utils/constants';
import {oAuthRequest} from '../utils/request';
import {getServerUrl} from '../utils/util';

export function createFolder(templateId, containerId, rootName) {
	return oAuthRequest({
		method: 'post',
		url: `${getServerUrl()}/${
			config['folder.generate.service.url']
		}/${templateId}/${containerId}/${rootName}`,
	});
}
