/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import React from 'react';
import TimeRangeQuery from '~/shared/queries/TimeRangeQuery';
import {RangeSelectors} from '~/shared/types';
import {RangeKeyTimeRanges} from '~/shared/util/constants';

import ErrorDisplay from '../ErrorDisplay';
import StatesRenderer from '../states-renderer/StatesRenderer';
import {DropdownRangeKeyContent} from './DropdownRangeKeyContent';

export interface DropdownRangeKeyIProps
	extends React.HTMLAttributes<HTMLElement> {
	alignmentPosition?: number;
	legacy: boolean;
	onRangeSelectorChange: (rangeSelectors: RangeSelectors) => void;
	rangeKeys?: Array<RangeKeyTimeRanges>;
	rangeSelectors?: RangeSelectors;
}

export type Data = {
	preference: {
		value: number;
	};
	timeRange: {
		endDate: string;
		rangeKey: string;
		startDate: string;
	}[];
};

export const DropdownRangeKey = function DropdownRangeKey({
	alignmentPosition,
	legacy,
	onRangeSelectorChange,
	rangeKeys,
	rangeSelectors,
}: DropdownRangeKeyIProps) {
	const {data, error, loading} = useQuery<Data>(TimeRangeQuery);

	return (
		<StatesRenderer error={!!error} loading={loading}>
			<StatesRenderer.Error apolloError={error}>
				<ErrorDisplay />
			</StatesRenderer.Error>

			<StatesRenderer.Loading center={false} />

			<StatesRenderer.Success>
				{data && (
					<DropdownRangeKeyContent
						alignmentPosition={alignmentPosition}
						data={data}
						legacy={legacy}
						onRangeSelectorChange={onRangeSelectorChange}
						rangeKeys={rangeKeys}
						rangeSelectors={rangeSelectors}
					/>
				)}
			</StatesRenderer.Success>
		</StatesRenderer>
	);
};
