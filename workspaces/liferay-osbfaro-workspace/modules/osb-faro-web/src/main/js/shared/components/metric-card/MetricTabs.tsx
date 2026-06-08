/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CardTabs from '~/shared/components/CardTabs';

import {ICommonMetricProps, useActions, useData} from './MetricBaseCard';
import MetricStateRenderer from './MetricStateRenderer';
import {useMetricQuery} from './hooks';
import {Metric} from './metrics';
import {buildTabs, getMetricCardTabsData} from './util';

interface IMetricTabsViewProps {
	activeItemIndex: number;
	changeActiveItemIndex: (index: number) => void;
	data: any;
	metrics: Metric[];
	queryName: string;
}

const MetricTabsView: React.FC<IMetricTabsViewProps> = React.memo(
	({activeItemIndex, changeActiveItemIndex, data, metrics, queryName}) => {
		const items = getMetricCardTabsData(data[queryName], metrics);

		return (
			<CardTabs
				activeTabId={activeItemIndex}
				className="analytics-metrics-tabs"
				tabs={buildTabs({
					activeItemIndex,
					items,
					onActiveItemIndexChange: changeActiveItemIndex,
				})}
			/>
		);
	}
);

const MetricTabsRenderer: React.FC<ICommonMetricProps> = ({
	experienceId,
	filters,
	interval,
	rangeSelectors,
}) => {
	const {activeItemIndex, metrics, queries, variables} = useData();
	const {changeActiveItemIndex} = useActions();

	const {data, error, loading} = useMetricQuery({
		experienceId,
		filters,
		interval,

		Query: queries.TabsQuery,
		rangeSelectors,
		variables,
	});

	return (
		<MetricStateRenderer error={error} loading={loading} spacer>
			<MetricTabsView
				activeItemIndex={activeItemIndex}
				changeActiveItemIndex={changeActiveItemIndex}
				data={data}
				metrics={metrics}
				queryName={queries.name}
			/>
		</MetricStateRenderer>
	);
};

export default MetricTabsRenderer;
