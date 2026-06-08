/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import MetricBaseCard, {
	IGenericMetricBaseCardProps,
} from '~/shared/components/metric-card/MetricBaseCard';
import {useAssetVariables} from '~/shared/components/metric-card/hooks';
import {
	AvgTimeOnPageMetric,
	BounceRateMetric,
	EntrancesMetric,
	ExitRateMetric,
	Metric,
	ViewsMetric,
	VisitorsMetric,
} from '~/shared/components/metric-card/metrics';
import {
	PageMetricQuery,
	PageMetricTabsQuery,
} from '~/shared/components/metric-card/queries';
import {ICommonVariables} from '~/shared/types';

const PageMetricCard: React.FC<IGenericMetricBaseCardProps> = (props) => {
	const variables = (commonVariables: ICommonVariables) =>
		useAssetVariables(commonVariables);

	const metrics: Metric[] = [
		VisitorsMetric,
		ViewsMetric,
		BounceRateMetric,
		AvgTimeOnPageMetric,
		EntrancesMetric,
		ExitRateMetric,
	];

	return (
		<MetricBaseCard
			{...props}
			metrics={metrics}
			queries={{
				MetricQuery: PageMetricQuery,
				TabsQuery: PageMetricTabsQuery,

				name: 'page',
			}}
			reportContainer={ReportContainer.VisitorsBehaviorCard}
			variables={variables}
		/>
	);
};

export default PageMetricCard;
