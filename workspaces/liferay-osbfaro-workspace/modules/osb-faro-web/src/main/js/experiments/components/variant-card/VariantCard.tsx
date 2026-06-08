/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayNavigationBar from '@clayui/navigation-bar';
import React, {useState} from 'react';
import {MediansChart} from '~/experiments/components/variant-card/MediansChart';
import {PerDayChart} from '~/experiments/components/variant-card/per-day-chart/PerDayChart';
import Card from '~/shared/components/Card';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {Sizes} from '~/shared/util/constants';

import {IExperiment} from '../summary-card/types';
import {VariantTable} from './VariantTable';

enum VariantView {
	Medians = 'medians',
	PerDay = 'per-day',
}

export const VariantCard = function VariantCard({
	experiment,
}: {
	experiment: IExperiment & {metricsHistogram?: unknown[]};
}) {
	const [variantView, setVariantView] = useState<VariantView>(
		VariantView.Medians
	);

	const Component =
		variantView === VariantView.Medians ? MediansChart : PerDayChart;

	return (
		<Card className="analytics-variant-card">
			<Card.Header>
				<Card.Title>
					{Liferay.Language.get('variant-report')}
				</Card.Title>
				<ClayNavigationBar
					triggerLabel={
						variantView === VariantView.Medians
							? Liferay.Language.get('medians')
							: Liferay.Language.get('per-day')
					}
				>
					<ClayNavigationBar.Item
						active={variantView === VariantView.Medians}
					>
						<ClayButton
							onClick={() => setVariantView(VariantView.Medians)}
						>
							{Liferay.Language.get('medians')}
						</ClayButton>
					</ClayNavigationBar.Item>

					<ClayNavigationBar.Item
						active={variantView === VariantView.PerDay}
					>
						<ClayButton
							onClick={() => setVariantView(VariantView.PerDay)}
						>
							{Liferay.Language.get('per-day')}
						</ClayButton>
					</ClayNavigationBar.Item>
				</ClayNavigationBar>
			</Card.Header>

			<Card.Body>
				<div className="analytics-variant-card-charts">
					<StatesRenderer
						empty={!experiment.metricsHistogram?.length}
					>
						<StatesRenderer.Empty
							className="my-6"
							description={Liferay.Language.get(
								'metrics-will-show-once-there-are-visitors-to-your-variants'
							)}
							icon={{
								border: false,
								size: Sizes.XLarge,
								symbol: 'ac_chart',
							}}
							title={Liferay.Language.get(
								'we-are-currently-collecting-data'
							)}
						/>

						{!!experiment.metricsHistogram?.length && (
							<Component experiment={experiment as any} />
						)}
					</StatesRenderer>

					<VariantTable experiment={experiment} />
				</div>
			</Card.Body>
		</Card>
	);
};
