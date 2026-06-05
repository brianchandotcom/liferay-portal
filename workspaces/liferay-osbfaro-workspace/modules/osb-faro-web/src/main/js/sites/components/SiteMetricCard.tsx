import MetricBaseCard, {
	IGenericMetricBaseCardProps
} from 'shared/components/metric-card/MetricBaseCard';
import React from 'react';
import {
	BounceRateMetric,
	CompositeMetric,
	Metric,
	SessionDurationMetric,
	SessionsPerVisitorMetric
} from 'shared/components/metric-card/metrics';
import {getSiteMetricsChartData} from 'shared/components/metric-card/util';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {
	SitesMetricQuery,
	SitesTabsQuery
} from 'shared/components/metric-card/queries';
import {useParams} from 'react-router-dom';

type TChartData = typeof getSiteMetricsChartData;

const SitesMetricCard: React.FC<IGenericMetricBaseCardProps> = props => {
	const {channelId} = useParams();

	const metrics: Metric[] = [
		CompositeMetric,
		SessionsPerVisitorMetric,
		SessionDurationMetric,
		BounceRateMetric
	];

	return (
		<MetricBaseCard<TChartData>
			chartDataMapFn={getSiteMetricsChartData}
			{...props}
			metrics={metrics}
			queries={{
				MetricQuery: SitesMetricQuery,
				name: 'site',
				TabsQuery: SitesTabsQuery
			}}
			reportContainer={ReportContainer.SiteActivityCard}
			variables={commonVariables => ({
				...commonVariables,
				channelId
			})}
		/>
	);
};

export default SitesMetricCard;
