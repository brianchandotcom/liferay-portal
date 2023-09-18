/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';
import React from 'react';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	emptyOptionMessage: string;
}

const Select = ({
	field,
	meta,
	label,
	emptyOptionMessage = 'No options available',
	options = [],
	required,
	...props
}: PRMFormFieldProps &
	React.ComponentProps<typeof ClaySelectWithOption> &
	PRMFormFieldStateProps<any> &
	IProps) => {
	const defaultOptions = {
		disabled: true,
		label: options.length ? 'Choose a option' : emptyOptionMessage,
		value: '',
	};

	const getValue = () => {
		if (typeof field.value === 'object') {
			if (
				field.value.externalReferenceCode
					? field.value.externalReferenceCode
					: field.value.id
			) {
				return (
					String(
						field.value.externalReferenceCode
							? field.value.externalReferenceCode
							: field.value.id
					) || ''
				);
			}

			return field.value.key || '';
		}

		return field.value || '';
	};

	return (
		<WrapperInput {...meta} label={label} required={required}>
			<ClaySelectWithOption
				options={[defaultOptions, ...options]}
				{...field}
				{...props}
				required={required}
				value={getValue()}
			/>
		</WrapperInput>
	);
};

export default Select;
