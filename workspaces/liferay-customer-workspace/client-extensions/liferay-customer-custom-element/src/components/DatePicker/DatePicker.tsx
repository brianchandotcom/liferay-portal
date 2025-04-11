/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Badge} from '..';
import ClayDatePicker from '@clayui/date-picker';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useField} from 'formik';
import {
	required as requiredValidation,
	validate,
} from '~/utils/validations.form';

import './DatePicker.css';

import {IYears} from '@clayui/date-picker/lib/types';
import getIconSpriteMap from '~/utils/getIconSpriteMap';

interface IProps {
	badgeClassName?: string;
	className?: string;
	dateFormat?: string;
	groupStyle?: string;
	helper?: string;
	id?: string;
	label?: string;
	name: string;
	onBlur?: () => void;
	onChange?: (date: string) => void;
	placeholder?: string;
	required?: boolean;
	validations?: ((value: any) => string | undefined)[];
	years?: IYears;
	yearsCheck?: boolean;
}

const DatePicker: React.FC<IProps> = ({
	badgeClassName,
	className,
	id,
	dateFormat = 'MM/dd/yyyy',
	groupStyle,
	helper,
	label,
	name,
	onBlur,
	onChange,
	placeholder,
	required,
	validations = [],
	years,
	yearsCheck,
}) => {
	if (required) {
		validations = validations
			? [...validations, (value: string) => requiredValidation(value)]
			: [(value: string) => requiredValidation(value)];
	}

	const [field, meta, helpers] = useField({
		className,
		id,
		name,
		required,
		validate: (value) => validate(validations, value),
	});

	const getStyleStatus = () => {
		if (meta.touched) {
			return meta.error ? 'has-error' : 'has-success';
		}

		return;
	};

	const handleBlur = () => {
		helpers.setTouched(true);

		if (onBlur) {
			onBlur();
		}
	};

	const handleChange = (value: string) => {
		helpers.setValue(value);

		if (onChange) {
			onChange(value);
		}
	};

	return (
		<ClayForm.Group
			className={classNames('w-100', getStyleStatus(), groupStyle)}
		>
			<label>
				{label}

				{required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}
				<ClayDatePicker
					dateFormat={dateFormat}
					onBlur={handleBlur}
					onChange={handleChange}
					placeholder={placeholder}
					spritemap={getIconSpriteMap()}
					value={field.value}
					years={years}
					yearsCheck={yearsCheck}
				/>
			</label>

			{meta.touched && meta.error && required && (
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

export default DatePicker;
