/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as d3 from 'd3';
import React from 'react';
import {toThousandsABTesting} from '~/experiments/util/experiments';
import ChartTooltip, {
	Alignments,
	IChartTooltipProps,
	Weights,
} from '~/shared/components/chart-tooltip';
import {getDate as getDateUtil} from '~/shared/util/date';

const formatTooltip = (dataPoint: any[]): IChartTooltipProps => {
	const control = dataPoint[0];
	const variant = dataPoint[1];

	const header = [
		{
			columns: [
				{
					label: `${Liferay.Language.get(
						'test-traffic'
					)} | ${d3.utcFormat('%b %-d %Y')(
						getDateUtil(control.payload.key)
					)}`,
					weight: Weights.Semibold,
					width: 180,
				},
				{
					align: Alignments.Right,
					label: Liferay.Language.get('test-sessions'),
					weight: Weights.Semibold,
					width: 100,
				},
				{
					align: Alignments.Right,
					label: Liferay.Language.get('traffic-split'),
					weight: Weights.Semibold,
					width: 100,
				},
			],
		},
	];

	const rows = [
		{
			columns: [
				{
					color: control.color,
					label: control.name,
					weight: Weights.Semibold,
				},
				{
					align: Alignments.Right,
					label: toThousandsABTesting(
						control.payload['data_control']
					),
				},
				{
					align: Alignments.Right,
					label: `${control.payload['data_control_traffic_split']}%`,
				},
			],
		},
		{
			columns: [
				{
					color: variant.color,
					label: variant.name,
					weight: Weights.Semibold,
				},
				{
					align: Alignments.Right,
					label: toThousandsABTesting(
						variant.payload['data_variant']
					),
				},
				{
					align: Alignments.Right,
					label: `${variant.payload['data_variant_traffic_split']}%`,
				},
			],
		},
		{
			columns: [
				{
					label: Liferay.Language.get('total'),
					weight: Weights.Semibold,
				},
				{
					align: Alignments.Right,
					label: toThousandsABTesting(
						variant.payload['data_control'] +
							variant.payload['data_variant']
					),
				},
				{
					align: Alignments.Right,
					label: `${
						variant.payload['data_control_traffic_split'] +
						variant.payload['data_variant_traffic_split']
					}%`,
				},
			],
		},
	];

	return {header, rows};
};

export const Tooltip = function Tooltip({dataPoint}: {dataPoint: any[]}) {
	return (
		<div className="bb-tooltip-container position-static">
			<ChartTooltip {...formatTooltip(dataPoint)} />
		</div>
	);
};
