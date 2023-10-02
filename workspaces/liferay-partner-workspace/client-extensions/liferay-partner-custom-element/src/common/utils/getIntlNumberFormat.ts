/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayPicklist from '../interfaces/liferayPicklist';
import {Liferay} from '../services/liferay';

export default function getIntlNumberFormat(currency?: LiferayPicklist) {
	return new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: currency?.key || 'USD',
		style: 'currency',
	});
}
