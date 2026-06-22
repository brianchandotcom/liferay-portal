import CommerceIncompleteOrdersQuery, {
	CommerceIncompleteOrdersData
} from 'commerce/queries/IncompleteOrdersQuery';
import CommerceMetricCard from './CommerceMetricCard';
import React from 'react';

const IncompleteOrdersCard = () => (
	<CommerceMetricCard<CommerceIncompleteOrdersData>
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
		Query={CommerceIncompleteOrdersQuery}
	/>
);

export default IncompleteOrdersCard;
