/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

import NoResultsDisplay from './NoResultsDisplay';

interface IComposedChartWithEmptyStateProps
	extends React.HTMLAttributes<HTMLElement> {
	emptyDescription: string | React.ReactElement;
	emptyTitle: string;
	showEmptyState?: boolean;
}

const ComposedChartWithEmptyState: React.FC<
	IComposedChartWithEmptyStateProps
> = ({children, emptyDescription, emptyTitle, showEmptyState = false}) => (
	<div
		className={getCN('composed-chart-with-empty-state', {
			'composed-chart-with-empty-state--show': showEmptyState,
		})}
	>
		{children}

		{showEmptyState && (
			<NoResultsDisplay
				description={emptyDescription}
				title={emptyTitle}
			/>
		)}
	</div>
);

export default ComposedChartWithEmptyState;
