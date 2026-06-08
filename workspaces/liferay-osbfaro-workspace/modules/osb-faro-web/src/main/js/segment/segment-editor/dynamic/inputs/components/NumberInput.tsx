/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {noop} from 'lodash';
import React from 'react';
import Input from '~/shared/components/Input';
import Form from '~/shared/components/form';

import {FunctionalOperators, RelationalOperators} from '../../utils/constants';
import {isValid} from '../../utils/utils';
import BetweenNumberInput, {BetweenNumber} from './BetweenNumberInput';

interface INumberInputProps {
	className?: string;
	onBlur?: () => void;
	onChange: (params: {
		touched?: boolean;
		valid?: boolean;
		value?: number | string | BetweenNumber;
	}) => void;
	operatorName: RelationalOperators & FunctionalOperators;
	touched: boolean;
	valid: boolean;
	value: number | string | BetweenNumber;
}

const NumberInput: React.FC<INumberInputProps> = ({
	className,
	onBlur = noop,
	onChange,
	operatorName,
	touched,
	valid,
	value,
	...otherProps
}) => {
	if (operatorName === FunctionalOperators.Between) {
		return (
			<BetweenNumberInput
				onChange={onChange}
				value={value as BetweenNumber}
			/>
		);
	}

	return (
		<Form.GroupItem
			className={getCN({
				'has-error': !valid && touched,
			})}
			shrink
		>
			<Input
				{...otherProps}
				className={getCN('number-input', className)}
				data-testid="number-input"
				onBlur={() => {
					onChange({touched: true, valid, value});

					onBlur && onBlur();
				}}
				onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
					const {value} = event.target;

					let numberVal: string | number = '';

					if (isValid(value)) {
						numberVal = parseInt(value);
					}

					onChange({
						touched: true,
						valid: isValid(value),
						value: numberVal,
					});
				}}
				placeholder={Liferay.Language.get('number')}
				type="number"
				value={value}
			/>
		</Form.GroupItem>
	);
};

export default NumberInput;
