/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {InputHTMLAttributes} from 'react';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
	component?: 'input' | 'textarea';
	name: string;
}

export function Input({component, ...props}: InputProps) {
	const maxLength = props.maxLength;

	return (
		<>
			<ClayInput
				className={classNames('marketplace-form-input', {
					'mb-1': maxLength,
				})}
				component={component}
				id={props.name}
				{...props}
			/>

			{maxLength && (
				<small className="text-black-50">
					{`${
						props.value ? (props.value as string).length : 0
					} / ${maxLength}`}
				</small>
			)}
		</>
	);
}
