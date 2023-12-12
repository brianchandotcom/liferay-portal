/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form from '..';
import {InputHTMLAttributes} from 'react';
import ReactSelect, {PropsValue} from 'react-select';

type Option = {label: string; value: string};

type MultiSelectProps = {
	label?: string;
	options: Option[];
} & InputHTMLAttributes<HTMLSelectElement>;

const MultiSelect: React.FC<MultiSelectProps> = ({
	disabled,
	label,
	name = '',
	onChange,
	options,
	value,
}) => (
	<Form.BaseWrapper label={label}>
		<ReactSelect
			classNamePrefix="testray-multi-select"
			closeMenuOnSelect={false}
			isDisabled={disabled}
			isMulti
			name={name}
			onChange={(value) => {
				if (onChange) {
					onChange({target: {name, value}} as any);
				}
			}}
			options={options}
			value={value as PropsValue<unknown>}
		/>
	</Form.BaseWrapper>
);

export default MultiSelect;
