/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldProps} from 'formik';
import React from 'react';

import RadioGroup from '../RadioGroup';
import Label from './Label';

interface IFormRadioGroupProps
	extends FieldProps,
		React.HTMLAttributes<HTMLElement> {
	inline: boolean;
	label: string;
	onChange: (value: any) => void;
}

const FormRadioGroup: React.FC<IFormRadioGroupProps> = ({
	children,
	className,
	field,
	form,
	inline = false,
	label,
	onChange,
}) => {
	const {name, value: checked} = field;

	const handleChange = (value: any) => {
		const {setFieldValue} = form;

		setFieldValue(name, value);

		if (onChange) {
			onChange(value);
		}
	};

	return (
		<div className={className}>
			{label && <Label htmlFor={name}>{label}</Label>}

			<RadioGroup
				{...field}
				checked={checked}
				id={name}
				inline={inline}
				name={name}
				onChange={handleChange}
			>
				{children}
			</RadioGroup>
		</div>
	);
};

export default Object.assign(FormRadioGroup, {
	Option: RadioGroup.Option,
	Subsection: RadioGroup.Subsection,
});
