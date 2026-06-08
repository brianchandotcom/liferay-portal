/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {useParams} from 'react-router-dom';
import {
	METRICS_TEXT,
	METRICS_TITLES,
	Metric,
	TrendClassification,
} from '~/segment/types';
import {addAlert} from '~/shared/actions/alerts';
import {fetchMembershipMetrics} from '~/shared/api/individual-segment';
import Card from '~/shared/components/Card';
import Loading from '~/shared/components/Loading';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {useRequest} from '~/shared/hooks/useRequest';
import {Alert} from '~/shared/types';
import {
	convertMillisecondsToDays,
	convertMillisecondsToHours,
} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {getIcon, getStatsColor, getTrendSign} from '~/shared/util/metrics';
import {toRounded, toThousands} from '~/shared/util/numbers';
import {getPercentage} from '~/shared/util/util';

const connector = connect(null, {
	addAlert,
});

type PropsFromRedux = ConnectedProps<typeof connector>;
interface ICardSectionProps {
	data: Metric;
	description: string;
	loading: boolean;
	title: string;
	totalComparisonValue?: number;
	trendComparison?: number;
}

export const CardSection = function CardSection({
	data,
	description,
	loading,
	title,
	totalComparisonValue,
	trendComparison,
}: ICardSectionProps) {
	const isAverageSegmentMetric =
		title === METRICS_TITLES.averageSegmentMembershipDurationMetric;

	const rawValue = data?.value || 0;

	let displayValue = '';

	if (isAverageSegmentMetric) {
		const days = convertMillisecondsToDays(rawValue);

		const hours = convertMillisecondsToHours(rawValue);

		const daysLanguageKey = sub(
			days === 1
				? Liferay.Language.get('x-day').toLowerCase()
				: Liferay.Language.get('x-days').toLowerCase(),
			[days]
		);

		const hoursLanguageKey = sub(
			hours === 1
				? Liferay.Language.get('x-hour').toLowerCase()
				: Liferay.Language.get('x-hours').toLowerCase(),
			[hours]
		);

		displayValue =
			days >= 1 || rawValue === 0
				? (daysLanguageKey as string)
				: (hoursLanguageKey as string);
	}
	else {
		displayValue = sub(
			rawValue === 1
				? Liferay.Language.get('x-member').toLowerCase()
				: Liferay.Language.get('x-members').toLowerCase(),
			[toThousands(rawValue)]
		) as string;
	}

	const previousDurationDays = convertMillisecondsToDays(
		data?.previousValue ?? 0
	);

	const previousValueComparison =
		((data?.previousValue ?? 0) * (trendComparison ?? 0)) / 100;

	return (
		<Card.Body className="card-section my-2 py-2 type-trend-root">
			{loading && <Loading />}

			{!loading && (
				<>
					<div className="align-items-center d-flex justify-content-between">
						<div className="align-items-center d-flex">
							<Card.Title>{title}</Card.Title>

							<ClayPopover
								alignPosition="top"
								closeOnClickOutside
								header={title}
								trigger={
									<ClayButtonWithIcon
										displayType="unstyled"
										size="xs"
										symbol="question-circle-full"
									/>
								}
							>
								{description}
							</ClayPopover>
						</div>

						<span className="font-weight-semibold text-secondary text-uppercase">
							{title ===
								METRICS_TITLES.averageSegmentMembershipDurationMetric ||
							title === METRICS_TITLES.totalMembersMetric
								? Liferay.Language.get('last-30-days')
								: Liferay.Language.get('last-24-hours')}
						</span>
					</div>

					<h2 className="my-3 text-secondary">{displayValue}</h2>

					<div className="d-flex flex-row">
						{!isAverageSegmentMetric && (
							<>
								<span className="mr-1 text-secondary">
									<span>
										{sub(
											title ===
												METRICS_TITLES.totalMembersMetric
												? Liferay.Language.get(
														'x-percent-of-all-individuals'
													)
												: Liferay.Language.get(
														'x-percent-of-all-members'
													),
											[
												toRounded(
													getPercentage(
														data?.value,
														totalComparisonValue
													),
													2
												),
											]
										)}
									</span>
								</span>

								<span className="mx-3 text-secondary">|</span>
							</>
						)}

						<>
							{isAverageSegmentMetric && (
								<span className="mr-1 text-3 text-secondary">
									<span
										style={{
											color: getStatsColor(
												data?.trend?.trendClassification
											),
										}}
									>
										{getTrendSign(previousValueComparison)}
									</span>

									{sub(
										Liferay.Language.get(
											'x-vs-last-x-days'
										),
										[
											<span
												className="mr-1"
												key="previousDays"
												style={{
													color: getStatsColor(
														data?.trend
															?.trendClassification
													),
												}}
											>
												{sub(
													previousDurationDays === 1
														? Liferay.Language.get(
																'x-day'
															).toLowerCase()
														: Liferay.Language.get(
																'x-days'
															).toLowerCase(),
													[previousDurationDays]
												)}
											</span>,
											30,
										],
										false
									)}
								</span>
							)}

							<span className="text-3">
								{!isAverageSegmentMetric && (
									<>
										{!!data?.trend?.trendClassification &&
											data?.trend?.trendClassification !==
												TrendClassification.Neutral && (
												<span
													className="ml-1"
													style={{
														color: getStatsColor(
															data?.trend
																?.trendClassification
														),
													}}
												>
													<ClayIcon
														symbol={
															getIcon(
																data?.trend
																	?.percentage ??
																	0
															) ?? ''
														}
													/>
												</span>
											)}

										<span className="text-secondary">
											{sub(
												title ===
													METRICS_TITLES.totalMembersMetric
													? Liferay.Language.get(
															'x-vs-last-x-days'
														)
													: Liferay.Language.get(
															'x-vs-last-x-day-avg'
														),
												[
													<span
														className="mr-1"
														key="percentage"
														style={{
															color:
																getStatsColor(
																	data?.trend
																		?.trendClassification
																) ||
																TrendClassification.Neutral,
														}}
													>
														{`${toRounded(
															data?.trend
																?.percentage ??
																0,
															2
														)}%`}
													</span>,
													30,
												],
												false
											)}
										</span>
									</>
								)}
							</span>
						</>
					</div>
				</>
			)}
		</Card.Body>
	);
};
const MembershipMetrics: React.FC<PropsFromRedux> = ({addAlert}) => {
	const {groupId, id} = useParams<{groupId: string; id: string}>();

	const {data, error, loading} = useRequest({
		dataSourceFn: fetchMembershipMetrics,
		variables: {groupId, individualSegmentId: id},
	});

	if (error) {
		addAlert({
			alertType: Alert.Types.Error,
			message: Liferay.Language.get(
				'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
			),
		});
	}

	return (
		<div className="membership-metrics-root">
			<Card
				minHeight={100}
				reportContainer={
					ReportContainer.AverageSegmentMembershipDurationCard
				}
			>
				<CardSection
					data={data?.averageSegmentMembershipDurationMetric}
					description={
						METRICS_TEXT.averageSegmentMembershipDurationMetric
					}
					loading={loading}
					title={
						METRICS_TITLES.averageSegmentMembershipDurationMetric
					}
					trendComparison={
						data?.averageSegmentMembershipDurationMetric?.trend
							?.percentage ?? 0
					}
				/>
			</Card>

			<Card
				className="d-flex flex-row justify-content-between"
				minHeight={100}
				reportContainer={ReportContainer.MembershipMetricsCard}
			>
				<CardSection
					data={data?.totalMembersMetric}
					description={METRICS_TEXT.totalMembersMetric}
					loading={loading}
					title={METRICS_TITLES.totalMembersMetric}
					totalComparisonValue={
						data?.totalMembersMetric?.totalIndividuals
					}
				/>

				<CardSection
					data={data?.entryRateMetric}
					description={METRICS_TEXT.entryRateMetric}
					loading={loading}
					title={METRICS_TITLES.entryRateMetric}
					totalComparisonValue={data?.totalMembersMetric?.value}
				/>

				<CardSection
					data={data?.exitRateMetric}
					description={METRICS_TEXT.exitRate}
					loading={loading}
					title={METRICS_TITLES.exitRate}
					totalComparisonValue={data?.totalMembersMetric?.value}
				/>
			</Card>
		</div>
	);
};

export default connector(MembershipMetrics);
