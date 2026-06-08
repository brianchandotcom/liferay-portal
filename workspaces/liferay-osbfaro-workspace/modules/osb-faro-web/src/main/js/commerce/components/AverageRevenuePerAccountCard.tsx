/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CommerceAverageRevenuePerAccountQuery, {
	CommerceAverageRevenuePerAccountData,
} from '~/commerce/queries/AverageRevenuePerAccountQuery';

import CommerceMetricCard from './CommerceMetricCard';

const AverageRevenuePerAccountCard = () => (
	<CommerceMetricCard<CommerceAverageRevenuePerAccountData>
		Query={CommerceAverageRevenuePerAccountQuery}
		description={Liferay.Language.get(
			'total-order-value-divided-by-accounts'
		)}
		emptyTitle={Liferay.Language.get(
			'there-is-no-revenue-on-the-selected-period'
		)}
		label={Liferay.Language.get('avg-revenue-per-account')}
		mapper={(result: CommerceAverageRevenuePerAccountData) =>
			result?.orderAccountAverageCurrencyValues
		}
	/>
);

export default AverageRevenuePerAccountCard;
