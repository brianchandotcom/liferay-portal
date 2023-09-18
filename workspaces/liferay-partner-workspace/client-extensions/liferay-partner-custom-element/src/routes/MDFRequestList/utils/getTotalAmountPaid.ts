/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimDTO from '../../../common/interfaces/dto/mdfClaimDTO';

export default function getTotalAmountPaid(mdfClaims: MDFClaimDTO[]) {
	return mdfClaims.reduce(
		(previousValue: number, currentValue: MDFClaimDTO) => {
			const sumAmount = currentValue.claimPaid
				? currentValue.claimPaid
				: 0;

			const totalAmountClaimed = previousValue + sumAmount;

			return totalAmountClaimed;
		},
		0
	);
}
