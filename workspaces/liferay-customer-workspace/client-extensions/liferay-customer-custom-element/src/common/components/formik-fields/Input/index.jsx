/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useField} from 'formik';
import {Badge} from '../..';
import {required, validate} from '../../../utils/validations.form';

const Input = ({
	disableError,
	groupStyle,
	helper,
	label,
	validations,
	...props
}) => {
	if (props.required) {
		validations = validations
			? [...validations, (value) => required(value)]
			: [(value) => required(value)];
	}

	const [field, meta] = useField({
		...props,
		validate: (value) => validate(validations, value),
	});

	return (
		<ClayForm.Group
			className={classNames('w-100', {
				groupStyle,
				'has-error': meta.touched && meta.error,
				'has-success': meta.touched && !meta.error,
			})}
		>
			<label>
				{`${label} `}

				{props.required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				<ClayInput {...field} {...props} />
			</label>

			{(typeof meta.error === 'string' || meta.error instanceof String) &&
			meta.touched &&
			!disableError ? (
				<Badge>
					<span className="pl-1">{meta.error}</span>
				</Badge>
			) : (
				helper && (
					<div className="ml-3 pl-3 pr-2 text-neutral-6 text-paragraph-sm">
						{helper}
					</div>
				)
			)}
		</ClayForm.Group>
	);
};

export default Input;
