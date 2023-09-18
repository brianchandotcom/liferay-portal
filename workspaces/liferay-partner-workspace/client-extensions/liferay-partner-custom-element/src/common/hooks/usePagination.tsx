/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

export default function usePagination() {
	const [activeDelta, setActiveDelta] = useState<number>(10);
	const [activePage, setActivePage] = useState<number>(1);

	return {
		activeDelta,
		activePage,
		onDeltaChange: setActiveDelta,
		onPageChange: setActivePage,
	};
}
