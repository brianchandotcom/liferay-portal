/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';

const WithCurrentUser = function WithCurrentUser<P extends object>(
	Component: React.ComponentType<P>
) {
	return (props: P) => {
		const currentUser = useCurrentUser();

		return <Component {...props} currentUser={currentUser} />;
	};
};

/**
 * CurrentUser HOC
 * @deprecated Use useCurrentUser Hook for functional components.
 */
export default WithCurrentUser;
