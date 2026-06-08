/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React, {useEffect, useState} from 'react';
import Input from '~/shared/components/Input';
import Form from '~/shared/components/form';

import {isValid} from '../../utils/utils';

export type BetweenNumber = {
	end: string | number;
	start: string | number;
};

interface IBetweenNumberInputProps {
	onChange: (params: {
		touched?: boolean;
		valid?: boolean;
		value?: number | string | BetweenNumber;
	}) => void;
	value: BetweenNumber;
}

const BetweenNumberInput: React.FC<IBetweenNumberInputProps> = ({
	onChange,
	value,
}) => {
	const [inputsValid, setInputsValid] = useState({end: false, start: false});
	const [inputsTouched, setInputsTouched] = useState({
		end: false,
		start: false,
	});

	useEffect(() => {
		onChange({
			touched: inputsTouched.end && inputsTouched.start,
			valid: inputsValid.end && inputsValid.start,
			value,
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [inputsTouched, inputsValid]);

	return (
		<>
			<Form.GroupItem
				className={getCN({
					'has-error': !inputsValid.start && inputsTouched.start,
				})}
				shrink
			>
				<Input
					className="number-input"
					data-testid="between-number-start-input"
					onBlur={() => {
						setInputsTouched({...inputsTouched, start: true});
					}}
					onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
						const {value: start} = event.target;

						let numberVal: string | number = '';

						if (isValid(start)) {
							numberVal = parseInt(start, 10);
						}

						setInputsTouched({...inputsTouched, start: true});
						setInputsValid({
							...inputsValid,
							start: isValid(start),
						});

						onChange({
							value: {end: value.end, start: numberVal},
						});
					}}
					placeholder={Liferay.Language.get('number')}
					type="number"
					value={value.start}
				/>
			</Form.GroupItem>
			<Form.GroupItem
				className={getCN({
					'has-error': !inputsValid.end && inputsTouched.end,
				})}
				shrink
			>
				<Input
					className="number-input"
					data-testid="between-number-end-input"
					onBlur={() => {
						setInputsTouched({...inputsTouched, end: true});
					}}
					onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
						const {value: end} = event.target;

						let numberVal: string | number = '';

						if (isValid(end)) {
							numberVal = parseInt(end, 10);
						}

						setInputsTouched({...inputsTouched, end: true});
						setInputsValid({
							...inputsValid,
							end: isValid(numberVal),
						});

						onChange({
							value: {end: numberVal, start: value.start},
						});
					}}
					placeholder={Liferay.Language.get('number')}
					type="number"
					value={value.end}
				/>
			</Form.GroupItem>
		</>
	);
};

export default BetweenNumberInput;
