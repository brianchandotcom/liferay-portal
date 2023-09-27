/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';

import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	items: React.OptionHTMLAttributes<HTMLOptionElement>[];
}

const CheckboxGroup = ({
	children,
	field,
	items = [],
	label,
	meta,
	required,
}: IProps & PRMFormFieldProps & PRMFormFieldStateProps<string[]>) => (
	<WrapperInput {...meta} label={label} required={required}>
		<div
			className={classNames('border px-3 pt-3 rounded-lg', {
				'border-danger': meta.error && meta.touched,
				'border-neutral-5': !meta.touched,
				'border-success': !meta.error && meta.touched,
			})}
		>
			{items.map((item, index) => (
				<ClayCheckbox
					{...field}
					checked={field.value?.includes(item.label as string)}
					key={`${item.value}-${index}`}
					label={item.label}
					value={item.label}
				/>
			))}

			{children}
		</div>
	</WrapperInput>
);
export default CheckboxGroup;
