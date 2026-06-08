/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';

const withQueryPagination =
	(initialParams: Parameters<typeof useQueryPagination>[0]) =>
	(WrappedComponent: React.ComponentType<any>) =>
	(props: any) => {
		const paginationParams = useQueryPagination(initialParams);

		return <WrappedComponent {...props} {...paginationParams} />;
	};

export default withQueryPagination;
