/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '.';

export const storage = {
	getItem: (
		key: string,
		consentType = Liferay.Util.SessionStorage.TYPES.NECESSARY
	) => (Liferay.Util.SessionStorage as any).getItem(key, consentType),
	removeItem: (key: string) => Liferay.Util.SessionStorage.removeItem(key),
	setItem: (
		key: string,
		value: any,
		consentType = Liferay.Util.SessionStorage.TYPES.NECESSARY
	) => (Liferay.Util.SessionStorage as any).setItem(key, value, consentType),
};
