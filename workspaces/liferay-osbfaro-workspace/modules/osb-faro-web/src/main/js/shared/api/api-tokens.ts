/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RESTParams} from '~/shared/types';
import sendRequest from '~/shared/util/request';

export function generate({
	expiresIn,
	groupId,
}: RESTParams & {expiresIn: string}) {
	return sendRequest({
		method: 'POST',
		path: `main/${groupId}/oauth2/tokens/new?expiresIn=${expiresIn}`,
	});
}

export function search({groupId}: RESTParams) {
	return sendRequest({
		method: 'GET',
		path: `main/${groupId}/oauth2/tokens`,
	});
}

export function revoke({groupId, token}: RESTParams & {token: string}) {
	return sendRequest({
		method: 'POST',
		path: `main/${groupId}/oauth2/tokens/${token}/revoke`,
	});
}
