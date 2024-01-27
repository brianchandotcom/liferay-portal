/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {InputHTMLAttributes} from 'react';

import BaseWrapper from '../base/BaseWrapper';

import './index.scss';

import classNames from 'classnames';

export type InputProps = {
	boldLabel?: boolean;
	className?: string;
	component?: string;
	description?: string;
	disabled?: boolean;
	errors?: any;
	helpMessage?: string;
	id?: string;
	label?: string;
	name: string;
	options?: {label: string; value: string} | [];
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const FormInput: React.FC<InputProps> = ({
	boldLabel,
	className,
	description,
	disabled = false,
	errors = {},
	helpMessage,
	label,
	name,
	register = () => {},
	id = name,
	type,
	value,
	required = false,
	onBlur,
	...otherProps
}) => {
	return (
		<BaseWrapper
			boldLabel={boldLabel}
			description={description}
			disabled={disabled}
			error={errors[name]?.message}
			helpMessage={helpMessage}
			id={id}
			label={label}
			required={required}
		>
			<ClayInput
				className={classNames('rounded-xs custom-input', className)}
				component={type === 'textarea' ? 'textarea' : 'input'}
				disabled={disabled}
				id={id}
				name={name}
				type={type}
				value={value}
				{...otherProps}
				{...register(name, {onBlur, required})}
			/>
		</BaseWrapper>
	);
};

export default FormInput;
