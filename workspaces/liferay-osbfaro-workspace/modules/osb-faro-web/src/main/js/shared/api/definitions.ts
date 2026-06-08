/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RESTParams} from '~/shared/types';
import sendRequest from '~/shared/util/request';

export function searchIndividualAttributes({groupId, query}: RESTParams) {
	return sendRequest({
		data: {
			displayName: query,
		},
		method: 'GET',
		path: `main/${groupId}/definitions/individual_attributes`,
	});
}
