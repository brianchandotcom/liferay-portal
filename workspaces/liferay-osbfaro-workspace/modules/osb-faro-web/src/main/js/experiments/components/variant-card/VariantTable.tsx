/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	getBestVariant,
	getMetricUnit,
	mergedVariants,
} from '~/experiments/util/experiments';
import {MetricName} from '~/experiments/util/types';
import Table, {Column} from '~/shared/components/table';
import {useStatefulPagination} from '~/shared/hooks/useStatefulPagination';
import {createOrderIOMap} from '~/shared/util/pagination';

import {IExperiment} from '../summary-card/types';
import columns from './variant-columns';

export const VariantTable = function VariantTable({
	experiment,
}: {
	experiment: IExperiment & {type?: string};
}) {
	const {onOrderIOMapChange, orderIOMap} = useStatefulPagination(undefined, {
		initialOrderIOMap: createOrderIOMap('dxpVariantName'),
	});

	const {
		dxpVariants,
		goal,
		metrics,
		publishedDXPVariantId,
		status,
		type,
		winnerDXPVariantId,
	} = experiment;

	const variantMetrics = metrics?.variantMetrics ?? [];
	const variants = mergedVariants(dxpVariants, variantMetrics);
	const metric = goal?.metric as MetricName | undefined;
	const metricUnit = getMetricUnit(metric as MetricName);
	const bestVariant = getBestVariant({
		dxpVariants: experiment.dxpVariants,
		goal: goal as {metric: MetricName} | undefined,
		metrics: {variantMetrics},
	});

	return (
		<div className="analytics-variant-card-table">
			<Table
				columns={
					columns({
						bestVariant,
						metric,
						metricUnit,
						publishedDXPVariantId,
						status,
						type,
						winnerDXPVariantId,
					}) as Column[]
				}
				headingNowrap={false}
				internalSort
				items={variants}
				onOrderIOMapChange={onOrderIOMapChange}
				orderIOMap={orderIOMap}
				rowIdentifier="dxpVariantId"
			/>
		</div>
	);
};
