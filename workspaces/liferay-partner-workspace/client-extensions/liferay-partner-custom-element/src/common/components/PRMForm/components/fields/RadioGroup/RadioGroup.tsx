/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio} from '@clayui/form';
import classNames from 'classnames';

import LiferayPicklist from '../../../../../interfaces/liferayPicklist';
import WrapperInput from '../common/components/WrapperInput';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

interface IProps {
	items: React.OptionHTMLAttributes<HTMLOptionElement>[];
	small?: boolean;
}

const RadioGroup = ({
	field,
	meta,
	items = [],
	label,
	required,
	small,
	...props
}: IProps &
	PRMFormFieldProps &
	PRMFormFieldStateProps<string | LiferayPicklist>) => {
	const getValue = () => {
		if (typeof field.value === 'object') {
			return field.value.key || '';
		}

		return field.value || '';
	};

	return (
		<WrapperInput {...meta} label={label} required={required}>
			{items.map((item, index) => (
				<div
					className={classNames({
						'border border-neutral-5 mb-2 p-3 rounded-lg': !small,
						'my-2': small,
					})}
					key={index}
				>
					<ClayRadio
						{...field}
						{...props}
						checked={getValue() === item.value}
						key={`${item.value}-${index}`}
						label={item.label}
						value={item.value as string}
					/>
				</div>
			))}
		</WrapperInput>
	);
};
export default RadioGroup;
