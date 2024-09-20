/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

import {FieldBase} from '../FieldBase';

import './Input.scss';

interface InputProps
	extends React.InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea';
	description?: string;
	errorMessage?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	label?: string;
	localized?: boolean;
	localizedTooltipText?: string;
	prependGroupItemSymbol?: string;
	required?: boolean;
	tooltip?: string;
	tooltipText?: string;
	type?: 'number' | 'textarea' | 'text' | 'date';
	value?: string;
}

export const Input = React.forwardRef<HTMLInputElement, InputProps>(
	(
		{
			className,
			component = 'input',
			errorMessage,
			helpMessage,
			hideFeedback,
			label,
			localized = false,
			localizedTooltipText,
			onChange,
			placeholder,
			prependGroupItemSymbol,
			required,
			tooltip,
			tooltipText,
			type,
			value,
			...otherProps
		},
		forwardRef
	) => {
		return (
			<FieldBase
				className={className}
				errorMessage={errorMessage}
				helpMessage={helpMessage}
				hideFeedback={hideFeedback}
				label={label}
				localized={localized}
				localizedTooltipText={localizedTooltipText}
				required={required}
				tooltip={tooltip}
				tooltipText={tooltipText}
			>
				{prependGroupItemSymbol ? (
					<ClayInput.Group>
						<ClayInput.GroupItem prepend shrink>
							<ClayInput.GroupText>
								{prependGroupItemSymbol}
							</ClayInput.GroupText>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem prepend>
							<ClayInput
								className="custom-input"
								component={component}
								onChange={onChange}
								placeholder={placeholder}
								ref={forwardRef}
								type={type}
								value={value}
								{...otherProps}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				) : (
					<ClayInput
						className="custom-input"
						component={component}
						onChange={onChange}
						placeholder={placeholder}
						ref={forwardRef}
						type={type}
						value={value}
						{...otherProps}
					/>
				)}
			</FieldBase>
		);
	}
);
