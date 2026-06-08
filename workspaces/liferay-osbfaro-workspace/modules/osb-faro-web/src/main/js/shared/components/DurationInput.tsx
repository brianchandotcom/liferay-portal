/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNumber} from 'lodash';
import React, {useState} from 'react';
import MaskedInput from '~/shared/components/MaskedInput';
import {formatTime, getMillisecondsFromTime} from '~/shared/util/time';

const DURATION_MASK = [/\d/, /\d/, ':', /[0-6]/, /\d/, ':', /[0-6]/, /\d/];

interface IDurationInputProps {
	onBlur?: (event: React.FocusEvent<HTMLInputElement>) => void;
	onChange: (milliseconds: number | string) => void;
	value: string | number;
}

const DurationInput: React.FC<IDurationInputProps> = ({
	onBlur,
	onChange,
	value = '',
}) => {
	const [duration, setDuration] = useState(
		isNumber(value) ? formatTime(value as number) : ''
	);

	return (
		<MaskedInput
			autoComplete="off"
			className="number-input"
			data-testid="duration-input"
			mask={DURATION_MASK}
			name="value"
			onBlur={onBlur}
			onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
				const {value} = event.target;

				setDuration(value);

				onChange &&
					onChange(
						value
							? getMillisecondsFromTime(value.replace(/_/g, '0'))
							: 0
					);
			}}
			placeholder="HH:MM:SS"
			required
			type="string"
			value={duration}
		/>
	);
};

export default DurationInput;
