/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {FieldProps} from 'formik';
import {isNumber} from 'lodash';
import React from 'react';

import DateInput, {OverlayAlignment} from '../DateInput';
import HelpBlock from './HelpBlock';
import Label from './Label';

interface IFormDateInputProps
	extends FieldProps,
		React.HTMLAttributes<HTMLElement> {
	inline: boolean;
	label: string;
	overlayAlignment?: OverlayAlignment;
	popover: {
		content: React.ReactNode;
		title: React.ReactNode;
	};
	required?: boolean;
	showRetentionPeriod?: boolean;
	usePortal?: boolean;
	width: number;
}

const FormDateInput: React.FC<IFormDateInputProps> = ({
	className,
	field,
	form,
	inline = false,
	label,
	overlayAlignment = 'bottomLeft',
	popover,
	required = false,
	showRetentionPeriod = true,
	usePortal = true,
	width,
}) => {
	const {name, value} = field;

	const handleChange = (value: any): void => {
		const {setFieldValue} = form;

		setFieldValue(name, value);
	};

	const error = form.errors[name];
	const touched = form.touched[name];

	const classes = getCN('form-date-input-root', className, {
		'form-inline-group': inline,
		'has-error': error && touched,
		'has-success': !error && touched,
	});

	const style = isNumber(width)
		? {flexBasis: `${width}%`, flexGrow: 0}
		: undefined;

	return (
		<div className={classes} style={style}>
			{label && (
				<Label htmlFor={name} popover={popover} required={required}>
					{label}
				</Label>
			)}

			<DateInput
				id={name}
				name={name}
				onDateInputChange={handleChange}
				overlayAlignment={overlayAlignment}
				showRetentionPeriod={showRetentionPeriod}
				usePortal={usePortal}
				value={value}
			/>

			<HelpBlock name={name} />
		</div>
	);
};

export default FormDateInput;
