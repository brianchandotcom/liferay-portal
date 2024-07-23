/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';
import {TestrayContext} from '~/context/TestrayContext';

const usePermission = (roles: String[]) => {
	const [{myUserAccount}] = useContext(TestrayContext);

	return myUserAccount?.roleBriefs.some((role) => roles.includes(role.name));
};

export default usePermission;
