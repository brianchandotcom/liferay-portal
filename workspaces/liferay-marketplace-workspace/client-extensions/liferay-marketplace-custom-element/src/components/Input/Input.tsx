/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';

import {FieldBase} from '../FieldBase';

import './Input.scss';

interface InputProps
	extends React.InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea';
	description?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	label?: string;
	localized?: boolean;
	localizedTooltipText?: string;
	required?: boolean;
	tooltip?: string;
	tooltipText?: string;
	type?: 'number' | 'textarea' | 'text' | 'date';
	value?: string;
}

export function Input({
	className,
	component = 'input',
	helpMessage,
	hideFeedback,
	label,
	localized = false,
	localizedTooltipText,
	onChange,
	placeholder,
	required,
	tooltip,
	tooltipText,
	type,
	value,
	...otherProps
}: InputProps) {
	return (
		<FieldBase
			className={className}
			helpMessage={helpMessage}
			hideFeedback={hideFeedback}
			label={label}
			localized={localized}
			localizedTooltipText={localizedTooltipText}
			required={required}
			tooltip={tooltip}
			tooltipText={tooltipText}
		>
			<ClayInput
				className="custom-input"
				component={component}
				onChange={onChange}
				placeholder={placeholder}
				type={type}
				value={value}
				{...otherProps}
			/>
		</FieldBase>
	);
}
