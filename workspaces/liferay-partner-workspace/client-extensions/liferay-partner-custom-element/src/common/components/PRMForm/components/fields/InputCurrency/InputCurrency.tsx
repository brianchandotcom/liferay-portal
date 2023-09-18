/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IMaskInput} from 'react-imask';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	onAccept: (value: number) => void;
}

const InputCurrency = ({
	description,
	field,
	label,
	meta,
	onAccept,
	required,
	value,
}: PRMFormFieldProps & PRMFormFieldStateProps<number> & IProps) => (
	<WrapperInput
		{...meta}
		description={description}
		label={label}
		required={required}
	>
		<IMaskInput
			className="form-control"
			mapToRadix={['.']}
			mask={Number}
			name={field.name}
			normalizeZeros
			onAccept={(value) => onAccept(value as number)}
			onBlur={(event) =>
				field.onBlur(event as React.FocusEvent<Element, Element>)
			}
			padFractionalZeros
			radix="."
			required={required}
			scale={2}
			thousandsSeparator=","
			unmask="typed"
			value={value || field.value || ''}
		/>
	</WrapperInput>
);

export default InputCurrency;
