import ClayLayout from '@clayui/layout';
import MetricCard from 'shared/components/MetricCard';
import React from 'react';
import {IOverviewMetric, OverviewMetricType} from '../utils/types';
import {SectionHeader} from 'shared/components/SectionHeader';
import {sub} from 'shared/util/lang';

interface IOverviewSectionProps {
	loading?: boolean;
	metrics?: IOverviewMetric[];
}

const renderTrendLabel = (percentageNode: React.ReactNode) =>
	sub(Liferay.Language.get('x-vs-last-x-days'), [percentageNode, 90], false);

const OverviewSection: React.FC<IOverviewSectionProps> = ({
	loading = false,
	metrics,
}) => {
	const getMetric = (metricType: OverviewMetricType) =>
		metrics?.find((metric) => metric.metricType === metricType);

	const atRiskAccounts = getMetric(OverviewMetricType.AtRisk);
	const netNewPipelineGenerated = getMetric(OverviewMetricType.NewPipeline);
	const stalledAccounts = getMetric(OverviewMetricType.Stalled);

	const cards = [
		{
			description: Liferay.Language.get(
				'total-new-accounts-that-entered-the-pipeline-stage-within-the-selected-timeframe,-excluding-cross-sells,-upsells,-and-renewals.'
			),
			metric: netNewPipelineGenerated,
			title: Liferay.Language.get('net-new-pipeline-generated'),
		},
		{
			description: Liferay.Language.get(
				'the-total-number-of-accounts-specifically-stuck-in-the-engaged-stage-that-have-exceeded-their-designated-time-in-stage-threshold-90-days'
			),
			metric: stalledAccounts,
			title: Liferay.Language.get('stalled-accounts'),
		},
		{
			description: Liferay.Language.get(
				'accounts-with-decreasing-product-usage-or-signs-of-churn-risk.-action-is-required'
			),
			metric: atRiskAccounts,
			title: Liferay.Language.get('at-risk-accounts'),
		},
	];

	return (
		<>
			<SectionHeader
				icon="box-container"
				title={Liferay.Language.get('overview')}
			/>

			<ClayLayout.Row className="row g-4">
				{cards.map(({description, metric, title}) => (
					<ClayLayout.Col key={title} lg={4} md={12}>
						<MetricCard
							description={description}
							loading={loading}
							minHeight={200}
							renderTrendLabel={renderTrendLabel}
							title={title}
							trend={metric?.trend}
							trendClassName="text-lowercase"
							value={metric?.value ?? 0}
						/>
					</ClayLayout.Col>
				))}
			</ClayLayout.Row>
		</>
	);
};

export default OverviewSection;
