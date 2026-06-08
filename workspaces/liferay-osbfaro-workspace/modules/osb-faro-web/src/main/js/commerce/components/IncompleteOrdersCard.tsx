/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CommerceIncompleteOrdersQuery, {
	CommerceIncompleteOrdersData,
} from '~/commerce/queries/IncompleteOrdersQuery';

import CommerceMetricCard from './CommerceMetricCard';

const IncompleteOrdersCard = () => (
	<CommerceMetricCard<CommerceIncompleteOrdersData>
		Query={CommerceIncompleteOrdersQuery}
		description={Liferay.Language.get(
			'open-order-value-minus-completed-order-value'
		)}
		emptyTitle={Liferay.Language.get(
			'there-are-no-orders-on-the-selected-period'
		)}
		label={Liferay.Language.get('incomplete-orders')}
		mapper={(result: CommerceIncompleteOrdersData) =>
			result?.orderIncompleteCurrencyValues
		}
	/>
);

export default IncompleteOrdersCard;
