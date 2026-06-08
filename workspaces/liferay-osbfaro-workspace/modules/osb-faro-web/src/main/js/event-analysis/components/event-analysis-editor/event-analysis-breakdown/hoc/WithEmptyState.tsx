/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBreakdownTableProps} from '..';
import React from 'react';

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
