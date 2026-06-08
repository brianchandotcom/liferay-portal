/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	getLegendData,
	getMedianGraphData,
	getMetricUnit,
	mergedVariants,
} from '~/experiments/util/experiments';
import {MetricName} from '~/experiments/util/types';
import HTMLBarChart, {
	IHTMLBarChartProps,
} from '~/shared/components/HTMLBarChart';
import Legend from '~/shared/components/Legend';

import {IExperiment} from '../summary-card/types';

export const MediansChart = function MediansChart({
	experiment,
}: {
	experiment: IExperiment;
}) {
	const {dxpVariants, goal, metrics} = experiment;

	const variantMetrics = metrics?.variantMetrics ?? [];

	const variants = mergedVariants(dxpVariants, variantMetrics);
	const metricUnit = getMetricUnit(goal?.metric as MetricName);
	const mediansData = getMedianGraphData({
		dxpVariants: variants,
		metricUnit,
	});

	return (
		<>
			<HTMLBarChart {...(mediansData as unknown as IHTMLBarChartProps)} />

			<Legend
				data={getLegendData(variants).map((item) => ({
					...item,
					color: item.color ?? '',
				}))}
			/>
		</>
	);
};
