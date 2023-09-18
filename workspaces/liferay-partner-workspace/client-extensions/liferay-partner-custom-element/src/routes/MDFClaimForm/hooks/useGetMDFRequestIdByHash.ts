/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

export default function useGetMDFRequestIdByHash(position: number) {
	return useMemo(() => {
		const hashLocation = window.location.hash;

		return hashLocation.replace('#/', '').split('/').filter(Boolean)[
			position
		];
	}, [position]);
}
