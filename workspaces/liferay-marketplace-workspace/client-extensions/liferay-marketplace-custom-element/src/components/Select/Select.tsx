/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {InputHTMLAttributes} from 'react';

import './Select.scss';
import BaseWrapper from '../Input/base/BaseWrapper';

type InputProps = {
	boldLabel?: boolean;
	className?: string;
	defaultOption?: boolean;
	defaultOptionLabel?: string;
	disabled?: boolean;
	errors?: any;
	helpText?: string;
	id?: string;
	label?: string;
	name: string;
	options?: {key: string; name: string}[];
	register?: any;
	required?: boolean;
} & InputHTMLAttributes<HTMLInputElement>;

const Select = React.forwardRef<HTMLInputElement, InputProps>(
	(
		{
			boldLabel,
			className,
			defaultOption = true,
			defaultOptionLabel,
			disabled = false,
			errors = {},
			label,
			helpText,
			name,
			register = () => {},
			id = name,
			value,
			required = false,
			onBlur,
			options,
			...otherProps
		},
		forwardRef
	) => {
		return (
			<BaseWrapper
				boldLabel={boldLabel}
				disabled={disabled}
				error={errors[name]?.message}
				id={id}
				label={label}
				required={required}
			>
				{helpText && <div className="text-muted">{helpText}</div>}

				<select
					className={`align-items-center custom-select d-flex form-control rounded-xs selection ${className}`}
					disabled={disabled}
					id={id}
					name={name}
					onBlur={onBlur}
					ref={forwardRef}
					value={value}
					{...register(name, {required})}
					{...otherProps}
				>
					{defaultOption && (
						<option
							className="selection-first-option"
							disabled
							value=""
						>
							{defaultOptionLabel}
						</option>
					)}

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
	}
);

export default Select;
