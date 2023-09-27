/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Select, {StylesConfig} from 'react-select';
import makeAnimated from 'react-select/animated';

import './MultiSelect.scss';

import classNames from 'classnames';

import {FieldBase} from '../FieldBase';

interface MultiSelectProps<T> {
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
}

const colourStyles: StylesConfig<any, true> = {
	control: (styles) => ({
		...styles,
		border: '2px solid #B1B2B9',
		borderRadius: '8px',
	}),
	multiValue: (styles) => {
		return {
			...styles,
			backgroundColor: '#E6EBF5',
			color: '#1C3667',
		};
	},
	multiValueRemove: (styles) => ({
		...styles,
		':hover': {
			backgroundColor: '#1C3667',
			color: 'white',
		},
		'color': '#1C3667',
	}),
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
}: MultiSelectProps<T>) {
	const animatedComponents = makeAnimated();

	return (
		<FieldBase
			className={classNames('multiselect-container', className)}
			helpMessage={helpMessage}
			hideFeedback={hideFeedback}
			label={label}
			localized={localized}
			required={required}
			tooltip={tooltip}
		>
			<Select
				components={animatedComponents}
				isMulti
				onChange={(newValue) => newValue && onChange(newValue as T)}
				options={items}
				placeholder={placeholder}
				styles={colourStyles}
			/>
		</FieldBase>
	);
}
