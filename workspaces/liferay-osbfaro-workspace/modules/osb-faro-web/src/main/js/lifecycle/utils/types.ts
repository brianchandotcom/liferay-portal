/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LifecycleStages} from '../../contacts/pages/account/utils/constants';
import {Metric} from '../../contacts/pages/account/utils/types';

export enum OverviewMetricType {
	AtRisk = 'at-risk',
	NewPipeline = 'new-pipeline',
	Stalled = 'stalled',
}

export interface IOverviewMetric extends Metric {
	metricType: OverviewMetricType;
}

export interface ILifecycleStage {
	accountCount: number;
	averageStageDuration: number;
	conversionRateToNextStage: number | null;
	description: string;
	percentage: number;
	stageType: LifecycleStages;
}
