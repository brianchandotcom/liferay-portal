/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Select, {MultiValue, StylesConfig} from 'react-select';
import makeAnimated from 'react-select/animated';

import {FieldBase} from '../FieldBase';

type MultiSelectProps<T> = {
	className?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	items: T[];
	label?: string;
	localized?: boolean;
	onChange: (values: T) => void;
	placeholder?: string;
	required?: boolean;
	tooltip?: string;
	value?: MultiValue<any>;
};

const colourStyles: StylesConfig<any, true> = {
	control: (styles) =>
		({
			...styles,
			':focus-within': {
				backgroundColor: '#f0f5ff',
				border: '1px solid #80acff',
				boxShadow: '0 0 0 0.125rem #fff, 0 0 0 0.25rem #80acff',
				transition: 'all ease-in-out .3s',
			},
			':hover': {
				background: '1px solid #f0f5ff',
				outline: 'none',
				transition: 'all ease-in-out .3s',
			},
			'border': '1px solid #B1B2B9',
			'borderRadius': '8px',
			'boxShadow': 'none',
			'transition': 'all ease-in-out .3s',
		} as any),

	multiValue: (styles) =>
		({
			...styles,
			backgroundColor: '#f0f5ff',
			borderRadius: '4px',
			color: '#1C3667',
		} as any),
	multiValueRemove: (styles) =>
		({
			...styles,
			':hover': {
				backgroundColor: '#80acff',
				color: 'white',
			},
			'color': '#1C3667',
		} as any),
};

export function MultiSelect<T>({
	className,
	helpMessage,
	hideFeedback,
	items,
	label,
	localized,
	onChange,
	placeholder,
	required,
	tooltip,
	value,
}: MultiSelectProps<T>) {
	const animatedComponents = makeAnimated();

	return (
		<FieldBase
			className={className}
			helpMessage={helpMessage}
			hideFeedback={hideFeedback}
			label={label}
			localized={localized}
			required={required}
			tooltip={tooltip}
		>
			<Select
				className="multiselect-container-form-control"
				components={animatedComponents}
				isMulti
				onChange={(newValue) => newValue && onChange(newValue as T)}
				options={items}
				placeholder={placeholder}
				styles={colourStyles}
				value={value}
			/>
		</FieldBase>
	);
}
