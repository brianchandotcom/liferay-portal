/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get, round} from 'lodash';
import React from 'react';
import {BreakdownDataItem} from '~/event-analysis/utils/types';
import {getPercentage} from '~/shared/util/util';

interface IPercentOfCellProps extends React.HTMLAttributes<HTMLElement> {
	compareToPrevious?: boolean;
	events: BreakdownDataItem[];
	totalValue: number;
}

const getItems = (
	events: BreakdownDataItem[],
	compareToPrevious: boolean
): {
	value: number;
}[] => {
	const data: {value: number}[] = [];

	events.forEach(({previousValue = 0, value}) => {
		data.push({
			value,
		});

		if (compareToPrevious) {
			data.push({
				value: previousValue,
			});
		}
	});

	return data;
};

const PercentOfCell: React.FC<IPercentOfCellProps> = ({
	compareToPrevious = false,
	events = [],
	totalValue,
}) => {
	const isComparingEvent = events.length > 1;
	const isComparingSegment = get(events[0], 'breakdownItems', []).length > 1;

	const data = isComparingSegment
		? getItems(events[0].breakdownItems ?? [], compareToPrevious)
		: getItems(events, compareToPrevious);

	return (
		<>
			<ul className="percentage-column">
				{data.map(({value}, i) => (
					<li key={i}>{`${round(
						getPercentage(value, totalValue),
						2
					)}%`}</li>
				))}
			</ul>

			{isComparingSegment && isComparingEvent && (
				<ul className="percentage-column">
					{getItems(
						events[1].breakdownItems ?? [],
						compareToPrevious
					).map(({value}, i) => (
						<li key={i}>{`${round(
							getPercentage(value, totalValue),
							2
						)}%`}</li>
					))}
				</ul>
			)}
		</>
	);
};

export default PercentOfCell;
