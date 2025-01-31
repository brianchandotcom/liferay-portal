/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../services/liferay';

export default function getLiferaySiteName() {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);

	return `${(pathSplit.length > 2
		? pathSplit.slice(0, pathSplit.length - 1)
		: pathSplit
	).join('/')}`;
}
