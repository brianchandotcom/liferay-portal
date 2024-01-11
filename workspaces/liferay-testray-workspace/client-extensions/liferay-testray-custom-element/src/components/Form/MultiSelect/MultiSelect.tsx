/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form from '..';
import {InputHTMLAttributes, useState} from 'react';
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
}) => {
	const [visible, setVisible] = useState(false);

	return (
		<Form.BaseWrapper label={label}>
			<ReactSelect
				classNamePrefix="testray-multi-select"
				closeMenuOnSelect={false}
				isDisabled={disabled}
				isMulti
				menuIsOpen={visible}
				name={name}
				onBlur={() => setVisible(false)}
				onChange={(value) => {
					if (onChange) {
						onChange({target: {name, value}} as any);
					}
				}}
				onFocus={() => setVisible(true)}
				onKeyDown={(event) => {
					if (event.key === 'Escape' && visible === true) {
						event.stopPropagation();
						setVisible(false);
					}

					return;
				}}
				options={options}
				value={value as PropsValue<unknown>}
			/>
		</Form.BaseWrapper>
	);
};

export default MultiSelect;
