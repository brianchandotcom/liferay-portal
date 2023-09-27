/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestBudget from '../../../../../../../../../common/interfaces/mdfRequestBudget';

export default function getNewBudget(): MDFRequestBudget {
	return {
		cost: 0,
		expense: {},
	};
}
