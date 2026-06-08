/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	getMetricName,
	mergedVariants,
	toThousandsABTesting,
} from '~/experiments/util/experiments';
import {MetricName} from '~/experiments/util/types';
import {formatDateToTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {toRounded} from '~/shared/util/numbers';

import {SummaryAlert} from './SummaryAlert';
import {SummaryBaseCard} from './SummaryBaseCard';
import {SummaryParagraph} from './SummaryParagraph';
import {SummarySection} from './SummarySection';
import {SummaryTitle} from './SummaryTitle';
import {IExperiment} from './types';

export const SummaryWinnerCard = function SummaryWinnerCard({
	experiment,
	timeZoneId,
}: {
	experiment: IExperiment & {
		description?: string;
		metrics: {
			completion: number;
			elapsedDays: number;
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
		dxpVariants,
		goal,
		metrics: {completion, elapsedDays, variantMetrics},
		sessions,
		startedDate,
		status,
		type,
		winnerDXPVariantId,
	} = experiment;

	const variants = mergedVariants(dxpVariants, variantMetrics);

	const winnerVariant = variants.find(
		({dxpVariantId}) => dxpVariantId === winnerDXPVariantId
	);

	const secondPlaceVariant = variants.find(
		({dxpVariantId}) => dxpVariantId !== winnerDXPVariantId
	);

	return (
		<SummaryBaseCard status={status.toLowerCase()}>
			<SummaryBaseCard.Header
				Description={() =>
					sub(Liferay.Language.get('started-x'), [
						formatDateToTimeZone(startedDate, 'll', timeZoneId),
					]) as any
				}
				title={Liferay.Language.get('winner-declared')}
			/>

			{winnerVariant && winnerVariant?.control ? (
				<SummaryAlert symbol="check-circle">
					<SummaryTitle
						className="font-weight-bold mb-1"
						label={
							sub(
								Liferay.Language.get(
									'control-has-outperformed-x-by-at-least-x'
								),
								[
									secondPlaceVariant?.dxpVariantName ?? '',
									`${toRounded(
										Math.abs(
											secondPlaceVariant?.improvement ?? 0
										),
										2
									)}%`,
								]
							) as string
						}
					/>

					<strong>
						{Liferay.Language.get(
							'we-recommend-that-you-keep-control-published-and-complete-this-test'
						)}
					</strong>
				</SummaryAlert>
			) : (
				<SummaryAlert symbol="exclamation-circle">
					<SummaryTitle
						className="font-weight-bold mb-1"
						label={
							sub(
								Liferay.Language.get(
									'x-has-outperformed-control-by-at-least-x'
								),
								[
									winnerVariant?.dxpVariantName,
									`${toRounded(
										winnerVariant?.improvement ?? 0,
										2
									)}%`,
								]
							) as string
						}
					/>

					<strong>
						{Liferay.Language.get(
							'we-recommend-that-you-publish-the-winning-variant'
						)}
					</strong>
				</SummaryAlert>
			)}

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
								value={parseInt(toRounded(completion))}
							/>
						</SummarySection>

						{type === 'AB' && (
							<SummarySection
								title={Liferay.Language.get('days-running')}
							>
								<SummarySection.Heading
									value={String(elapsedDays)}
								/>
							</SummarySection>
						)}

						<SummarySection
							title={Liferay.Language.get('total-test-sessions')}
						>
							<SummarySection.Heading
								value={toThousandsABTesting(sessions)}
							/>
						</SummarySection>

						{goal && (
							<SummarySection
								title={Liferay.Language.get('test-metric')}
							>
								<SummarySection.MetricType
									value={getMetricName(
										goal.metric as MetricName
									)}
								/>
								{winnerVariant?.improvement !== undefined &&
									winnerVariant.improvement > 0 && (
										<SummarySection.Variant
											lift={`${toRounded(
												winnerVariant.improvement,
												2
											)}%`}
											status="up"
										/>
									)}
							</SummarySection>
						)}
					</div>
				</div>
			</SummaryBaseCard.Body>
		</SummaryBaseCard>
	);
};
