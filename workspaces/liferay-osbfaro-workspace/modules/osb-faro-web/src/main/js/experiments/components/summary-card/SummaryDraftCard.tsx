/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import {getMetricName} from '~/experiments/util/experiments';
import {MetricName} from '~/experiments/util/types';
import Card from '~/shared/components/Card';
import {sub} from '~/shared/util/lang';

import ClayMultiStep from '../clay-multi-step';
import {SummaryBaseCard} from './SummaryBaseCard';
import {SummaryTitle} from './SummaryTitle';
import {IExperiment} from './types';

export const SummaryDraftCard = function SummaryDraftCard({
	experiment: {dxpExperienceName, dxpSegmentName, dxpVariants, goal, status},
}: {
	experiment: IExperiment;
}) {
	const currentStep = dxpVariants ? 3 : goal ? 2 : 1;

	const totalVariants = dxpVariants?.filter(
		({control}: {control: boolean}) => !control
	).length;

	const steps = [
		{
			Description: ({className}: {className?: string}) => (
				<span className={className}>
					{dxpExperienceName ? (
						<>
							<div>
								<span className="mr-1 text-secondary">
									{`${Liferay.Language.get('experience')}:`}
								</span>

								{dxpExperienceName}
							</div>

							<div>
								<span className="mr-1 text-secondary">
									{`${Liferay.Language.get('segment')}:`}
								</span>
								{dxpSegmentName}
							</div>
						</>
					) : (
						Liferay.Language.get(
							'select-a-control-experience-and-target-segment-for-your-test'
						)
					)}
				</span>
			),
			title: Liferay.Language.get('test-target'),
		},
		{
			Description: ({className}: {className?: string}) => (
				<span className={className}>
					{goal ? (
						<strong>
							{getMetricName(goal.metric as MetricName)}
						</strong>
					) : (
						Liferay.Language.get(
							'choose-a-metric-that-determines-your-campaigns-success'
						)
					)}
				</span>
			),
			title: Liferay.Language.get('test-metric'),
		},
		{
			Description: ({className}: {className?: string}) => (
				<span className={className}>
					{dxpVariants
						? sub(
								dxpVariants && totalVariants > 1
									? Liferay.Language.get('x-variants')
									: Liferay.Language.get('x-variant'),
								[totalVariants]
							)
						: Liferay.Language.get('no-variants-created')}
				</span>
			),
			title: Liferay.Language.get('variants'),
		},
		{
			Description: ({className}: {className?: string}) => (
				<span className={className}>
					{Liferay.Language.get(
						'review-traffic-split-and-run-your-test'
					)}
				</span>
			),
			title: Liferay.Language.get('review-&-run'),
		},
	];

	return (
		<SummaryBaseCard status={status.toLowerCase()}>
			<SummaryBaseCard.Header
				Description={() =>
					Liferay.Language.get('finish-the-setup-to-run-the-test')
				}
				title={Liferay.Language.get('test-is-in-draft-mode')}
			/>

			<SummaryBaseCard.Body>
				<div className="mt-4 w-100">
					<SummaryTitle
						className="mb-4"
						label={Liferay.Language.get('test-target')}
					/>

					<ClayMultiStep
						current={currentStep}
						showIndicatorLabel={false}
					>
						{steps.map(({Description, title}, index) => (
							<ClayMultiStep.Item
								Body={() => (
									<Card
										className={getCN(
											'analytics-summary-card-step-content',
											{
												[`analytics-summary-card-step-content-${status}`]:
													status,
											}
										)}
									>
										<Card.Body>
											<div className="h4">{title}</div>

											<Description className="analytics-summary-card-step-content-description" />
										</Card.Body>
									</Card>
								)}
								key={index}
							/>
						))}
					</ClayMultiStep>
				</div>
			</SummaryBaseCard.Body>
		</SummaryBaseCard>
	);
};
