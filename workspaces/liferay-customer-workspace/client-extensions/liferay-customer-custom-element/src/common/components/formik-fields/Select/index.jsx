/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useField} from 'formik';
import {Badge} from '../..';
import {required, validate} from '../../../utils/validations.form';

const Select = ({
	groupStyle,
	helper,
	label,
	options,
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

	const getStyleStatus = () => {
		if (meta.touched) {
			return meta.error ? 'has-error' : 'has-success';
		}

		return;
	};

	return (
		<ClayForm.Group
			className={classNames('w-100', getStyleStatus(), groupStyle)}
		>
			<label>
				{label}

				{props.required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				<div className="position-relative">
					<ClayIcon className="select-icon" symbol="caret-bottom" />

					<ClaySelect {...field} {...props}>
						{options.map(({disabled, label, value}, index) => (
							<ClaySelect.Option
								disabled={disabled}
								key={`${value}-${index}`}
								label={label}
								value={value}
							/>
						))}
					</ClaySelect>
				</div>
			</label>

			{meta.touched && meta.error && props.required && (
				<Badge>
					<span className="pl-1">{meta.error}</span>
				</Badge>
			)}

			{helper && (
				<div className="ml-3 pl-3 text-neutral-6 text-paragraph-sm">
					{helper}
				</div>
			)}
		</ClayForm.Group>
	);
};

export default Select;
