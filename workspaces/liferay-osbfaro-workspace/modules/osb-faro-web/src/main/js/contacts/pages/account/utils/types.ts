/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TrendClassification} from '~/segment/types';

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
