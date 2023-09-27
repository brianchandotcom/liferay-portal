/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MDFClaimColumnKey} from '../../../common/enums/mdfClaimColumnKey';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';

export default function getMDFClaimAmountClaimedInfo(
	totalClaimAmount?: number,
	currency?: LiferayPicklist
) {
	if (totalClaimAmount) {
		return {
			[MDFClaimColumnKey.AMOUNT_CLAIMED]: getIntlNumberFormat(
				currency
			).format(totalClaimAmount),
		};
	}
}
