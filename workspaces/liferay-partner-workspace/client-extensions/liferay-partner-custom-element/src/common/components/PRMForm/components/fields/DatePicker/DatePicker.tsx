/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

const DatePicker = ({
	field,
	label,
	meta,
	required,
	...props
}: PRMFormFieldProps & PRMFormFieldStateProps<string>) => (
	<WrapperInput {...meta} label={label} required={required}>
		<input
			className="form-control"
			name={field.name}
			onBlur={field.onBlur}
			onChange={field.onChange}
			type="date"
			value={field.value || ''}
			{...props}
		/>
	</WrapperInput>
);

export default DatePicker;
