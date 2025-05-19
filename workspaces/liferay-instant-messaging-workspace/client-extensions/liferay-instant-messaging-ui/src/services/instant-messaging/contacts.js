/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {oAuthRequest} from '../../utils/request';

export async function getContacts() {
	const endPoint = `/im/contacts`;

	return oAuthRequest({
		method: 'GET',
		url: `${endPoint}`,
	});
}
