/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {Progress, Tasks} from '../../util/mock';
import TaskbarProgress from './TaskbarProgress';

type ProgressBarProps = {
	chartOrder?: string[];
	displayTotalCompleted?: boolean;
	items: Tasks | Progress;
	legend?: boolean;
};

const ProgressBar: React.FC<ProgressBarProps> = ({
	chartOrder,
	displayTotalCompleted = true,
	items,
	legend,
}) => {
	const sortedItems = Object.entries(items).sort(
		([keyA, valueA], [keyB, valueB]) => {
			if (chartOrder) {
				return chartOrder.indexOf(keyA) - chartOrder.indexOf(keyB);
			}

			return valueB - valueA;
		}
	);

	const totalCompleted = useMemo(() => {
		const _totalCompleted = sortedItems
			.filter(([label, value]) => {
				if (label !== 'incomplete') {
					return value;
				}
			})
			.map(([, value]) => value);

		if (_totalCompleted.length) {
			return _totalCompleted.reduce(
				(previousValue, currentValue) => previousValue + currentValue
			);
		}

		return 0;
	}, [sortedItems]);

	return (
		<TaskbarProgress
			displayTotalCompleted={displayTotalCompleted}
			items={sortedItems}
			legend={legend}
			totalCompleted={totalCompleted}
		/>
	);
};

export default ProgressBar;
