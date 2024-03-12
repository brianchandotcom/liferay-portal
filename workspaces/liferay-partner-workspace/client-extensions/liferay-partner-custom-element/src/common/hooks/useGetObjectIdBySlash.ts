/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

const ID_POSITION = 1;

export default function useGetObjectIdBySlash() {
	return useMemo(() => {
		const hrefLocation = window.location.pathname;

		return hrefLocation.split('/l/')[ID_POSITION].split('?')[0];
	}, []);
}
