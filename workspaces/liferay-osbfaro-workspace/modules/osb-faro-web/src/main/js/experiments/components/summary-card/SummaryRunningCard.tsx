/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	getBestVariant,
	getMetricName,
	toThousandsABTesting,
} from '~/experiments/util/experiments';
import {MetricName} from '~/experiments/util/types';
import {formatDateToTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {toRounded} from '~/shared/util/numbers';

import {SummaryBaseCard} from './SummaryBaseCard';
import {SummaryParagraph} from './SummaryParagraph';
import {SummarySection} from './SummarySection';
import {IExperiment} from './types';

export const SummaryRunningCard = function SummaryRunningCard({
	experiment,
	timeZoneId,
}: {
	experiment: IExperiment & {
		description?: string;
		metrics: {
			completion: number;
			elapsedDays: number;
			estimatedDaysLeft?: number;
			variantMetrics: IExperiment['dxpVariants'];
		};
		sessions: number;
		startedDate?: string;
		type?: string;
	};
	timeZoneId: string;
}) {
	const {
		description,
		goal,
		metrics: {completion, elapsedDays, estimatedDaysLeft},
		sessions,
		startedDate,
		status,
		type,
	} = experiment;

	const bestVariant = getBestVariant({
		dxpVariants: experiment.dxpVariants,
		goal: goal as {metric: MetricName} | undefined,
		metrics: experiment.metrics,
	});

	return (
		<SummaryBaseCard status={status.toLowerCase()}>
			<SummaryBaseCard.Header
				Description={() =>
					sub(Liferay.Language.get('started-x'), [
						formatDateToTimeZone(startedDate, 'll', timeZoneId),
					]) as any
				}
				title={Liferay.Language.get('test-is-running')}
			/>
			<SummaryBaseCard.Body>
				<div className="mt-4 w-100">
					<SummaryParagraph
						description={description}
						title={Liferay.Language.get('summary')}
					/>

					<div className="analytics-summary-card-sections">
						<SummarySection
							title={Liferay.Language.get('test-completion')}
						>
							<SummarySection.Heading
								value={`${toRounded(completion)}%`}
							/>

							<SummarySection.ProgressBar
								value={parseInt(toRounded(completion), 10)}
							/>
						</SummarySection>

						{type === 'AB' && (
							<SummarySection
								title={Liferay.Language.get('days-running')}
							>
								<SummarySection.Heading
									value={String(elapsedDays)}
								/>

								{!!estimatedDaysLeft && (
									<SummarySection.Description
										value={
											sub(
												estimatedDaysLeft > 1
													? Liferay.Language.get(
															'about-x-days-left'
														)
													: Liferay.Language.get(
															'about-x-day-left'
														),
												[estimatedDaysLeft]
											) as string
										}
									/>
								)}
							</SummarySection>
						)}

						<SummarySection
							title={Liferay.Language.get('total-test-sessions')}
						>
							<SummarySection.Heading
								value={toThousandsABTesting(sessions)}
							/>
						</SummarySection>

						<SummarySection
							title={Liferay.Language.get('test-metric')}
						>
							<SummarySection.MetricType
								value={
									goal &&
									getMetricName(goal.metric as MetricName)
								}
							/>

							{bestVariant?.improvement !== undefined &&
								bestVariant.improvement > 0 && (
									<SummarySection.Variant
										lift={`${toRounded(
											bestVariant.improvement,
											2
										)}%`}
										status="up"
									/>
								)}
						</SummarySection>
					</div>
				</div>
			</SummaryBaseCard.Body>
		</SummaryBaseCard>
	);
};
