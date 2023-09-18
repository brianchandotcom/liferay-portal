/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	textArea?: boolean;
}

const InputText = ({
	description,
	field,
	label,
	meta,
	required,
	textArea,
}: PRMFormFieldProps & PRMFormFieldStateProps<string> & IProps) => (
	<WrapperInput
		{...meta}
		description={description}
		label={label}
		required={required}
	>
		<ClayInput
			{...field}
			component={textArea ? 'textarea' : 'input'}
			required={required}
			type="text"
			value={field.value || ''}
		/>
	</WrapperInput>
);

export default InputText;
