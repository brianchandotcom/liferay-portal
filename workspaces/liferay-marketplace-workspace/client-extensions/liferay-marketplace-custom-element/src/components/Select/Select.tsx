/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {InputHTMLAttributes} from 'react';

import './Select.scss';
import BaseWrapper from '../Input/base/BaseWrapper';

type InputProps = {
	boldLabel?: boolean;
	className?: string;
	defaultOption?: boolean;
	defaultOptionLabel?:string;
	disabled?: boolean;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	options?: {key: string; name: string}[];
	register?: any;
	required?: boolean;
} & InputHTMLAttributes<HTMLInputElement>;

const Select: React.FC<InputProps> = ({
	boldLabel,
	className,
	defaultOption= true,
	defaultOptionLabel,
	disabled = false,
	errors = {},
	label,
	name,
	register = () => {},
	id = name,
	value,
	required = false,
	onBlur,
	options,
	...otherProps
}) => {
	return (
		<BaseWrapper
			boldLabel={boldLabel}
			disabled={disabled}
			error={errors[name]?.message}
			id={id}
			label={label}
			required={required}
		>
			<select
				className={`align-items-center custom-select d-flex form-control rounded-xs ${className}`}
				disabled={disabled}
				id={id}
				name={name}
				onBlur={onBlur}
				value={value}
				{...register(name, {required})}
				{...otherProps}
				
			>
				{defaultOption && <option className='first-option' disabled value="">{defaultOptionLabel}</option>}

				{options?.map((option) => {
					return (
						<option key={option.key} value={option.key}>
							{option.name}
						</option>
					);
				})}
			</select>
		</BaseWrapper>
	);
};

export default Select;
