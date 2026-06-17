import React from 'react';
import {IBreakdownTableProps} from '..';

const WithEmptyState =
	(Component: React.FC<IBreakdownTableProps>) =>
	({event, ...otherProps}: Partial<IBreakdownTableProps>) => {
		if (!event) {
			return (
				<div className="breakdown-empty">
					{Liferay.Language.get('add-an-event-to-analyze')}
				</div>
			);
		}

		return (
			<Component
				{...(otherProps as IBreakdownTableProps)}
				event={event}
			/>
		);
	};

export default WithEmptyState;
