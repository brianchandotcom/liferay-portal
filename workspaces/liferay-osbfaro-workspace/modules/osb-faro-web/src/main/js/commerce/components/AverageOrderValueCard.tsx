/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CommerceAverageOrderValueQuery, {
	CommerceAverageOrderValueData,
} from '~/commerce/queries/AverageOrderValueQuery';

import CommerceMetricCard from './CommerceMetricCard';

const AverageOrderValueCard = () => (
	<CommerceMetricCard<CommerceAverageOrderValueData>
		Query={CommerceAverageOrderValueQuery}
		description={Liferay.Language.get(
			'total-order-value-divided-by-placed-order'
		)}
		emptyTitle={Liferay.Language.get(
			'there-are-no-orders-on-the-selected-period'
		)}
		label={Liferay.Language.get('avg-order-value')}
		mapper={(result: CommerceAverageOrderValueData) =>
			result?.orderAverageCurrencyValues
		}
	/>
);

export default AverageOrderValueCard;
