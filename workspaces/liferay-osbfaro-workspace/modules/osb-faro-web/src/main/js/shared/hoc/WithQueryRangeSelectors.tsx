/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useQueryRangeSelectors} from '~/shared/hooks/useQueryRangeSelectors';

const withQueryRangeSelectors =
	() => (WrappedComponent: React.ComponentType<any>) => (props: any) => {
		const rangeSelectors = useQueryRangeSelectors();

		return <WrappedComponent {...props} rangeSelectors={rangeSelectors} />;
	};

export default withQueryRangeSelectors;
