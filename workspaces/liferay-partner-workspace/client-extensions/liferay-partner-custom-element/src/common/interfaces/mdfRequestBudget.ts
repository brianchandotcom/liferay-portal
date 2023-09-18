/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';

export default interface MDFRequestBudget extends Partial<LiferayObject> {
	cost: number;
	expense: LiferayPicklist;
	removed?: boolean;
}
