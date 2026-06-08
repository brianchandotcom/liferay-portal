/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {find, get} from 'lodash';
import React, {useMemo} from 'react';
import ChartTooltip, {
	Alignments,
	Weights,
} from '~/shared/components/chart-tooltip';
import {Interval, RangeSelectors} from '~/shared/types';
import {getDateTitle} from '~/shared/util/charts';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';

import {useData} from './MetricBaseCard';
import {
	CHART_DATA_ID_1,
	CHART_DATA_ID_2,
	CHART_DATA_PREVIOUS,
	METRIC_TOOLTIP_LABEL_MAP,
	getActiveItem,
	getPreviousValueFromCompositeData,
} from './util';

type TTooltipPayloadItem = {
	dataKey?: string;
	payload: {date: number};
	value: number;
};

const useMetricTooltipRows = ({
	data,
	interval,
	payload,
	rangeSelectors,
}: {
	data: any;
	interval: Interval;
	payload: TTooltipPayloadItem[] | undefined;
	rangeSelectors: RangeSelectors;
}) => {
	const {compareToPrevious} = useData();

	const activeItem = useMemo(
		() => getActiveItem(data, compareToPrevious),
		[compareToPrevious, data]
	);

	return useMemo(() => {
		if (!payload?.length) {
			return null;
		}

		const {
			asymmetricComparison,
			chartData,
			compositeData,
			content: {name, title},
			dateKeysIMap,
			format,
			prevDateKeysIMap,
		} = activeItem;

		const showCurrentPeriod =
			compareToPrevious && asymmetricComparison
				? payload.length > 1
				: true;

		const dateKey = payload[0].payload.date;

		const dataOneItemData = find(
			chartData,
			({id}: {id: string}) => id === CHART_DATA_ID_1
		);
		const dataOneValue = payload[0].value;

		const dataTwoItemData = find(
			chartData,
			({id}: {id: string}) => id === CHART_DATA_ID_2
		);
		const dataTwoValue = payload[1] && payload[1].value;

		const dataPreviousPoint = find(
			payload,
			({dataKey}) => dataKey === CHART_DATA_PREVIOUS
		);

		const dataOnePreviousValue = compositeData
			? getPreviousValueFromCompositeData(
					compositeData,
					get(dataOneItemData, 'dataName'),
					dateKey
				)
			: get(dataPreviousPoint, 'value');
		const dataTwoPreviousValue = getPreviousValueFromCompositeData(
			compositeData,
			get(dataTwoItemData, 'dataName'),
			dateKey
		);

		const currentPeriodTitle = getDateTitle(
			dateKeysIMap.get(dateKey),
			rangeSelectors.rangeKey,
			interval
		);
		const previousPeriodTitle = getDateTitle(
			prevDateKeysIMap.get(dateKey),
			rangeSelectors.rangeKey,
			interval
		);

		const getDataRowName = (itemData: unknown) =>
			get(itemData, 'tooltipTitle') ||
			METRIC_TOOLTIP_LABEL_MAP[name] ||
			get(itemData, 'name');

		const header = [
			{
				columns: [
					{label: title, weight: Weights.Semibold, width: 116},
					compareToPrevious && {
						align: Alignments.Right,
						label: previousPeriodTitle,
						width: 55,
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: currentPeriodTitle,
						width: 55,
					},
				].filter(Boolean),
			},
		];

		const rows = [
			{
				columns: [
					{label: getDataRowName(dataOneItemData)},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(dataOnePreviousValue),
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataOneValue),
					},
				].filter(Boolean),
			},
			compositeData && {
				columns: [
					{label: getDataRowName(dataTwoItemData)},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(dataTwoPreviousValue),
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataTwoValue),
					},
				].filter(Boolean),
			},
			compositeData && {
				columns: [
					{label: Liferay.Language.get('total')},
					compareToPrevious && {
						align: Alignments.Right,
						label: format(
							dataOnePreviousValue + dataTwoPreviousValue
						),
					},
					showCurrentPeriod && {
						align: Alignments.Right,
						label: format(dataOneValue + dataTwoValue),
					},
				].filter(Boolean),
			},
		].filter(Boolean);

		return {header, rows};
	}, [activeItem, compareToPrevious, interval, payload, rangeSelectors]);
};

interface IMetricTooltipProps {
	active?: boolean;
	compareToPrevious: boolean;
	data: any;
	interval: Interval;
	payload?: TTooltipPayloadItem[];
	rangeSelectors: RangeSelectors;
	retentionPeriod?: number;
}

const MetricTooltip: React.FC<IMetricTooltipProps> = ({
	active,
	compareToPrevious,
	data,
	interval,
	payload,
	rangeSelectors,
	retentionPeriod,
}) => {
	const tooltipRows = useMetricTooltipRows({
		data,
		interval,
		payload,
		rangeSelectors,
	});

	if (!active || !tooltipRows) {
		return null;
	}

	const showRetentionWarning =
		(compareToPrevious &&
			rangeSelectors.rangeKey === RangeKeyTimeRanges.Last180Days) ||
		(retentionPeriod === 13 &&
			rangeSelectors.rangeKey === RangeKeyTimeRanges.LastYear);

	const description = showRetentionWarning
		? (sub(
				Liferay.Language.get(
					'there-is-no-data-available-for-dates-prior-to-x-months-due-to-your-workspaces-data-retention-period'
				),
				[retentionPeriod]
			) as string)
		: '';

	return (
		<div
			className="bb-tooltip-container"
			style={{maxWidth: 400, position: 'static'}}
		>
			<ChartTooltip
				description={description}
				header={tooltipRows.header}
				rows={tooltipRows.rows}
			/>
		</div>
	);
};

export default MetricTooltip;
