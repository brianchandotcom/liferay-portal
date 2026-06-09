/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type FormatYAxisFn = (metricUnit: string) => (value: number) => string;

export type GetFormattedMedianFn = (
	median: number,
	metric: MetricName
) => string;

export type GetMetricNameFn = (metric: MetricName) => string;

export type GetMetricUnitFn = (metric: MetricName) => string;

export type GetStatusColorFn = (status: Status) => string;

export type GetStatusNameFn = (status: Status) => string;

export type GetShortIntervals = <T>(intervals: Array<T>) => Array<T>;

export type GetTicksFn = (maxValue: number) => Array<number>;

export type GetVariantLabels = (experiment: {
	bestVariant?: Variant;
	dxpVariantId: string;
	publishedDXPVariantId?: string;
	status: Status;
	winnerDXPVariantId?: string;
}) => Array<{status: string; value: string}>;

type MetricName =
	| 'BOUNCE_RATE'
	| 'CLICK_RATE'
	| 'MAX_SCROLL_DEPTH'
	| 'TIME_ON_PAGE';

type MergedVariant = {
	changes: number;
	confidenceInterval: Array<number>;
	control: boolean;
	dxpVariantId: string;
	dxpVariantName: string;
	improvement: number;
	median: number;
	probabilityToWin: number;
	trafficSplit: number;
	uniqueVisitors: number;
};

export type MergedVariantsFn = (
	variants: Array<Variant>,
	variantMetrics: Array<VariantMetric>
) => Array<MergedVariant>;

type Status =
	| 'COMPLETED'
	| 'DRAFT'
	| 'FINISHED_NO_WINNER'
	| 'FINISHED_WINNER'
	| 'PAUSED'
	| 'RUNNING'
	| 'SCHEDULED'
	| 'TERMINATED';

type Variant = {
	changes: number;
	control: boolean;
	dxpVariantId: string;
	dxpVariantName: string;
	trafficSplit: number;
	uniqueVisitors: number;
};

type VariantMetric = {
	confidenceInterval?: Array<number>;
	dxpVariantId: string;
	improvement?: number;
	median?: number;
	probabilityToWin?: number;
};

export type {MergedVariant, MetricName, Status, Variant, VariantMetric};
