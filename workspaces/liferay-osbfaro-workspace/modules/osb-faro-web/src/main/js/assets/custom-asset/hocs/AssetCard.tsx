/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DocumentNode} from '@apollo/client';
import {graphql} from '@apollo/client/react/hoc';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useCallback, useState} from 'react';
import {compose} from 'redux';
import getMetricsMapper from '~/cerebro-shared/hocs/mappers/metrics';
import {withEmpty, withError} from '~/cerebro-shared/hocs/utils';
import Card from '~/shared/components/Card';
import BaseCard from '~/shared/components/base-card';
import {MetricChart} from '~/shared/components/metric-card/MetricChart';
import {withLoading} from '~/shared/hoc';
import {RangeSelectors, Router} from '~/shared/types';
import {ASSET_METRICS} from '~/shared/util/constants';

const CHARTS = {
	line: {
		component: MetricChart,
		mapper: getMetricsMapper,
	},
};

interface IAssetComponent extends IChartProps {
	assetId: string;
	router: Router;
	showTabs: boolean;
}

interface IChartProps {
	chartHeight: number;
	handleShowPreviousChanged: (newVal: any) => void;
	id: number;
	items?: Object[];
	onRemoveAsset: (id: number) => void;
	panel: {chartType: string};
	rangeSelectors: RangeSelectors;
	showPrevious: boolean;
}

const Chart: React.FC<IChartProps> = ({
	chartHeight,
	handleShowPreviousChanged,
	id,
	items,
	onRemoveAsset,
	panel: {chartType},
	rangeSelectors,
	showPrevious,
}) => {
	const ChartComponent = (CHARTS as any)[chartType].component;

	return (
		<>
			<ChartComponent
				chartHeight={chartHeight}
				compareToPrevious={showPrevious}
				data={items && items[0]}
				onCompareToPreviousChange={handleShowPreviousChanged}
				rangeSelectors={rangeSelectors}
			/>

			<div className="d-flex justify-content-end">
				<ClayButton
					aria-label={Liferay.Language.get('delete')}
					borderless
					className="button-root"
					displayType="secondary"
					onClick={() => onRemoveAsset(id)}
					size="sm"
				>
					<ClayIcon className="icon-root" symbol="trash" />
				</ClayButton>
			</div>
		</>
	);
};

const getMetric = (name: any) => {
	const metric = ASSET_METRICS.find(({key}) => key === name);

	return [{name, title: metric?.selectTitle, type: metric?.type}];
};

const getMapper = ({chartType, metric}: any) => {
	const mapper = (CHARTS as any)[chartType].mapper;

	if (chartType === 'line') {
		return mapper(({custom}: any) => custom, getMetric(metric));
	}

	return mapper(({custom}: any) => custom);
};

interface IAssetCardProps extends React.HTMLAttributes<HTMLElement> {
	assetId: string;
	itemQuery: DocumentNode;
	label: string;
	legacyDropdownRangeKey: boolean;
	onRemoveAsset: () => void;
	panel: {
		chartType: string;
		metric: string;
	};
	rangeSelector?: RangeSelectors;
	router: Router;
}

const AssetCard: React.FC<IAssetCardProps> = ({
	assetId,
	className = 'analytics-custom-metrics-card',
	id,
	itemQuery,
	label,
	legacyDropdownRangeKey,
	onRemoveAsset,
	panel,
}) => {
	const AssetComponent = compose(
		graphql(itemQuery, getMapper(panel)),
		withLoading(),
		withError(),
		withEmpty()
	)(Chart) as unknown as React.FC<IAssetComponent>;

	const [showPrevious, setShowPrevious] = useState(false);

	const handleShowPreviousChanged = useCallback(
		(newVal: any) => setShowPrevious(newVal),
		[]
	);

	return (
		<BaseCard
			className={className}
			label={label}
			legacyDropdownRangeKey={legacyDropdownRangeKey}
			minHeight={536}
		>
			{({rangeSelectors, router}) => (
				<Card.Body>
					<AssetComponent
						assetId={assetId}
						chartHeight={416}
						handleShowPreviousChanged={handleShowPreviousChanged}
						id={Number(id)}
						onRemoveAsset={onRemoveAsset}
						panel={panel}
						rangeSelectors={rangeSelectors}
						router={router}
						showPrevious={showPrevious}
						showTabs={false}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);
};

export default AssetCard;
