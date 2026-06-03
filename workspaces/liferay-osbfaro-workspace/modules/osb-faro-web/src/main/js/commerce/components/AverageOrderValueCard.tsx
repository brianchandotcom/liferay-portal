import CommerceAverageOrderValueQuery, {
	CommerceAverageOrderValueData
} from 'commerce/queries/AverageOrderValueQuery';
import CommerceMetricCard from './CommerceMetricCard';
import React from 'react';

const AverageOrderValueCard = () => (
	<CommerceMetricCard<CommerceAverageOrderValueData>
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
		Query={CommerceAverageOrderValueQuery}
	/>
);

export default AverageOrderValueCard;
