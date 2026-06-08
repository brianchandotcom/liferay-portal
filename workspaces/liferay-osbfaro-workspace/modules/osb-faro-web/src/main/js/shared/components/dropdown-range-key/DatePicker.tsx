/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text as ClayText} from '@clayui/core';
import React from 'react';
import {MomentDateRange} from '~/shared/components/DateRangeInput';
import {useTimeZone} from '~/shared/hooks/useTimeZone';
import {sub} from '~/shared/util/lang';

import DatePicker from '../date-picker';
import {formatDateWithTimezone} from './utils';

interface IDropdownRangeKeyDatePickerProps {
	customDateRange: MomentDateRange;
	onCustomRangeChange: (range: MomentDateRange) => void;
	retentionPeriod: number | null;
}

export const DropdownRangeKeyDatePicker = function DropdownRangeKeyDatePicker({
	customDateRange,
	onCustomRangeChange,
	retentionPeriod,
}: IDropdownRangeKeyDatePickerProps) {
	const {timeZoneId} = useTimeZone();

	return (
		<DatePicker
			date={customDateRange}
			displayLabel={false}
			header={
				<>
					<ClayText size={2} weight="semi-bold">
						{Liferay.Language.get('custom-range').toUpperCase()}
					</ClayText>

					<div>
						<ClayText color="secondary" size={2}>
							{sub(
								Liferay.Language.get(
									'dates-prior-to-x-months-cannot-be-selected-due-to-your-workspaces-data-retention-period'
								),
								[retentionPeriod]
							)}
						</ClayText>
					</div>
				</>
			}
			maxDate={formatDateWithTimezone(timeZoneId)
				.clone()
				.subtract(1, 'days')}
			maxRange={365}
			minDate={formatDateWithTimezone(timeZoneId)
				.clone()
				.subtract(retentionPeriod, 'month')}
			onSelect={({end, start}: MomentDateRange) =>
				onCustomRangeChange({
					end,
					start,
				})
			}
			timeZoneId={timeZoneId}
		/>
	);
};
