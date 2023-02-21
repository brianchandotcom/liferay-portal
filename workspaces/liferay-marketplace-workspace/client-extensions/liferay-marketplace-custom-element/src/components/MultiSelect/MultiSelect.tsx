import Select, {StylesConfig} from 'react-select';
import makeAnimated from 'react-select/animated';

import './MultiSelect.scss';

import classNames from 'classnames';
import {useRef, useState} from 'react';

import {FieldBase} from '../FieldBase';

type Item = {
	label: string;
	value: string;
	checked: boolean;
};

interface MultiSelectProps {
	className?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	label?: string;
	localized?: boolean;
	placeholder?: string;
	onChange: (values: Item[]) => void;
	required?: boolean;
	tooltip?: string;
	items: Item[];
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
		'color': '#1C3667',
		':hover': {
			backgroundColor: '#1C3667',
			color: 'white',
		},
	}),
};

export function MultiSelect({
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
}: MultiSelectProps) {
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
				onChange={(newValue) =>
					newValue && onChange(newValue as Item[])
				}
				options={items}
				placeholder={placeholder}
				styles={colourStyles}
			/>
		</FieldBase>
	);
}
