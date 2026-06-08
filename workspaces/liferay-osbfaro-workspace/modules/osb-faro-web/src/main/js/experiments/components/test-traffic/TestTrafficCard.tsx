/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Card from '~/shared/components/Card';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {getAxisFormatter} from '~/shared/util/charts';
import {Sizes} from '~/shared/util/constants';

import {ComposedChart} from '../ComposedChart';
import {Tooltip} from './Tooltip';

interface IExperimentWithHistogram {
	dxpVariants: Array<{
		dxpVariantName: string;
		sessionsHistogram: Array<{key: string | number; value: number}>;
		trafficSplit: number;
	}>;
}

const formatData = (experiment: IExperimentWithHistogram) => {
	const chartData: Array<Record<string, any>> = [];
	const control = experiment.dxpVariants[0];
	const variant = experiment.dxpVariants[1];

	control.sessionsHistogram.forEach((session, index) => {
		chartData.push({
			data_control: session.value,
			data_control_traffic_split: control.trafficSplit,
			data_variant: variant.sessionsHistogram[index].value,
			data_variant_traffic_split: variant.trafficSplit,
			key: session.key,
		});
	});

	return {
		controlLabel: control.dxpVariantName,
		data: chartData,
		format: getAxisFormatter('number'),
		intervals: control.sessionsHistogram.map(({key}) => key),
		variantLabel: variant.dxpVariantName,
	};
};

const TestTraffic = ({experiment}: {experiment: IExperimentWithHistogram}) => (
	<Card className="analytics-session-card" minHeight={405}>
		<Card.Header className="align-items-center d-flex justify-content-between">
			<Card.Title>{Liferay.Language.get('test-traffic')}</Card.Title>
		</Card.Header>

		<Card.Body>
			<StatesRenderer empty={!experiment.dxpVariants.length}>
				<StatesRenderer.Empty
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

				{!!experiment.dxpVariants.length && (
					<ComposedChart
						Tooltip={Tooltip}
						data={formatData(experiment)}
					/>
				)}
			</StatesRenderer>
		</Card.Body>
	</Card>
);

export default TestTraffic;
