/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export default function useSupportSeatsCount(userAccounts, searching) {
	const [supportSeatsCount, setSupportSeatsCount] = useState();

	useEffect(() => {
		if (!searching) {
			setSupportSeatsCount(
				userAccounts?.items.filter(
					(item) =>
						item.selectedAccountSummary.hasSupportSeatRole &&
						!item.isLiferayStaff
				).length
			);
		}
	}, [searching, userAccounts?.items]);

	return supportSeatsCount;
}
