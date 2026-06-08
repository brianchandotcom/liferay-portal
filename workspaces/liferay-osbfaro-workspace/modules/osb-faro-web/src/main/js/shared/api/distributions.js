/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldContexts} from '~/shared/util/constants';
import sendRequest from '~/shared/util/request';

export function fetch(params) {
	const {
		context = FieldContexts.Demographics,
		groupId,
		...otherParams
	} = params;

	const entityType =
		context === FieldContexts.Demographics ? 'individual' : 'account';

	return sendRequest({
		data: {
			...otherParams,
		},
		method: 'GET',
		path: `contacts/${groupId}/${entityType}/distribution`,
	});
}
