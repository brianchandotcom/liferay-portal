import {TrendClassification} from 'segment/types';

export enum AccountMetricType {
	Active = 'activeCount',
	New = 'newCount',
	Total = 'totalCount',
}

export interface IAccountMetric extends Metric {
	metricType: AccountMetricType;
}

export type Metric = {
	trend: Trend;
	value: number;
};

export type Trend = {
	percentage: number;
	trendClassification: TrendClassification;
};
