/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import DateInput from '~/shared/components/DateInput';
import DateRangeInput, {DateRange} from '~/shared/components/DateRangeInput';

interface IDatePickerInputProps {
	isRange?: boolean;
	onBlur: () => void;
	onChange: (param: string | DateRange) => void;
	touched: boolean;
	valid: boolean;
	value: string | DateRange;
}

const DatePickerInput: React.FC<IDatePickerInputProps> = ({
	isRange,
	onBlur,
	onChange,
	touched,
	valid,
	value,
}) => {
	const classNames = getCN({
		'has-error': !valid && touched,
	});

	return (
		<>
			{isRange ? (
				<DateRangeInput
					className={classNames}
					onBlur={onBlur}
					onChange={onChange as (param: DateRange) => void}
					value={value as DateRange}
				/>
			) : (
				<DateInput
					className={classNames}
					onDateInputBlur={onBlur}
					onDateInputChange={onChange}
					readOnly
					value={value}
				/>
			)}
		</>
	);
};

export default DatePickerInput;
