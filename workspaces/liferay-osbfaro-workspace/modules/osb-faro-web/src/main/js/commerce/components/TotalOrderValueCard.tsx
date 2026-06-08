/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CommerceTotalOrderValueQuery, {
	CommerceTotalOrderValueData,
} from '~/commerce/queries/TotalOrderValueQuery';

import CommerceMetricCard from './CommerceMetricCard';

const TotalOrderValueCard = () => (
	<CommerceMetricCard<CommerceTotalOrderValueData>
		Query={CommerceTotalOrderValueQuery}
		description={Liferay.Language.get('value-of-placed-orders')}
		emptyTitle={Liferay.Language.get(
			'there-are-no-orders-on-the-selected-period'
		)}
		label={Liferay.Language.get('total-order-value')}
		mapper={(result: CommerceTotalOrderValueData) =>
			result?.orderTotalCurrencyValues
		}
	/>
);

export default TotalOrderValueCard;
