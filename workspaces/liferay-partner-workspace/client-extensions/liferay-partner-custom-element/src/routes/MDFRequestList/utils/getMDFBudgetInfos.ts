/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';

const APPROVED_STATUS = 'approved';
export default function getMDFBudgetInfos(
	totalCostOfExpense?: number,
	totalRequested?: number,
	currency?: LiferayPicklist,
	requestStatus?: string
) {
	if (totalCostOfExpense && totalRequested) {
		return {
			[MDFColumnKey.TOTAL_COST]: getIntlNumberFormat(currency).format(
				totalCostOfExpense
			),
			[MDFColumnKey.REQUESTED]: getIntlNumberFormat(currency).format(
				totalRequested
			),
			[MDFColumnKey.APPROVED]:
				requestStatus === APPROVED_STATUS
					? getIntlNumberFormat(currency).format(totalRequested)
					: '-',
		};
	}
}
