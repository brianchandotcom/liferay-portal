/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useParams} from 'react-router-dom';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import MetricBaseCard, {
	IGenericMetricBaseCardProps,
} from '~/shared/components/metric-card/MetricBaseCard';
import {
	BounceRateMetric,
	CompositeMetric,
	Metric,
	SessionDurationMetric,
	SessionsPerVisitorMetric,
} from '~/shared/components/metric-card/metrics';
import {
	SitesMetricQuery,
	SitesTabsQuery,
} from '~/shared/components/metric-card/queries';
import {getSiteMetricsChartData} from '~/shared/components/metric-card/util';

type TChartData = typeof getSiteMetricsChartData;

const SitesMetricCard: React.FC<IGenericMetricBaseCardProps> = (props) => {
	const {channelId} = useParams();

	const metrics: Metric[] = [
		CompositeMetric,
		SessionsPerVisitorMetric,
		SessionDurationMetric,
		BounceRateMetric,
	];

	return (
		<MetricBaseCard<TChartData>
			chartDataMapFn={getSiteMetricsChartData}
			{...props}
			metrics={metrics}
			queries={{
				MetricQuery: SitesMetricQuery,
				TabsQuery: SitesTabsQuery,

				name: 'site',
			}}
			reportContainer={ReportContainer.SiteActivityCard}
			variables={(commonVariables) => ({
				...commonVariables,
				channelId,
			})}
		/>
	);
};

export default SitesMetricCard;
