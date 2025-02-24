/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Badge} from '..';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useField} from 'formik';
import {required, validate} from '~/utils/validations.form';

import './TimePicker.css';

import ClayTimePicker from '@clayui/time-picker';

interface IProps {
	badgeClassName?: string;
	groupStyle?: string;
	helper?: string;
	label: string;
	name: string;
	required?: boolean;
	validations?: Function[];
}

const TimePicker: React.FC<IProps> = ({
	badgeClassName,
	groupStyle,
	helper,
	label,
	validations = [],
	...props
}) => {
	if (props.required) {
		validations = validations
			? [...validations, (value: string) => required(value)]
			: [(value: string) => required(value)];
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
			<label className="date-picker">
				{label}

				{props.required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				<ClayTimePicker {...field} {...props} />
			</label>

			{meta.touched && meta.error && props.required && (
				<Badge badgeClassName={badgeClassName}>
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

export default TimePicker;
