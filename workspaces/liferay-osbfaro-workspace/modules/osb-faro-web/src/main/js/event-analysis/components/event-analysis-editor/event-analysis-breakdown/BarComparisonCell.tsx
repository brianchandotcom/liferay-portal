/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get} from 'lodash';
import React from 'react';
import {BreakdownDataItem, Event} from '~/event-analysis/utils/types';

import BarComparisonTable, {
	BarComparisonTableItems,
} from './BarComparisonTable';

enum BAR_COMPARISON_COLORS {
	Blue = 'blue',
	Green = 'green',
}

const EMPTY_BAR_COLOR = '#CDCED9';

const MAP_COLORS: Record<
	BAR_COMPARISON_COLORS,
	Array<{
		current: string;
		previous: string;
	}>
> = {
	blue: [
		{
			current: '#187FFF',
			previous: '#97C5FF',
		},
		{
			current: '#4B9BFF',
			previous: '#97C5FF',
		},
	],
	green: [
		{
			current: '#31BE88',
			previous: '#8DE2C1',
		},
		{
			current: '#3CCD95',
			previous: '#8DE2C1',
		},
	],
};

interface IBarComparisonCellProps extends React.HTMLAttributes<HTMLElement> {
	compareToPrevious: boolean;
	event: Event;
	events: BreakdownDataItem[];
	topValue: number;
}

const BarComparisonCell: React.FC<IBarComparisonCellProps> = ({
	compareToPrevious = false,
	event,
	events = [],
	topValue,
}) => {
	const isComparingSegment = get(events[0], 'breakdownItems', []).length > 1;

	const sections = getSections(
		events,
		compareToPrevious,
		isComparingSegment,
		topValue
	);

	return (
		<div className="bar-comparison-root table-responsive table-root">
			{sections.map((items, i) => (
				<BarComparisonTable
					event={event}
					isComparingSegment={isComparingSegment}
					items={items}
					key={i}
				/>
			))}
		</div>
	);
};

const getSections = (
	events: BreakdownDataItem[],
	compareToPrevious: boolean,
	isComparingSegment: boolean,
	topValue: number
): BarComparisonTableItems[] => {
	const isComparingEvent = events.length > 1;

	const sections: BarComparisonTableItems[] = [];
	let data: BarComparisonTableItems = [];

	const addToData = (
		{
			name,
			previousValue = 0,
			value,
		}: {name: string; previousValue?: number; value: number},
		color: BAR_COMPARISON_COLORS,
		index: number
	) => {
		data.push({
			isPreviousValue: false,
			name,
			percent: value / topValue,
			style: {
				'background-color':
					value > 0
						? MAP_COLORS[color][index].current
						: EMPTY_BAR_COLOR,
			},
			value,
		});

		if (compareToPrevious) {
			data.push({
				isPreviousValue: true,
				name: Liferay.Language.get('previous-value'),
				percent: previousValue / topValue,
				style: {
					'background-color':
						previousValue > 0
							? MAP_COLORS[color][index].previous
							: EMPTY_BAR_COLOR,
				},
				value: previousValue,
			});
		}
	};

	events.forEach((event, i) => {
		const color =
			i === 0 ? BAR_COMPARISON_COLORS.Blue : BAR_COMPARISON_COLORS.Green;

		if (i !== 0 && isComparingEvent && isComparingSegment) {
			sections.push([...data]);
			data = [];
		}

		if (isComparingSegment) {
			(event.breakdownItems ?? []).forEach((item, i) => {
				addToData(item, color, i);
			});
		}
		else {
			addToData(event, color, i);
		}
	});

	sections.push([...data]);

	return sections;
};

export default BarComparisonCell;
