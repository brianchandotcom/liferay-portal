/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {FieldProps} from 'formik';
import React from 'react';
import AutocompleteInput from '~/shared/components/AutocompleteInput';

import HelpBlock from './HelpBlock';
import Label from './Label';

interface FormAutocompleteInputType {
	className?: string;
	label?: string;
	required?: boolean;
	value: string;
}
const FormAutocompleteInput: React.FC<
	FormAutocompleteInputType & FieldProps
> = ({
	className,
	field: {name},
	form: {errors},
	label,
	required,
	...otherProps
}) => {
	const error = errors[name];

	const classes = getCN(className, {
		'has-error': error,
	});

	return (
		<div className={classes}>
			{label && (
				<Label htmlFor={name} required={required}>
					{label}
				</Label>
			)}

			<AutocompleteInput {...otherProps} />

			<HelpBlock name={name} />
		</div>
	);
};
export default FormAutocompleteInput;
